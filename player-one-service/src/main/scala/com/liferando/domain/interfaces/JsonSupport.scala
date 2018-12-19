package com.liferando.domain.interfaces

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.liferando.domain.model.UserRegistryActor.ActionPerformed
import com.liferando.domain.model.{Player, User, Users}
import spray.json.DefaultJsonProtocol

trait JsonSupport extends SprayJsonSupport {
  // import the default encoders for primitive types (Int, String, Lists etc)
  import DefaultJsonProtocol._

  implicit val userJsonFormat = jsonFormat3(User)
  implicit val usersJsonFormat = jsonFormat1(Users)
  implicit val playerJsonFormat = jsonFormat1(Player)

  implicit val actionPerformedJsonFormat = jsonFormat1(ActionPerformed)
}
