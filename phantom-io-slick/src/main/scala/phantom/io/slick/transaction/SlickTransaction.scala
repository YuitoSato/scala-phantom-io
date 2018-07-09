package phantom.io.slick.transaction

import phantom.io.core.error
import phantom.io.core.error.Error
import phantom.io.core.result.{ AsyncResult, Result }
import phantom.io.core.transaction.Transaction
import scalaz.EitherT
import slick.basic.BasicProfile
import slick.dbio.DBIO
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.ExecutionContext

case class SlickTransaction[A](
  execute: () => EitherT[DBIO, Error, A]
)(
  implicit val ec: ExecutionContext,
  implicit val db: BasicProfile#Backend#Database
) extends Transaction[A] { self =>

  override def map[B](f: A => B): SlickTransaction[B] = {
    val exec = () => execute().map(f)
    SlickTransaction(exec)
  }

  override def flatMap[B](f: A => Transaction[B]): SlickTransaction[B] = {
    val exec = () => execute().map(f).flatMap(_.asInstanceOf[SlickTransaction[B]].execute())
    SlickTransaction(exec)
  }

  override def foreach(f: A => Unit): Unit = map(f)

  override def run: Result[A] = AsyncResult {
    val dbio = self.asInstanceOf[SlickTransaction[A]].execute().run
    EitherT(db.run(dbio.transactionally))
  }

  override def leftMap(f: error.Error => error.Error): Transaction[A] = {
    val exec = () => execute().leftMap(f)
    SlickTransaction(exec)
  }

}
