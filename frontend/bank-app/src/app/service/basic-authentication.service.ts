import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { User } from '../user/user.component';
import { AUTHENTICATED_TOKEN, API_URL, AUTHENTICATION_URL, AUTHENTICATED_USER_USERNAME, AUTHENTICATED_USER_ID } from '../app.constants';

@Injectable({
  providedIn: 'root'
})
export class BasicAuthenticationService {

  constructor(private http : HttpClient) { }

  executeJWTAuthenticationService(username : string, password : string) {
    let header = this.createBasicAuthenticationHttpHeader(username, password)

    return this.http.post<any>(
      AUTHENTICATION_URL,
      {
        username,
        password
      }
    ).pipe(
      map(
        data => {
          sessionStorage.setItem(AUTHENTICATED_USER_USERNAME, username)
          sessionStorage.setItem(AUTHENTICATED_USER_ID, `${data[1].id}`)
          sessionStorage.setItem(AUTHENTICATED_TOKEN, `Bearer ${data[0].token}`)
          return data
        }
      )
    )
  }

  logout() {
    sessionStorage.removeItem(AUTHENTICATED_USER_ID)
    sessionStorage.removeItem(AUTHENTICATED_USER_USERNAME)
    sessionStorage.removeItem(AUTHENTICATED_TOKEN)
  }

  isUserLoggedIn() {
    let user = sessionStorage.getItem(AUTHENTICATED_USER_ID)
    return !(user === null)
  }

  getAuthenticatedUserId() {
    return sessionStorage.getItem(AUTHENTICATED_USER_ID)
  }

  getAuthenticatedUserUsername() {
    return sessionStorage.getItem(AUTHENTICATED_USER_USERNAME)
  }

  getAuthenticatedToken() {
    if(this.getAuthenticatedUserId()) {
      return sessionStorage.getItem(AUTHENTICATED_TOKEN)
    }
    return "Doesn't exist"
  }

  private createBasicAuthenticationHttpHeader(username : string, password : string) {
    let basicAuthHeaderString = 'Basic ' + window.btoa(username + ":" + password)
    
    return new HttpHeaders({
      Authorization : basicAuthHeaderString
    })
  }
}
