import { Component, OnInit } from '@angular/core';
import { Transaction } from '../transaction/transaction.component';
import { TransactionDataService } from '../service/data/transaction-data.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-all-transactions',
  templateUrl: './all-transactions.component.html',
  styleUrls: ['./all-transactions.component.css']
})
export class AllTransactionsComponent implements OnInit {

  transactions : Transaction[]
  message : string
  userId : string = '-1'
  accountId : string = '-1'

  constructor(
    private transactionService : TransactionDataService,
    private router : Router,
    private activatedRoute : ActivatedRoute,
    private location : Location
  ) { }

  ngOnInit() {
    let id = this.activatedRoute.snapshot.params['accountId']
    this.userId = this.activatedRoute.snapshot.params['userId']
    if(id) {
      this.accountId = id
    }
    if(this.accountId == '-1') {
      this.transactionService.getAllTransactions().subscribe(
        data => {
          console.log(data)
          this.transactions = data
        }
      )
    } else {
      this.transactionService.getAllTransactionsByUserAndAccountId(
        this.userId,
        this.accountId
        ).subscribe(
        data => {
          this.transactions = data
        }
      )
    }
  }

  viewTransactions(transactionId : string) {
    this.router.navigate(['all_transactions', transactionId])
  }

  backPage() {
    this.location.back()
  }

  finalizeTransaction(transactionId : string) {
    
  }
}
