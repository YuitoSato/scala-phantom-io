package phantom.io.core.transaction

import phantom.io.core.error.Error
import phantom.io.core.result.{ Result, SyncResult }
import scalaz.\/

case class MockTransaction[A](
  execute: () => Error \/ A
) extends Transaction[A]  {

  override def map[B](f: A => B): Transaction[B] =
    MockTransaction(() => execute().map(f))

  override def flatMap[B](f: A => Transaction[B]): Transaction[B] =
    MockTransaction(() => execute().map(f).flatMap(_.asInstanceOf[MockTransaction[B]].execute()))

  override def leftMap(f: Error => Error): Transaction[A] = MockTransaction(() => execute().leftMap(f))

  override def run: Result[A] = SyncResult(execute())

}
