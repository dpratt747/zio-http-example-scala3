package http.endpoints

import zio.http.*
import zio.*

trait DefaultRoutesAlg {
  def routes: Routes[Any, Nothing]
}

final case class DefaultRoutes() extends DefaultRoutesAlg {
  override def routes: Routes[Any, Nothing] = Routes(
    RoutePattern.any -> handler { (_: Request) =>
      Response.json("Incorrect endpoint").status(Status.NotImplemented)
    }
  ) @@ Middleware.requestLogging()
}

object DefaultRoutes {
  val live: ZLayer[Any, Nothing, DefaultRoutesAlg] = ZLayer.fromFunction(() => DefaultRoutes.apply())
}
