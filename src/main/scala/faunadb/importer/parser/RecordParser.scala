package faunadb.importer.parser

import faunadb.importer.config._
import faunadb.importer.lang._
import faunadb.importer.values._

private[parser] object RecordParser {
  def parse(input: => Stream[Result[Value]])(implicit context: Context): Stream[Result[Record]] =
    new RecordParser(input, context).parse()
}

private class RecordParser(input: => Stream[Result[Value]], context: Context) {
  private type RN[A] = (A, Int)

  def parse(): Stream[Result[Record]] = {
    val withIds = context.idField
      .map(id => input.map(findId(id, _)))
      .getOrElse(generateRownum(input))

    context.tsField
      .map(ts => withIds.map(findTS(ts, _)))
      .getOrElse(withIds.map(noTSField))
  }

  private def findId(idField: String, value: Result[Value]): Result[(String, Value)] =
    value flatMap (v => getScalarField(idField, v) map (_.raw -> v))

  private def generateRownum(stream: => Stream[Result[Value]]): Stream[Result[(String, Value)]] =
    stream.zip(Stream.iterate(1L)(_ + 1)) map { case (value, id) =>
      value map (id.toString -> _)
    }

  private def findTS(tsField: String, value: Result[(String, Value)]): Result[Record] =
    value flatMap { case (id, v) =>
      getScalarField(tsField, v) map (ts => Record(id, Some(ts), v))
    }

  private def noTSField(value: Result[(String, Value)]): Result[Record] =
    value map { case (id, v) => Record(id, None, v) }

  private def getScalarField(fieldName: String, value: Value): Result[Scalar] = value match {
    case obj: Object =>
      obj.fields.get(fieldName) map {
        case scalar: Scalar => Ok(scalar)
        case other          => Err(s"Can not use value of field $fieldName as the id for entry at ${other.localized}")
      } getOrElse
        Err(s"Can not find field $fieldName for entry at ${obj.localized}")

    case other =>
      Err(s"Can not get field $fieldName from non object value at ${other.localized}")
  }
}
