import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders} from '@angular/common/http';
import { User } from 'src/app/user/user.component';

@Injectable({
  providedIn: 'root'
})
export class UserDataService {

  constructor(
    private http : HttpClient
  ) { }

  getAllUsers() {
    return this.http.get<User>(
      'http://localhost:8080/users',
      { 
        headers : this.createBasicAuthenticationHttpHeader()
      }
    )
  }

  getUserById(id : string) {
    return this.http.get<User[]>(`http://localhost:8080/users/${id}`)
  }

  createBasicAuthenticationHttpHeader() {
    let username = 'brsufirefox'
    let password = 'Bludakeia1'
    let basicAuthHeaderString = 'Basic ' + window.btoa(username + ":" + password)
    
    return new HttpHeaders({
      Authorization : basicAuthHeaderString
    })
  }
}
