import { Component, OnInit } from '@angular/core';
import { AccountDataService } from '../service/data/account-data.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Account } from '../account/account.component';
import { Location } from '@angular/common';
import { UserDataService } from '../service/data/user-data.service';
import { User } from '../user/user.component';

@Component({
  selector: 'app-all-accounts',
  templateUrl: './all-accounts.component.html',
  styleUrls: ['./all-accounts.component.css']
})
export class AllAccountsComponent implements OnInit {


  accounts : Account[]
  users : User[]
  usersAccounts = new Map() 
  message : string
  id : string = '-1'

  constructor(
    private userService : UserDataService,
    private accountService : AccountDataService,
    private router : Router,
    private activatedRoute : ActivatedRoute,
    private location : Location
  ) { }

  ngOnInit() {
    let id = this.activatedRoute.snapshot.params['userId']
    if(id) {
      this.id = id
    }
    if(this.id == '-1') {
      this.userService.getAllUsers().subscribe(
        data => {
          this.users = data
          for(let user of this.users) {
            this.accountService.getAllAccountsByUserId(user.id + "").subscribe(
              data => {
                this.usersAccounts.set(user.id, data)
              }
            )
          }
        }
      )
    } else {
      this.accountService.getAllAccountsByUserId(this.id).subscribe(
        data => {
          this.usersAccounts.set(this.id, data)
        }
      )
    }
  }

  viewTransactions(userId : string, transactionId : string) {
    this.router.navigate(['all_transactions', userId, transactionId])
  }

  backPage() {
    this.location.back()
  }
}
