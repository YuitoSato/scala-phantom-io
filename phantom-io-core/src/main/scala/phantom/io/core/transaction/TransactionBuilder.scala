package phantom.io.core.transaction

import scalaz.\/

trait TransactionBuilder {

  def build[RESULT, ERROR, A](value: \/[ERROR, A]): Transaction[RESULT, ERROR, A]

  def build[RESULT, ERROR, A](value: A): Transaction[RESULT, ERROR, A]

  def sequence[RESULT, ERROR, A](seq: Seq[Transaction[RESULT, ERROR, A]]): Transaction[RESULT, ERROR, Seq[A]]

}
