import com.google.inject.AbstractModule
import javax.inject.Singleton
import models.{PostRepository, PostRepositoryImpl}
import net.codingwell.scalaguice.ScalaModule
import play.api.{Configuration, Environment}

class Module(environment: Environment, configuration: Configuration) extends AbstractModule with ScalaModule {
  override def configure() = {
    bind[PostRepository].to[PostRepositoryImpl].in[Singleton]
  }
}
