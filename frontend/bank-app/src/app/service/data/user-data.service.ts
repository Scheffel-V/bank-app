import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { User } from 'src/app/user/user.component';

@Injectable({
  providedIn: 'root'
})
export class UserDataService {

  constructor(
    private http : HttpClient
  ) { }

  getAllUsers() {
    return this.http.get<User>('http://localhost:8080/users')
  }

  getUserById(id : string) {
    return this.http.get<User[]>(`http://localhost:8080/users/${id}`)
  }
}
