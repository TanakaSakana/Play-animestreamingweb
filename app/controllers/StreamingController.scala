package controllers

import javax.inject._

import akka.stream.scaladsl.{FileIO, Source}
import akka.util.ByteString
import play.Play
import play.api.mvc._
import services.FileVisitorA

@Singleton
class StreamingController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def video(episode: String) = Action {
    episode match {
      case "0" =>
        Ok(views.html.index(
          FileVisitorA.listDirectory((Play.application().path().toString + "/public/yuyuyu"))
            .toArray
            .toList
            .map(_.asInstanceOf[String])
            .map(_.replace(".mp4", ""))
        ,"Click to watch video"))
      case _ => Ok(views.html.index(
        FileVisitorA.listDirectory((Play.application().path().toString + "/public/yuyuyu"))
          .toArray
          .toList
          .map(_.asInstanceOf[String])
          .map(_.replace(".mp4", "")
      ),episode))
    }
  }
/*
*     Ok.chunked(Result(
      ResponseHeader(200),
      body = HttpEntity.Streamed(source, Some(file.length), Some("video/mp4"))
      // body = HttpEntity.Streamed(source, Some(file.length), Some("video/mp4"))
    ).as("video/mp4"))
* */
  def getMovie(episode: String): Action[AnyContent] = Action {
    val file = new java.io.File(Play.application().path() + "/public/yuyuyu/" + episode + ".mp4")
    val path: java.nio.file.Path = file.toPath
    val source: Source[ByteString, _] = FileIO.fromPath(path)
    Ok.chunked(source).as("video/mp4")
      // body = HttpEntity.Streamed(source, Some(file.length), Some("video/mp4"))
  }
}
