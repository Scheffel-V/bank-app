import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css']
})
export class WelcomeComponent implements OnInit {

  username : string = ''
  welcomeMessageFromService : string
  constructor(
    private route : ActivatedRoute
    ) { }

  ngOnInit() {
    this.username = this.route.snapshot.params['username']
  }
}
