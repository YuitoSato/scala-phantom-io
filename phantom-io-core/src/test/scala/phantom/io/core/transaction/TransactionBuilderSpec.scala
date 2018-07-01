package phantom.io.core.transaction

import org.scalatest.FunSpec
import phantom.io.core.result.SyncResult
import scalaz.\/-

class TransactionBuilderSpec extends FunSpec {

  val builder: MockTransactionBuilder = new MockTransactionBuilder

  describe("#sequence") {
    it("returns Result[Seq[A]] and does not change the order.") {
      val seq: Seq[Transaction[Int]] = for (i <- 1 to 10) yield builder.build(i)
      val transaction = builder.sequence(seq)

      assert(transaction.run match {
        case SyncResult(\/-(s)) => s.head == 1 && s.reverse.head == 10
        case _ => false
      })
    }
  }

}
