import { Component, OnInit } from '@angular/core';
import { Transaction } from '../transaction/transaction.component';


export class Account {
  
  constructor(
    public id : number,
    public amount : number,
    public transactions : Transaction[]
  ) {

  }

}

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
