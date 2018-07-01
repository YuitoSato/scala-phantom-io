package phantom.io.core.error

trait Error {

  def code: String
  def message: String

  override def toString: String = s"$code: $message"

}
