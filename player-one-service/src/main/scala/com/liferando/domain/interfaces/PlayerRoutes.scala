package com.liferando.domain.interfaces

import akka.actor.{ActorRef, ActorSystem}
import akka.event.Logging
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.MethodDirectives.{delete, get, post}
import akka.http.scaladsl.server.directives.PathDirectives.path
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import akka.pattern.ask
import akka.util.Timeout
import com.liferando.domain.model.UserRegistryActor._
import com.liferando.domain.model.{User, Users}

import scala.concurrent.Future
import scala.concurrent.duration._

//#user-routes-class
trait PlayerRoutes extends JsonSupport {
  //#user-routes-class

  // we leave these abstract, since they will be provided by the App
  implicit def system: ActorSystem

  // other dependencies that UserRoutes use
  def userRegistryActor: ActorRef

  // Required by the `ask` (?) method below
  implicit lazy val timeout = Timeout(5.seconds) // usually we'd obtain the timeout from the system's configuration

  //#all-routes
  //#users-get-post
  //#users-get-delete
  lazy val playerRoutes: Route =
  pathPrefix("play") {
    concat(
      //#users-get-delete
      pathEnd {
        concat(
          get {
            val users: Future[Users] =
              (userRegistryActor ? GetUsers).mapTo[Users]
            complete(users)
          },
          post {
            entity(as[User]) { user =>
              val userCreated: Future[ActionPerformed] =
                (userRegistryActor ? CreateUser(user)).mapTo[ActionPerformed]
              onSuccess(userCreated) { performed =>
                complete((StatusCodes.Created, performed))
              }
            }
          })
      },
      //#users-get-post
      //#users-get-delete
      path(Segment) { name =>
        concat(
          get {
            //#retrieve-user-info
            val maybeUser: Future[Option[User]] =
              (userRegistryActor ? GetUser(name)).mapTo[Option[User]]
            rejectEmptyResponse {
              complete(maybeUser)
            }
            //#retrieve-user-info
          },
          delete {
            //#users-delete-logic
            val userDeleted: Future[ActionPerformed] =
              (userRegistryActor ? DeleteUser(name)).mapTo[ActionPerformed]
            onSuccess(userDeleted) { performed =>
              complete((StatusCodes.OK, performed))
            }
            //#users-delete-logic
          })
      })
    //#users-get-delete
  }
}