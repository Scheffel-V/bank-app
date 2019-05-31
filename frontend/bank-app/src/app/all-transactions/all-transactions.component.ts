import { Component, OnInit } from '@angular/core';
import { Transaction } from '../transaction/transaction.component';
import { TransactionDataService } from '../service/data/transaction-data.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { AccountDataService } from '../service/data/account-data.service';
import { UserDataService } from '../service/data/user-data.service';
import { User } from '../user/user.component';
import { Account } from '../account/account.component';

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
  users : User[]
  accounts : Account[]
  usersAccounts = new Map() 
  accountsTransactions = new Map()

  constructor(
    private transactionService : TransactionDataService,
    private accountService : AccountDataService,
    private userService : UserDataService,
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
      this.userService.getAllUsers().subscribe(
        data => {
          this.users = data
          for(let user of this.users) {
            this.accountService.getAllAccountsByUserId(user.id + "").subscribe(
              data => {
                this.usersAccounts.set(user.id, data)
                for(let account of data) {
                  this.transactionService.getAllTransactionsByUserAndAccountId(user.id + "", account.id + "").subscribe(
                    data => {
                      this.accountsTransactions.set(account.id, data)
                    }
                  )
                }
              }
            )
          }
        }
      )
    } else {

      this.userService.getUser(this.userId).subscribe(
        data => {
          this.accountService.getAllAccountsByUserId(this.userId).subscribe(
            data => {
              this.usersAccounts.set(this.userId, data)
              for(let account of data) {
                this.transactionService.getAllTransactionsByUserAndAccountId(this.userId + "", account.id + "").subscribe(
                  data => {
                    this.accountsTransactions.set(account.id, data)
                  }
                )
              }
            }
          )
          
        }
      )
    }
  }

  refreshTransactions() {
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

  finalizeTransaction(originUserId : string, originAccount : Account, transaction : Transaction) {
    for(let destinyUserId of this.usersAccounts.keys()) {
      for(let destinyAccount of this.usersAccounts.get(destinyUserId)) {
        if(destinyAccount.id == transaction.destinyAccount.id) {
          originAccount.amount = originAccount.amount - transaction.amount
          destinyAccount.amount = destinyAccount.amount + transaction.amount
          transaction.state = "FINISHED"
          originAccount.transactions = []
          destinyAccount.transactions = []
          this.accountService.updateAccount(originUserId, originAccount.id + "", originAccount).subscribe(
           data => {
             this.accountService.updateAccount(destinyUserId, destinyAccount.id, destinyAccount).subscribe( 
               data => {
                this.transactionService.updateTransaction(originUserId, originAccount.id + "", transaction.id + "", transaction).subscribe(
                  data => {
                    this.refreshTransactions()
                  }
                )
               }
              )
            } 
          )
        }
      }
    }
  }
}
