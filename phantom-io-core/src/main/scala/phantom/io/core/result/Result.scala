package phantom.io.core.result

import phantom.io.core.error.Error
import scalaz.std.{ EitherInstances, FutureInstances }
import scalaz.{ EitherT, \/ }

import scala.concurrent.duration.Duration
import scala.concurrent.{ Await, ExecutionContext, Future }

/**
  * @tparam E Error
  * @tparam A Value from a result
  */
trait Result[E, A] { self =>

  def map[B](f: A => B): Result[E, B]

  def flatMap[B](f: A => Result[E, B]): Result[E, B]

  def leftMap(f: E => E): Result[E, A]

  def foreach(f: A => Unit): Unit = map(f)

}

case class SyncResult[A](
  value: Error \/ A
) extends Result[Error, A] with EitherInstances {

  override def map[B](f: A => B): Result[Error, B] = SyncResult(value.map(f))

  override def flatMap[B](f: A => Result[Error, B]): Result[Error, B] = {
    SyncResult(
      value.flatMap(f(_) match {
        case sync: SyncResult[B] => sync.value
        case async: AsyncResult[B] => Await.result(async.value.run, Duration.Inf)
      })
    )
  }

  override def leftMap(f: Error => Error): Result[Error, A] = SyncResult(value.leftMap(f))

}

case class AsyncResult[A](
  value: EitherT[Future, Error, A]
)(
  implicit ec: ExecutionContext
) extends Result[Error, A] with FutureInstances with EitherInstances {

  override def map[B](f: A => B): Result[Error, B] = AsyncResult(value.map(f))

  override def flatMap[B](f: A => Result[Error, B]): Result[Error, B] = {
    AsyncResult(
      value.flatMap(f(_) match {
        case async: AsyncResult[B] => async.value
        case sync: SyncResult[B] => EitherT(Future.successful(sync.value))
      })
    )
  }

  override def leftMap(f: Error => Error): Result[Error, A] = AsyncResult(value.leftMap(f))

}
