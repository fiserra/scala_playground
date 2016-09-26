package org.fiser.json

trait Expression[A] {
  def value(expr: A): Int
}

object Expression {
  implicit val intExpression = new Expression[Int] {
    override def value(n: Int): Int = n
  }

  implicit def pairPlusExpression[T1: Expression, T2: Expression] =
    new Expression[(T1, T2)] {
      override def value(pair: (T1, T2)): Int = {
        implicitly[Expression[T1]].value(pair._1) + implicitly[Expression[T2]].value(pair._2)
      }
    }
}

object ExpressionEvaluator {
  def evaluate[A](value: A)(implicit expr: Expression[A]): Int = {
    expr.value(value)
  }
}