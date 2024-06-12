//package http.endpoints
//
//import zio.*
//import zio.http.*
//import zio.http.codec.*
//import zio.http.codec.PathCodec.*
//import zio.http.endpoint.*
//import zio.http.endpoint.openapi.*
//import zio.schema.annotation.description
//import zio.schema.{DeriveSchema, Schema}
//
//final case class SwaggerRoutes {
//
//  val openAPI = OpenAPIGen.fromEndpoints(title = "Library API", version = "1.0", endpoint)
//
//  val routes = SwaggerUI.routes("docs" / "openapi", openAPI)
//
//}
