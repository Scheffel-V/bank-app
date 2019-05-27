import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class HardcodedAuthenticationService {

  constructor() { }

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

}
