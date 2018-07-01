package phantom.io.core.transaction

import org.scalatest.FunSpec

class TransactionSpec extends FunSpec {

  implicit val builder: MockTransactionBuilder = new MockTransactionBuilder

  val seqF: Seq[() => Int] =  for (i <- 1 to 10) yield {
    { () =>
      println(i)
      i
    }
  }

  val seqT: Seq[Transaction[Int]] = seqF.map { f =>
    builder.build(f)
  }

  builder.sequence(seqT).run






}
