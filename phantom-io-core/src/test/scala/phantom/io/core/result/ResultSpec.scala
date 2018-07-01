package phantom.io.core.result

import org.scalatest.FunSpec
import phantom.io.core.error.MockError
import scalaz.{ -\/, \/- }

class ResultSpec extends FunSpec {

  implicit val builder: SyncResultBuilder = new SyncResultBuilder

  describe("#zipWith") {
    it("returns 0 as first element and 10 as last element.") {
      val list = (for (i <- 1 to 10) yield i).toList
      val result = builder.build(list).zipWith(builder.build(0))((l, i) => i :: l)

      assert(result match {
        case SyncResult(\/-(l)) => l.head == 0 && l.reverse.head == 10
        case _ => false
      })
    }
  }

  describe("#_if, #_else") {
    describe("when it fails.") {
      it("returns error.") {
        val result = builder.build(Some(1)) _if(_.isEmpty) _else MockError

        assert(result match {
          case SyncResult(-\/(_)) => true
          case _ => false
        })
      }
    }

    describe("when it successes.") {
      it("returns int.") {
        val result = builder.build(Some(1)) _if(_.isDefined) _else MockError

        assert(result match {
          case SyncResult(\/-(Some(i))) => i == 1
          case _ => false
        })
      }
    }
  }

}
