package helpers

import zio.http.{Method, URL}
import zio.test.Gen

trait Generators {

  val methodGen: Gen[Any, Method] = Gen.fromIterable(Seq(
    Method.POST,
    Method.GET,
    Method.OPTIONS,
    Method.HEAD,
    Method.PUT,
    Method.PATCH,
    Method.DELETE,
    Method.TRACE,
    Method.CONNECT
  ))

  val urlGen: Gen[Any, URL] = Gen.alphaNumericString.map(URL.decode(_).getOrElse(URL.empty))

}
