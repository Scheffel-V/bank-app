import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { User } from '../user/user.component';

@Injectable({
  providedIn: 'root'
})
export class BasicAuthenticationService {

  constructor(private http : HttpClient) { }

  authenticate(username, password) {
    if(username ==="1" && password === "Bludakeia1") {
      sessionStorage.setItem('authenticateUser', username)
      return true
    }
    return false
  }

  logout() {
    sessionStorage.removeItem('authenticateUser')
  }

  isUserLoggedIn() {
    let user = sessionStorage.getItem('authenticateUser')
    return !(user === null)
  }

  executeBasicAuthenticationService(username : string, password : string) {
    return this.http.get<User>(
      `http://localhost:8080/basicauth`,
      {
        headers : this.createBasicAuthenticationHttpHeader(username, password)
      }
    ).pipe(
      map(
        data => {
          sessionStorage.setItem('authenticateUser', username)
          return data
        }
      )
    )
  }

  createBasicAuthenticationHttpHeader(username : string, password : string) {
    let basicAuthHeaderString = 'Basic ' + window.btoa(username + ":" + password)
    
    return new HttpHeaders({
      Authorization : basicAuthHeaderString
    })
  }
}
