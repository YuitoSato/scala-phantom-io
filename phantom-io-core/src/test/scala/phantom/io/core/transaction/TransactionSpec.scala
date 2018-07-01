package phantom.io.core.transaction

import org.scalatest.FunSpec
import phantom.io.core.error.MockError
import phantom.io.core.result.SyncResult
import scalaz.{ -\/, \/- }

class TransactionSpec extends FunSpec {

  implicit val builder: MockTransactionBuilder = new MockTransactionBuilder

  describe("#zipWith") {
    it("returns 0 as first element and 10 as last element.") {
      val list = (for (i <- 1 to 10) yield i).toList
      val transaction = builder.build(list).zipWith(builder.build(0))((l, i) => i :: l)

      assert(transaction.run match {
        case SyncResult(\/-(l)) => l.head == 0 && l.reverse.head == 10
        case _ => false
      })
    }
  }

  describe("#_if, #_else") {
    describe("when it fails.") {
      it("returns error.") {
        val transaction = builder.build(Some(1)) _if(_.isEmpty) _else MockError

        assert(transaction.run match {
          case SyncResult(-\/(_)) => true
          case _ => false
        })
      }
    }

    describe("when it successes.") {
      it("returns int.") {
        val transaction = builder.build(Some(1)) _if(_.isDefined) _else MockError

        assert(transaction.run match {
          case SyncResult(\/-(Some(i))) => i == 1
          case _ => false
        })
      }
    }
  }

}
