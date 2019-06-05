import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BasicAuthenticationService } from '../service/basic-authentication.service';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css']
})
export class WelcomeComponent implements OnInit {

  username : string = ''
  welcomeMessageFromService : string
  constructor(
    private router : Router,
    private basicAuthService : BasicAuthenticationService
    ) { }

  ngOnInit() {
    this.username = this.basicAuthService.getAuthenticatedUserUsername()
  }

  manageAccounts() {
    this.router.navigate(['my_accounts'])
  }
}
