import { Component, OnInit } from '@angular/core';
import { User } from '../user/user.component';
import { UserDataService } from '../service/data/user-data.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-all-users',
  templateUrl: './all-users.component.html',
  styleUrls: ['./all-users.component.css']
})
export class AllUsersComponent implements OnInit {

  users : User[]
  message : string

  constructor(
    private userService : UserDataService,
    private router : Router
  ) { }

  ngOnInit() {
    this.userService.getAllUsers().subscribe(
      data => {
        this.users = data
      }
    )
  }

  viewAccounts(userId : string) {
    this.router.navigate(['all_accounts', userId])
  }

  createUser() {
    this.router.navigate(['sign_in'])
  }

  deleteUser(userId : string) {
    this.userService.deleteUser(userId).subscribe(
      data => {
        this.message = 'Deleted user!'
        this.refreshUsers()
      }  
    )
  }

  refreshUsers() {
    this.userService.getAllUsers().subscribe(
      data => {
        this.users = data
      }
    )
  }

}
