import org.scalatest._

/**
 * Concordance unit tests
 */
class ConcordanceSpec extends FlatSpec with Matchers {

  "Concordance calculation" should "return empty collection when input is empty" in {
    Concordance.calculateConcordance("") should be(Seq())
  }

  it should "return empty collection when input is null" in {
    Concordance.calculateConcordance(null) should be(Seq())
  }

  it should "return empty collection when input is '.....'" in {
    Concordance.calculateConcordance(".....") should be(Seq())
  }

  it should "return single result for one word sentence" in {
    Concordance.calculateConcordance("Concordance") should be(Seq(ConcordanceResult("concordance", List(1))))
  }

  it should "return correct result for sample sentences" in {
    Concordance.calculateConcordance("Hello Petr. How are you today? Bye Petr.") should be(Seq(
      ConcordanceResult("are", List(2)),
      ConcordanceResult("bye", List(3)),
      ConcordanceResult("hello", List(1)),
      ConcordanceResult("how", List(2)),
      ConcordanceResult("petr", List(1, 3)),
      ConcordanceResult("today", List(2)),
      ConcordanceResult("you", List(2))
    ))
  }

  it should "return correct result sentence with special chars" in {
    Concordance.calculateConcordance("test @$ test .,.'; test") should be(Seq(ConcordanceResult("test", List(1, 1, 1))))
  }
}
