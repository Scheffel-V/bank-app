import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler } from '@angular/common/http';
import { BasicAuthenticationService } from '../basic-authentication.service';

@Injectable({
  providedIn: 'root'
})
export class HttpIntercepterBasicAuthService implements HttpInterceptor {

  constructor(
    private basicAuthenticationService : BasicAuthenticationService
  ) { }

  intercept(request : HttpRequest<any>, next : HttpHandler) {
    let basicAuthHeaderString = this.basicAuthenticationService.getAuthenticatedToken()
    let userId = this.basicAuthenticationService.getAuthenticatedUserId()

    if(basicAuthHeaderString && userId) {
      request = request.clone(
        {
          setHeaders : {
            Authorization : basicAuthHeaderString
          }
        }
      )
    }
    return next.handle(request)
  }
}
