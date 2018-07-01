package phantom.io.core.result

import phantom.io.core.error.Error
import scalaz.{ EitherT, \/, \/- }

import scala.concurrent.{ ExecutionContext, Future }

trait ResultBuilder {

  def build[A](value: \/[Error, A]): Result[A]

  def build[A](value: A): Result[A]

  def sequence[A](seq: Seq[Result[A]]): Result[Seq[A]] = {
    seq.toList.foldLeft(build(Nil: List[A])) {
      (resultSeq, resultA) => resultSeq.zipWith(resultA)((l, a) => a :: l)
    }.map(_.reverse.toSeq)
  }

}

class SyncResultBuilder extends ResultBuilder {

  override def build[A](value: Error \/ A): Result[A] = SyncResult(value)

  override def build[A](value: A): Result[A] = SyncResult(\/-(value))

}

class AsyncResultBuilder()(
  implicit val ec: ExecutionContext
) extends ResultBuilder {

  override def build[A](value: Error \/ A): Result[A] = AsyncResult(
    EitherT(Future.successful(value))
  )

  override def build[A](value: A): Result[A] = {
    val either: Error \/ A = \/-(value)
    AsyncResult(
      EitherT(Future.successful(either))
    )
  }

}
