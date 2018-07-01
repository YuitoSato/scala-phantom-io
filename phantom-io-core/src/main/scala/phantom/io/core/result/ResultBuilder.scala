package phantom.io.core.result

import phantom.io.core.error.Error
import scalaz.{ \/, \/- }

trait ResultBuilder {

  def build[A](value: \/[Error, A]): Result[A]

  def build[A](value: A): Result[A]

  def sequence[A](seq: Seq[Result[A]]): Result[Seq[A]] = {
    seq.toList.foldLeft(build(Nil: List[A])) {
      (resultSeq, resultA) => resultSeq.zipWith(resultA)((l, a) => a :: l)
    }.map(_.reverse.toSeq)
  }

}

class SyncResultBuilder extends ResultBuilder{

  override def build[A](value: Error \/ A): Result[A] = SyncResult(value)

  override def build[A](value: A): Result[A] = SyncResult(\/-(value))

}
