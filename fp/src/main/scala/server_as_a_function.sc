import cats.data.{Xor, XorT}
import cats.instances.future._
import fiser.utils._
import cats.syntax.xor._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}
import simulacrum.typeclass

type Service[F[_], Req, Rep] = Req => F[Rep]

@typeclass trait Capture[F[_]] {
  def capture[A](a: => A): F[A]
}

implicit def futureCapture(implicit ec:  ExecutionContext): Capture[Future] =
  new Capture[Future] {
    override def capture[A](a: => A): Future[A] = Future(a)(ec)
  }

type UserId = Long
type AddressId = Long

case class User(userId: UserId, addressId: AddressId)

case class Address(addressId: AddressId)

sealed abstract class AppException(msg: String) extends Product with Serializable

case class NotFound(msg: String) extends AppException(msg)

case class DuplicateFound(msg: String) extends AppException(msg)

case class TimeoutException(msg: String) extends AppException(msg)

case class HostNotFoundException(msg: String) extends AppException(msg)

val existingUserId = 1L
val noneExistingUserId = 2L

def fetchRemoteUser(userId: UserId): User = {
  if (userId == existingUserId) User(userId, 10L) else null
}

def fetchRemoteAddress(addressId: AddressId): Address = {
  Address(addressId)
}

implicit class FutureOptionOps[A](fa: Future[Option[A]]) {
   def toXor[L](e: L): Future[L Xor A] =
     fa map (_.fold(e.left[A])(x => x.right[L]))
}

val fetchUser: Service[Future, UserId, NotFound Xor User] =
  (userId: UserId) => Future(Option(fetchRemoteUser(userId)))
    .toXor(NotFound(s"User $userId not found"))

val fetchAddress: Service[Future, AddressId, NotFound Xor Address] =
  (addressId: AddressId) => Future(Option(fetchRemoteAddress(addressId)))
    .toXor(NotFound(s"Address $addressId not found"))

val fetchUserInfo: Service[Future, UserId, NotFound Xor (User, Address)] =
  (userId: UserId) =>
    (for {
      user <- XorT(fetchUser(userId))
      address <- XorT(fetchAddress(user.addressId))
    } yield (user, address)).value

fetchUserInfo(existingUserId).await
fetchUserInfo(noneExistingUserId).await