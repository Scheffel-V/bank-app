import { Component, OnInit } from '@angular/core';
import { AccountDataService } from '../service/data/account-data.service';
import { Account } from '../account/account.component';
import { BasicAuthenticationService } from '../service/basic-authentication.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-list-accounts',
  templateUrl: './list-accounts.component.html',
  styleUrls: ['./list-accounts.component.css']
})
export class ListAccountsComponent implements OnInit {

  accounts : Account[]
  message : string

  constructor(
    private accountService : AccountDataService,
    private basicAuthService : BasicAuthenticationService,
    private router : Router
    ) { }

  ngOnInit() {
    this.refreshAccounts()
  }

  refreshAccounts() {
    this.accountService.getAllAccountsByUserId(this.basicAuthService.getAuthenticatedUserId()).subscribe(
      response => {
        this.accounts = response;
      }
    )
  }

  deleteAccount(accountId) {
    this.accountService.deleteAccount(this.basicAuthService.getAuthenticatedUserId(), accountId).subscribe(
      response => {
        this.message = `Deleted account!`
        this.refreshAccounts()
      }
    )
  }

  updateAccount(accountId) {
    this.router.navigate(['my_accounts', accountId])
  }

  createAccount() {
    this.router.navigate(['my_accounts', -1])
  }

}
