package phantom.io.slick.transaction

import phantom.io.core.error.Error
import phantom.io.core.transaction.TransactionBuilder
import scalaz.{ EitherT, \/, \/- }
import slick.dbio.DBIO

class SlickTransactionBuilder extends TransactionBuilder {

  override def build[A](value: A): SlickTransaction[A] = {
    val dbio: DBIO[Error \/ A] = DBIO.successful(\/-(value))
    val exec = () => EitherT(dbio)
    SlickTransaction(exec)
  }

  override def build[A](value: \/[Error, A]): SlickTransaction[A] = {
    val dbio: DBIO[Error \/ A] = DBIO.successful(value)
    val exec = () => EitherT(dbio)
    SlickTransaction(exec)
  }

}
