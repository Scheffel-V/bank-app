import { Component, OnInit } from '@angular/core';
import { Account } from '../account/account.component';
import { Router, ActivatedRoute } from '@angular/router';
import { BasicAuthenticationService } from '../service/basic-authentication.service';
import { AccountDataService } from '../service/data/account-data.service';
import { TransactionDataService } from '../service/data/transaction-data.service';
import { Location } from '@angular/common';


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

  id : string
  transaction : Transaction
  destinyAccountId : string

  constructor(
    private accountService : AccountDataService,
    private transactionService : TransactionDataService,
    private basicAuthService : BasicAuthenticationService,
    private activatedRoute : ActivatedRoute,
    private router : Router,
    private location : Location
    ) { }

  ngOnInit() {
    this.id = this.activatedRoute.snapshot.params['transactionId']
    this.transaction = new Transaction(+this.id, 0, new Account(0, 0, []), new Account(0, 0, []))

    if(+this.id != -1) {
      this.transactionService.getTransaction(
        this.basicAuthService.getAuthenticatedUserId(),
        this.activatedRoute.snapshot.params['accountId'],
        this.id
        ).subscribe(
          data => {
            this.transaction = data
          }
        )
    }
  }

  saveTransaction() {
    if(+this.id == -1) {
      this.transactionService.createTransaction(
        this.basicAuthService.getAuthenticatedUserId(),
        this.activatedRoute.snapshot.params['accountId'],
        this.transaction
        ).subscribe(
        data => {
          this.location.back()
        }
      )
    } else {
      this.transactionService.updateTransaction(
        this.basicAuthService.getAuthenticatedUserId(),
        this.activatedRoute.snapshot.params['accountId'],
        this.id,
        this.transaction
        ).subscribe(
        data => {
          this.location.back()
        }
      )
    }
  }
}