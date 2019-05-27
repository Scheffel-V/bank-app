import { Component, OnInit } from '@angular/core';
import { Account } from '../account/account.component';


export class User {
  
  constructor(
    public id : number,
    public username : string,
    public password : string,
    public accounts : Account[]
  ) {

  }

}

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
