import { Component, OnInit } from '@angular/core';
import { Transaction } from '../transaction/transaction.component';
import { AccountDataService } from '../service/data/account-data.service';
import { BasicAuthenticationService } from '../service/basic-authentication.service';
import { ActivatedRoute, Router } from '@angular/router';
import { TransactionDataService } from '../service/data/transaction-data.service';
import { Location } from '@angular/common';

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

  id : string
  account : Account
  transactions : Transaction[]

  constructor(
    private accountService : AccountDataService,
    private basicAuthService : BasicAuthenticationService,
    private activatedRoute : ActivatedRoute,
    private router : Router,
    private location : Location
    ) { }

  ngOnInit() {
    this.id = this.activatedRoute.snapshot.params['accountId']
    this.account = new Account(+this.id, 0, [])

    if(+this.id != -1) {
      this.accountService.getAccount(
        this.basicAuthService.getAuthenticatedUserId(),
        this.id
        ).subscribe(
          data => {
            this.account = data
            this.transactions = this.account.transactions
          }
        )
    }
  }

  saveAccount() {
    if(+this.id == -1) {
      this.accountService.createAccount(
        this.basicAuthService.getAuthenticatedUserId(),
        this.account
          ).subscribe(
        data => {
          this.location.back()
        }
      )
    } else {
      this.accountService.updateAccount(
        this.basicAuthService.getAuthenticatedUserId(),
        this.id,
        this.account
        ).subscribe(
        data => {
          this.location.back()
        }
      )
    }
  }

  createTransaction() {
    this.router.navigate(['my_accounts', this.id, 'my_transactions', -1])
  }

}
