package phantom.result

trait Result[A, ERROR] {

  def map[B](f: A => B): Result[B, ERROR]

  def flatMap[B](f: A => Result[B, ERROR]): Result[B, ERROR]

  def foreach(f: A => Unit): Unit = map(f)

}
