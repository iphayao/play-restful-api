package models

import controllers.PostFormInput
import javax.inject.{Inject, Provider}
import play.api.MarkerContext
import play.api.inject.RoutesProvider
import play.api.libs.json.{JsValue, Json, Writes}
import routers.PostRouter

import scala.concurrent.{ExecutionContext, Future}


case class PostResource(id: String, link: String, title: String, body: String)

object PostResource {
  implicit val implicitWrites = new Writes[PostResource] {
    override def writes(post: PostResource): JsValue = {
      Json.obj(
        "id" -> post.id,
        "link" -> post.link,
        "title" -> post.title,
        "body" -> post.body
      )
    }
  }
}

class PostResourceHandler @Inject()(routesProvider: Provider[PostRouter],
                                    postRepository: PostRepository)
                                    (implicit ec: ExecutionContext) {

  def create(postInput: PostFormInput)(implicit mc: MarkerContext): Future[PostResource] = {
    val data = PostData(PostId("9999"), postInput.title, postInput.body)
    postRepository.create(data).map { _ =>
      createPostResource(data)
    }
  }

  def lookup(id: String)(implicit mc: MarkerContext): Future[Option[PostResource]] = {
    val postFuture = postRepository.get(PostId(id))
    postFuture.map { posts =>
      posts.map { postData => createPostResource(postData)}
    }
  }

  def find(implicit mc: MarkerContext): Future[Iterable[PostResource]] = {
    postRepository.list().map { postDataList =>
      postDataList.map(postData => createPostResource(postData))
    }
  }

  def createPostResource(p: PostData): PostResource = {
    PostResource(p.id.toString, routesProvider.get().link(p.id), p.title, p.body)
  }

}
