import org.fiser.Addable

def sum[T](xs: List[T])(implicit addable: Addable[T]) = {
  xs.foldLeft(addable.zero)(addable.append)
}
sum(List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))

def sum2[T: Addable](xs: List[T]) = {
  val addable = implicitly[Addable[T]]
  xs.foldLeft(addable.zero)(addable.append)
}
sum2(List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))

