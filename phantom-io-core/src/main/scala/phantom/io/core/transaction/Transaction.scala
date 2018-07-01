package phantom.io.core.transaction

/**
  * @tparam R Result after a transaction is committed
  * @tparam E Error while transaction processing
  * @tparam A Value in a transaction
  */
trait Transaction[R, E, A] { self =>

  def map[B](f: A => B): Transaction[R, E, B]

  def flatMap[B](f: A => Transaction[R, E, B]): Transaction[R, E, B]

  def leftMap(f: E => E): Transaction[R, E, A]

  def foreach(f: A => Unit): Unit = map(f)

  def run: R

}
