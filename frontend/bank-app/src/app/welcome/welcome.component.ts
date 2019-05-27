import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { WelcomeDataService } from '../service/data/welcome-data.service';

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
    private dataService : WelcomeDataService
    ) { }

  ngOnInit() {
    this.username = this.route.snapshot.params['username']
  }

  getWelcomeMessage() {
    this.dataService.executeHelloWorldBeanService().subscribe(
      response => this.handleSucessfulResponse(response),
      error => this.handleErrorResponse(error)
    )
  }

  getWelcomeMessageWithParameter() {
    this.dataService.executeHelloWorldServiceWithPathVariable(this.username).subscribe(
      response => this.handleSucessfulResponse(response),
      error => this.handleErrorResponse(error)
    )
  }

  handleSucessfulResponse(response) {
    console.log(response);
    this.welcomeMessageFromService = response
  }

  handleErrorResponse(error) {
    this.welcomeMessageFromService = error
  }
}
