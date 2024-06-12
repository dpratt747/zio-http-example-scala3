package http.endpoints

import zio.http.*
import zio.http.codec.PathCodec.*
import zio.http.codec.{Doc, HttpContentCodec}
import zio.http.endpoint.EndpointMiddleware.None
import zio.http.endpoint.openapi.{OpenAPI, OpenAPIGen, SwaggerUI}
import zio.http.endpoint.{Endpoint, EndpointMiddleware}
import zio.schema.Schema
import zio.{ZIO, ZNothing, http}

object HealthRoutes {

  private val statusEndpoint: Endpoint[Unit, Unit, ZNothing, String, None] =
    Endpoint((Method.GET / "status") ?? Doc.p("Route for getting application status"))
      .out[String]


  private val statusRoute: Route[Any, Nothing] = statusEndpoint.implement {
    Handler.fromZIO(
      ZIO.succeed("Everything is okay")
    )
  }

  private val openAPI: OpenAPI = OpenAPIGen.fromEndpoints(title = "Library API", version = "1.0", statusEndpoint)

  val routes: Routes[Any, Response] = Routes(statusRoute) ++ SwaggerUI.routes("docs" / "openapi", openAPI)


}