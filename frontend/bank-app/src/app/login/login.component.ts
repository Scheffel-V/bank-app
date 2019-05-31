import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BasicAuthenticationService } from '../service/basic-authentication.service';
import { User } from '../user/user.component';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  user : User = new User(-1, "", "", [])
  invalidLoginMessage : string = "Invalid username or password"
  invalidLogin = false
  message : string
  
  constructor(
    private router : Router,
    private basicAuthenticationService : BasicAuthenticationService
    ) { }

  ngOnInit() {
  }

  handleBasicAuthLogin() {
    this.basicAuthenticationService.executeJWTAuthenticationService(this.user.username, this.user.password).subscribe(
      data => {
        this.router.navigate(['welcome', this.user.username])
        this.invalidLogin = false
      },
      error => {
        this.invalidLogin = true
      }
    )
  }

  goToSignInPage() {
    this.router.navigate(['sign_in'])
  }
}
