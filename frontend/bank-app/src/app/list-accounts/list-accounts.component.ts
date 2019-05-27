import { Component, OnInit } from '@angular/core';
import { AccountDataService } from '../service/data/account-data.service';


@Component({
  selector: 'app-list-accounts',
  templateUrl: './list-accounts.component.html',
  styleUrls: ['./list-accounts.component.css']
})
export class ListAccountsComponent implements OnInit {

  accounts : Account[]
  userId = '1'
  message : string

  constructor(private accountService : AccountDataService) { }

  ngOnInit() {
    this.refreshAccounts()
  }

  refreshAccounts() {
    this.accountService.getAllAccounts().subscribe(
      response => {
        console.log(response);
        this.accounts = response;
      }
    )
  }

  deleteAccount(accountId) {
    this.accountService.deleteAccountById(this.userId, accountId).subscribe(
      response => {
        console.log(response);
        this.accounts = response;
        this.message = `Deleted account!`
        this.refreshAccounts()
      }
    )
  }

}
