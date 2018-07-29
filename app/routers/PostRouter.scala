package routers

import controllers.PostController
import javax.inject.Inject
import models.PostId
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

class PostRouter @Inject()(controller: PostController) extends SimpleRouter {
  val prefix = "/api/posts"

  def link(id: PostId): String = {
    import com.netaporter.uri.dsl._
    val url = prefix / id.toString
    url.toString
  }

  override def routes: Routes = {
    case GET(p"/") =>
      controller.index

    case GET(p"/${long(id)}") =>
      controller.get(id)

    case POST(p"/") =>
      controller.create()

//    case PATCH(p"/${long(id)}") =>
//      controller.update(id)
//
//    case DELETE(p"/${long(id)}") =>
//      controller.delete(id)
  }
}
