package http.endpoints

import helpers.Generators
import zio.*
import zio.http.{Method, Request}
import zio.test.*

import java.nio.charset.Charset

object DefaultRoutesSpec extends ZIOSpecDefault with Generators {

  override def spec: Spec[TestEnvironment & Scope, Any] =
    suite("DefaultRoutes")(
      test("can handle all non mapped routes successfully and logs the incoming request") {
        check(methodGen, urlGen) { (method, url) =>
          (for {
            defaultRoute <- ZIO.service[DefaultRoutesAlg]
            request = Request(
              method = method,
              url = url
            )
            response <- defaultRoute.routes(request)
            loggerChunk <- ZTestLogger.logOutput
            logMessages = loggerChunk.map(_.message()).toSet
            resBody <- response.body.asArray
          } yield assertTrue(
            response.status == zio.http.Status.NotImplemented,
            resBody sameElements "Incorrect endpoint".getBytes,
            logMessages == Set("Http request served")
          ))
            .provide(
              DefaultRoutes.live,
              Runtime.removeDefaultLoggers >>> ZTestLogger.default
            )
        }
      }
    )

}
