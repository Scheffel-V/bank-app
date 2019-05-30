import { Component, OnInit } from '@angular/core';
import { Transaction } from '../transaction/transaction.component';
import { TransactionDataService } from '../service/data/transaction-data.service';
import { BasicAuthenticationService } from '../service/basic-authentication.service';
import { AccountDataService } from '../service/data/account-data.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Account } from '../account/account.component';
import { Location } from '@angular/common';

@Component({
  selector: 'app-list-transactions',
  templateUrl: './list-transactions.component.html',
  styleUrls: ['./list-transactions.component.css']
})
export class ListTransactionsComponent implements OnInit {

  transactions : Transaction[]
  account : Account = new Account(-1, -1, [])
  message : string

  constructor(
    private accountService : AccountDataService,
    private transactionService : TransactionDataService,
    private basicAuthService : BasicAuthenticationService,
    private router : Router,
    private activatedRoute : ActivatedRoute,
    private location : Location
  ) { }

  ngOnInit() {
    this.accountService.getAccount(
      this.basicAuthService.getAuthenticatedUserId(),
      this.activatedRoute.snapshot.params['accountId']
    ).subscribe(
      data => {
        this.account = data
        this.refreshTransactions()
      }
    )
  }

  refreshTransactions() {
    this.transactionService.getAllTransactionsByUserAndAccountId(
      this.basicAuthService.getAuthenticatedUserId(),
      this.account.id + ""
      ).subscribe(
      response => {
        this.transactions = response;
      }
    )
  }

  createTransaction() {
    this.router.navigate(['my_accounts', this.account.id, 'my_transactions', -1])
  }
  
  updateTransaction(transactionId : string) {
    this.router.navigate(['my_accounts', this.account.id, 'my_transactions', transactionId])
  }

  deleteTransaction(transactionId : string) {
    this.transactionService.deleteTransaction(
      this.basicAuthService.getAuthenticatedUserId(),
      this.account.id + "",
      transactionId
      ).subscribe(
      response => {
        this.message = `Deleted transaction!`
        this.refreshTransactions()
      }
    )
  }

  backPage() {
    this.location.back()
  }
}
