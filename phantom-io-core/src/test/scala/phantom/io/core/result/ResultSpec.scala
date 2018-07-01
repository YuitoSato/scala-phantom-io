package phantom.io.core.result

import org.scalatest.FunSpec
import phantom.io.core.error.MockError

class ResultSpec extends FunSpec {

  def main(): Unit = {}

  implicit val builder: SyncResultBuilder = new SyncResultBuilder

  val result: Result[Some[Int]] = builder.build(Some(1))

  val r = for {
    a <- result _if (a => true) _else MockError
    b <- result
  } yield b

  println(r)

}
