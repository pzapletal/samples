import scala.collection.mutable
import scalaz.Scalaz._

/**
 * Solution of Concordance task
 */
object Concordance {

  /**
   * Regex for splitting article to sentences
   * http://stackoverflow.com/questions/25735644/python-regex-for-splitting-text-into-sentences-sentence-tokenizing
   */
  val SENTENCE_PATTERN = "(?<!\\w\\.\\w.)(?<![A-Z][a-z]\\.)(?<=\\.|\\?)\\s"

  /**
   * Regex for splitting sentence to words
   * https://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html
   */
  val WORD_PATTERN = "(\\s|\\p{Punct})+"

  /**
   * Test article from task description. Used as input for main method
   */
  val ARTICLE = "Given an arbitrary text document written in English, write a program that will generate a " +
    "concordance, i.e. an alphabetical list of all word occurrences, labeled with word frequencies." +
    " Bonus: label each word with the sentence numbers in which each occurrence appeared."

  /**
   * Entry point of algorithm. Calculates the concordance for 'ARTICLE'
   * @param args
   */
  def main(args: Array[String]): Unit = {
    val result = calculateConcordance(ARTICLE)
    prettyPrint(result)
  }

  /**
   * Calculates Concordance for given 'text'
   * @param text 'text' for processing
   * @return Seq of 'ConcordanceResult' with calculated concordances
   */
  def calculateConcordance(text: String): Seq[ConcordanceResult] = {

    val sentences = Option(text).getOrElse("").toLowerCase.split(SENTENCE_PATTERN)

    val wordsOccursPerSentence = sentences
      .filter(_.length > 0)
      .map(s => s.split(WORD_PATTERN))
      .zipWithIndex
      .map(s => s._1.foldLeft(mutable.Map[String, List[Int]]()) {
      (m, w) =>
        m(w) = m.get(w) match {
          case Some(l) => l.::(s._2 + 1)
          case None => List(s._2 + 1)
        }
        m
    }.toMap
      ).toList

    val merged = wordsOccursPerSentence.reduceOption(_ |+| _)

    val result = merged match {
      case Some(r) => r.toSeq.sortBy(_._1).map(r => ConcordanceResult(r._1, r._2))
      case None => Seq()
    }

    result
  }

  /**
   * Prints given Seq of 'ConcordanceResult' in requested format
   * @param result
   */
  def prettyPrint(result: Seq[ConcordanceResult]): Unit =
    result.zipWithIndex.foreach(r =>
      println(s"${r._2 + 1}. ${r._1.word}\t\t\t{${r._1.occurrences.length}:${printFreqs(r._1.occurrences)}}"))

  private def printFreqs(freqs: List[Int]): String = freqs.map(_.toString + ",").reduce(_ + _).dropRight(1)
}

/**
 * Contains result for one word
 * @param word Processed word
 * @param occurrences Occurrences in sentences
 */
case class ConcordanceResult(word: String, occurrences: List[Int]);