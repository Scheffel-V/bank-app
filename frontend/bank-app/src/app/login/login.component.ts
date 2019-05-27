import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HardcodedAuthenticationService } from '../service/hardcoded-authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username : string = ""
  password : string = ""
  invalidLoginMessage : string = "Invalid username or password"
  invalidLogin = false
  
  constructor(private router : Router,
    private authenticationService : HardcodedAuthenticationService) { }

  ngOnInit() {
  }

  handleLogin() {
    if(this.authenticationService.authenticate(this.username, this.password)) {
      this.router.navigate(['welcome', this.username])
      this.invalidLogin = false
    } else {
      this.invalidLogin = true
    }
  }
}
