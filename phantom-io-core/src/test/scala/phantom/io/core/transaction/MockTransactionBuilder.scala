package phantom.io.core.transaction

import phantom.io.core.error.Error
import scalaz.{ \/, \/- }

class MockTransactionBuilder extends TransactionBuilder {

  override def build[A](value: Error \/ A): Transaction[A] = MockTransaction(() => value)

  override def build[A](value: A): Transaction[A] = MockTransaction(() => \/-(value))

  def build[A](f: () => A): Transaction[A] = {
    val exec: () => Error \/ A = () => \/-(f())

    MockTransaction(exec)
  }

}
