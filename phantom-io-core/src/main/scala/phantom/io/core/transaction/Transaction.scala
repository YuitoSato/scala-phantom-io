package phantom.io.core.transaction

import phantom.io.core.error.Error
import phantom.io.core.result.Result
import scalaz.-\/

trait Transaction[A] { self =>

  def map[B](f: A => B): Transaction[B]

  def flatMap[B](f: A => Transaction[B]): Transaction[B]

  def leftMap(f: Error => Error): Transaction[A]

  def foreach(f: A => Unit): Unit = map(f)

  def zipWith[U, R](that: Transaction[U])(f: (A, U) => R): Transaction[R] = flatMap(a => that.map(u => f(a, u)))

  def run: Result[A]

  def _if(p: A => Boolean): ErrorHandler = new ErrorHandler(p)

  class ErrorHandler(p: A => Boolean) {
    def _else(e: => Error)(implicit builder: TransactionBuilder): Transaction[A] = {
      flatMap(a =>
        if (p(a))
          self
        else
          builder.build(-\/(e))
      )
    }

    def _else(f: A => Error)(implicit builder: TransactionBuilder): Transaction[A] = {
      flatMap(a =>
        if (p(a))
          self
        else
          builder.build(-\/(f(a)))
      )
    }
  }

}
