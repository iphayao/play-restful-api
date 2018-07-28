package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc._

@Singleton
class PostController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  // GET -> /
  def index() : Action[AnyContent] = Action { implicit request =>
    Ok("Get List")
  }

  // GET -> /:id
  def get(id: Long): Action[AnyContent] = Action { implicit request =>
    Ok(s"Get id: $id")
  }

  // POST -> /
  def create(): Action[AnyContent] = Action { implicit  request =>
    Created("Created")
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
