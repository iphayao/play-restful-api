package controllers

import javax.inject.{Inject, Singleton}
import models.PostRepository
import play.api.Logger
import play.api.data.Form
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.ExecutionContext

case class PostFormInput(title: String, body: String)

@Singleton
class PostController @Inject()(pcc: PostControllerComponent)(implicit ec: ExecutionContext) extends PostBaseController(pcc) {
  private val logger = Logger(this.getClass)

  private val form: Form[PostFormInput] = {
    import play.api.data.Forms._
    Form(
      mapping(
        "title" -> nonEmptyText,
        "body" -> text
      )(PostFormInput.apply)(PostFormInput.unapply)
    )
  }

  def index() : Action[AnyContent] = Action.async { implicit request =>
    logger.trace("index: ")
    postResourceHandler.find.map { posts =>
      Ok(Json.toJson(posts))
    }
  }

  def get(id: Long): Action[AnyContent] = Action.async { implicit request =>
    logger.trace(s"show: id = $id")
    postResourceHandler.lookup(id.toString).map { post =>
      Ok(Json.toJson(post))
    }
  }

  def create(): Action[AnyContent] = Action.async { implicit  request =>
    logger.trace("create: ")
    val post = form.bindFromRequest.get
    postResourceHandler.create(post).map { post =>
      Created(Json.toJson(post))
    }
  }

  // PATCH -> /:id
  def update(id: Long): Action[AnyContent] = Action { implicit request =>
    Ok(s"Updated $id")
  }

  // DELETE -> /:id
  def delete(id: Long): Action[AnyContent] = Action { implicit request =>
    Ok(s"Deleted $id")
  }

}
