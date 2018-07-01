package phantom.io.core.result

import scalaz.\/

trait ResultBuilder {

  def build[E, A](value: \/[E, A]): Result[E, A]

  def build[E, A](value: A): Result[E, A]

  def sequence[E, A](seq: Seq[Result[E, A]]): Result[E, Seq[A]]

}
