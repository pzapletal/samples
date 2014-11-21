# Concordance Task

Solution of Concordance task

## Intro

Algorithm calculates Concordance for given input text. Solution is based on splitting input text into sentences and 
sentences to words with regular expression. Parsed words of each sentence are counted and merged together in order
to provide expected result. The result can be printed in requested format. 

Program can be built with sbt easily. Apart of standard Scala library, it uses scalaz and scalatest libraries.

Program also contains 'main' class and therefore can be executed. It this case, text from solution description
is used as an input of algorithm.

Part of the delivered solution are also unit tests and codebase documentation.

## Prerequisites:

* Scala 2.11.0
* Sbt 0.3.12

## Discussion

As stated before, solution is based on parsing given text with regular expressions. These regexes gives a very 
similar output as in shown in task description (the only difference is 'i.e.'). I've also modified a text output
to be more readable in console environment.

I consider this task as very easy, complete solution was ready during 4 hours.

## Usage

```
$ sbt run
...
1. a			{2:1,1}
2. all			{1:1}
3. alphabetical			{1:1}
4. an			{2:1,1}
5. appeared			{1:2}
6. arbitrary			{1:1}
7. bonus			{1:2}
8. concordance			{1:1}
9. document			{1:1}
...
```
```
$ sbt test
...
ConcordanceSpec:
Concordance calculation
 - should return empty collection when input is empty
 - should return empty collection when input is null
...
```