package models

import akka.actor.ActorSystem
import javax.inject.{Inject, Singleton}
import play.api.MarkerContext
import play.api.libs.concurrent.CustomExecutionContext

import scala.concurrent.{ExecutionContext, Future}

final case class PostData(id: PostId, title: String, body: String)

class PostId private (val underlying: Int) extends AnyVal {
  override def toString: String = underlying.toString
}

object PostId {
  def apply(raw: String): PostId = {
    require(raw != null)
    new PostId(Integer.parseInt(raw))
  }
}

//class PostExecutionContext @Inject()(actorSystem: ActorSystem) extends CustomExecutionContext(actorSystem, "repository.dispatcher")

trait PostRepository {
  def create(data: PostData)(implicit mc: MarkerContext): Future[PostId]
  def list()(implicit mc: MarkerContext): Future[Iterable[PostData]]
  def get(id: PostId)(implicit mc: MarkerContext): Future[Option[PostData]]
//  def update(id: PostId, value: PostData)(implicit mc: MarkerContext): Future[Option[PostData]]
//  def delete(id: PostId)(implicit mc: MarkerContext): Future[PostId]
}

@Singleton
class PostRepositoryImpl @Inject()()(implicit ec: ExecutionContext) extends PostRepository {
  private var postList = List(
    PostData(PostId("1"), "title1", "blog post 1"),
    PostData(PostId("2"), "title2", "blog post 2"),
    PostData(PostId("3"), "title3", "blog post 3"),
    PostData(PostId("4"), "title4", "blog post 4"),
    PostData(PostId("5"), "title5", "blog post 5"),
  )

  override def create(data: PostData)(implicit mc: MarkerContext): Future[PostId] = {
    Future {
      postList = postList :+ data
      data.id
    }
  }

  override def list()(implicit mc: MarkerContext): Future[Iterable[PostData]] = {
    Future {
      postList
    }
  }

  override def get(id: PostId)(implicit mc: MarkerContext): Future[Option[PostData]] = {
    Future {
      postList.find(post => post.id == id)
    }
  }

//  override def update(id: PostId, postData: PostData)(implicit mc: MarkerContext): Future[Option[PostData]] = {
//    Future {
//      var p = postList.find(post => post.id == id)
//      //postLis t diff List(p)
//      p
//    }
//  }
//
//  override def delete(id: PostId)(implicit mc: MarkerContext): Future[PostId] = {
//    Future {
//      val p = postList.find(post => post.id == id)
//      //postList diff List(p)
//      p.id
//    }
//  }
}
