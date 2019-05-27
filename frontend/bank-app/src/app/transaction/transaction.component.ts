import { Component, OnInit } from '@angular/core';
import { Account } from '../account/account.component';


export class Transaction {
  
  constructor(
    public id : number,
    public amount : number,
    public originAccount : Account,
    public destinyAccount : Account
  ) {

  }

}

@Component({
  selector: 'app-transaction',
  templateUrl: './transaction.component.html',
  styleUrls: ['./transaction.component.css']
})
export class TransactionComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
