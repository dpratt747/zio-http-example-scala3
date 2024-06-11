package http.endpoints

import zio.{ZIO, ZLayer, http}
import zio.http.*


trait HealthRoutesAlg {
  def routes: Routes[Any, Nothing]
}

final case class HealthRoutes() extends HealthRoutesAlg {

  def routes: Routes[Any, Nothing] = Routes(
    Method.GET / "status" -> handler { (_: Request) =>
        Response
          .json("Hello World")
          .status(Status.Ok)
    } @@ Middleware.requestLogging()
  )

}

object HealthRoutes {
  val live: ZLayer[Any, Nothing, HealthRoutesAlg] = ZLayer.fromFunction(() => HealthRoutes.apply())
}