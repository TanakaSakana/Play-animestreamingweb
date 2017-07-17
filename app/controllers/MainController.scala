package controllers

import javax.inject._

import play.Play
import play.api.mvc._
import services.{Counter, FileVisitorA}

@Singleton
class MainController @Inject() (cc: ControllerComponents, counter: Counter) extends AbstractController(cc) {

  def redirect= Action { Redirect("/video") }

  def index = Action{Ok(views.html.index(
    FileVisitorA.listDirectory((Play.application().path().toString + "/public/yuyuyu"))
      .toArray
      .toList
      .map(_.asInstanceOf[String])
      .map(_.replace(".mp4",""))
  ,"Click to watch video"))}
}
