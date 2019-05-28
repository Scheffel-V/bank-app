import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { User } from '../user/user.component';

@Injectable({
  providedIn: 'root'
})
export class BasicAuthenticationService {

  constructor(private http : HttpClient) { }

  executeBasicAuthenticationService(username : string, password : string) {
    let header = this.createBasicAuthenticationHttpHeader(username, password)

    return this.http.get<User>(
      `http://localhost:8080/basicauth`,
      {
        headers : header
      }
    ).pipe(
      map(
        data => {
          sessionStorage.setItem('authenticateUser', username)
          sessionStorage.setItem('token', header.get('Authorization'))
          return data
        }
      )
    )
  }

  logout() {
    sessionStorage.removeItem('authenticateUser')
  }

  isUserLoggedIn() {
    let user = sessionStorage.getItem('authenticateUser')
    return !(user === null)
  }

  getAuthenticatedUser() {
    return sessionStorage.getItem('authenticateUser')
  }

  getAuthenticatedToken() {
    if(this.getAuthenticatedUser()) {
      return sessionStorage.getItem('token')
    }
  }

  private createBasicAuthenticationHttpHeader(username : string, password : string) {
    let basicAuthHeaderString = 'Basic ' + window.btoa(username + ":" + password)
    
    return new HttpHeaders({
      Authorization : basicAuthHeaderString
    })
  }
}
