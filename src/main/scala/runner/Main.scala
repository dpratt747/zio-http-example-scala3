package runner

import http.endpoints.{DefaultRoutes, DefaultRoutesAlg, HealthRoutes}
import zio.http.Server
import zio.{ZIO, ZIOAppDefault}

object Main extends ZIOAppDefault {

  override def run: ZIO[Any, Throwable, Unit] =
    (for {
//      healthRoute <- ZIO.service[HealthRoutesAlg]
      defaultRoute <- ZIO.service[DefaultRoutesAlg]
      _ <- Server.serve(HealthRoutes.routes ++ defaultRoute.routes)
      _ <- ZIO.logInfo("Application running on port localhost:8080")
    } yield ()).provide(
//      HealthRoutes.live,
      DefaultRoutes.live,
      Server.default
    )

}
