package http.endpoints

import helpers.Generators
import zio.*
import zio.http.{Method, Request, URL}
import zio.test.*

object HealthRoutesSpec extends ZIOSpecDefault with Generators {

  override def spec: Spec[TestEnvironment & Scope, Any] =
    suite("HealthRoutes")(
      test("can handle a status request") {
        (for {
          healthRoutes <- ZIO.service[HealthRoutesAlg]
          request = Request(
            method = Method.GET,
            url = URL.decode("status").getOrElse(URL.empty)
          )
          response <- healthRoutes.routes(request)
          loggerChunk <- ZTestLogger.logOutput
          logMessages = loggerChunk.map(_.message())
        } yield assertTrue(
          response.status == zio.http.Status.Ok,
          logMessages.toSet == Set(
            "Http request served"
          )
        ))
          .provide(
            HealthRoutes.live,
            Runtime.removeDefaultLoggers >>> ZTestLogger.default
          )
      },
      test("can handle invalid requests") {
        (for {
          healthRoutes <- ZIO.service[HealthRoutesAlg]
          request = Request(
            method = Method.GET,
            url = URL.decode("notImplemented").getOrElse(URL.empty)
          )
          response <- healthRoutes.routes(request)
        } yield assertTrue(
          response.status == zio.http.Status.NotFound,
        ))
          .provide(
            HealthRoutes.live,
            Runtime.removeDefaultLoggers >>> ZTestLogger.default
          )
      }
    )

}
