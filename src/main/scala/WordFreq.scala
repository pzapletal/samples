import scala.collection.mutable
import scalaz.Scalaz._

/**
 * Created by petrzapletal on 20/11/14.
 */

object WordFreq {

  //http://stackoverflow.com/questions/25735644/python-regex-for-splitting-text-into-sentences-sentence-tokenizing
  val SENTENCE_SEP = "(?<!\\w\\.\\w.)(?<![A-Z][a-z]\\.)(?<=\\.|\\?)\\s"

  //https://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html
  val WORD_SEP = "(\\s|\\p{Punct})+" //TODO FIX i.e.

  val input = "Given an arbitrary text document written in English, write a program that will generate a " +
    "concordance, i.e. an alphabetical list of all word occurrences, labeled with word frequencies." +
    " Bonus: label each word with the sentence numbers in which each occurrence appeared."

  def main(args: Array[String]): Unit = {

    val sentences = input.toLowerCase.split(SENTENCE_SEP)

    sentences foreach (println(_))

    val wordsOccursPerSentence = sentences
      .map(s => s.split(WORD_SEP))
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

    val result = wordsOccursPerSentence
      .reduce(_ |+| _)
      .toSeq
      .sortBy(_._1)

    prettyPrint(result)
  }

  def prettyPrint(result: Seq[(String, List[Int])]): Unit = {
    result.foreach(r => println(s"${r._1}\t\t\t{${r._2.length}:${printFreqs(r._2)}}"))
  }

  def printFreqs(freqs: List[Int]): String = freqs.map(_.toString + ",").reduce(_ + _).dropRight(1)
}
