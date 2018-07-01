package phantom.io.core.error

case object MockError extends Error {

  override val code: String = "error.mock"

  override val message: String = "This is a mock error."

}
