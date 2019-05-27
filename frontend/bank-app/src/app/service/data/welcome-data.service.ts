import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class WelcomeDataService {

  constructor(
    private http : HttpClient
  ) { }

  executeHelloWorldBeanService() {
    return this.http.get('http://localhost:8080/users')
  }

  executeHelloWorldServiceWithPathVariable(id:string) {
    return this.http.get(`http://localhost:8080/users/${id}`)
  }
}
