package org.fiser

trait Addable[T] {
  def zero: T
  def append(a: T, b: T): T
}

object Addable {
  implicit object IntIsAddable extends Addable[Int] {
    override def zero: Int = 0
    override def append(a: Int, b: Int): Int = a + b
  }

  implicit object StringIsAddable extends Addable[String] {
    override def zero: String = ""
    override def append(a: String, b: String): String = a + b
  }
}
