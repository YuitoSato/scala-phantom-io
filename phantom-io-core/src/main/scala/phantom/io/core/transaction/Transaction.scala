package phantom.io.core.transaction

trait Transaction[RESULT, ERROR, A] { self =>

  def map[B](f: A => B): Transaction[RESULT, ERROR, B]

  def flatMap[B](f: A => Transaction[RESULT, ERROR, B]): Transaction[RESULT, ERROR, B]

  def leftMap[NEW_ERROR](f: ERROR => NEW_ERROR): Transaction[RESULT, NEW_ERROR, A]

  def foreach(f: A => Unit): Unit = map(f)

  def run: RESULT

}
