import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css']
})
export class WelcomeComponent implements OnInit {

  username : string = ''
  welcomeMessageFromService : string
  constructor(
    private route : ActivatedRoute,
    private router : Router
    ) { }

  ngOnInit() {
    this.username = this.route.snapshot.params['username']
  }

  manageAccounts() {
    this.router.navigate(['my_accounts'])
  }
}
