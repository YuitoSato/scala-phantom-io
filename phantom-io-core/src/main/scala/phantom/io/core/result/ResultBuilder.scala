package phantom.io.core.result

import scalaz.\/

trait ResultBuilder {

  def build[ERROR, A](value: \/[ERROR, A]): Result[ERROR, A]

  def build[ERROR, A](value: A): Result[ERROR, A]

  def sequence[ERROR, A](seq: Seq[Result[ERROR, A]]): Result[ERROR, Seq[A]]

}
