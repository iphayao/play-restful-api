import javax.inject.Inject
import play.api.http._
import play.api.mvc._
import play.api.mvc.request.RequestTarget
import play.api.routing.Router

class RequestHandler @Inject()(router: Router,
                               errorHandler: HttpErrorHandler,
                               configuration: HttpConfiguration,
                               filters: HttpFilters)
      extends DefaultHttpRequestHandler (router, errorHandler, configuration, filters) {

  override def handlerForRequest(request: RequestHeader): (RequestHeader, Handler) = {
    super.handlerForRequest(addTrailingSlash(request))
  }

  def addTrailingSlash(req: RequestHeader): RequestHeader = {
    if(!req.path.endsWith("/")) {
      val path = req.path + "/"
      if(req.rawQueryString.isEmpty)
        req.withTarget(RequestTarget(path = path, uriString = path, queryString = Map()))
      else
        req.withTarget(RequestTarget(path = path, uriString = req.uri, queryString = req.queryString))
    } else {
      req
    }
  }
}
