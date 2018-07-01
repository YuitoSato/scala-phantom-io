package phantom.io.core.result

import org.scalatest.FunSpec
import scalaz.\/-

class ResultBuilderSpec extends FunSpec {

  val builder: SyncResultBuilder = new SyncResultBuilder

  describe("#sequence") {
    it("returns Result[Seq[A]] and does not change the order.") {
      val seq: Seq[Result[Int]] = for (i <- 1 to 10) yield builder.build(i)
      val result = builder.sequence(seq)

      assert(result match {
        case SyncResult(\/-(s)) => s.head == 1 && s.reverse.head == 10
        case _ => false
      })
    }
  }

}
