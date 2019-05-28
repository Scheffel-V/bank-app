import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { User } from '../user/user.component';
import { AUTHENTICATED_USER, AUTHENTICATED_TOKEN, API_URL } from '../app.constants';

@Injectable({
  providedIn: 'root'
})
export class BasicAuthenticationService {

  constructor(private http : HttpClient) { }

  executeJWTAuthenticationService(username : string, password : string) {
    let header = this.createBasicAuthenticationHttpHeader(username, password)

    return this.http.post<any>(
      `${API_URL}/authenticate`,
      {
        username,
        password
      }
    ).pipe(
      map(
        data => {
          sessionStorage.setItem(AUTHENTICATED_USER, username)
          sessionStorage.setItem(AUTHENTICATED_TOKEN, `Bearer ${data.token}`)
          console.log(sessionStorage.getItem(AUTHENTICATED_TOKEN))
          console.log("DATA::::::::")
          console.log(data)
          return data
        }
      )
    )
  }

  logout() {
    sessionStorage.removeItem(AUTHENTICATED_USER)
  }

  isUserLoggedIn() {
    let user = sessionStorage.getItem(AUTHENTICATED_USER)
    return !(user === null)
  }

  getAuthenticatedUser() {
    return sessionStorage.getItem(AUTHENTICATED_USER)
  }

  getAuthenticatedToken() {
    if(this.getAuthenticatedUser()) {
      return sessionStorage.getItem(AUTHENTICATED_TOKEN)
    }
  }

  private createBasicAuthenticationHttpHeader(username : string, password : string) {
    let basicAuthHeaderString = 'Basic ' + window.btoa(username + ":" + password)
    
    return new HttpHeaders({
      Authorization : basicAuthHeaderString
    })
  }
}
