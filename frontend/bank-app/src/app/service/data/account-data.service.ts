import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Account } from 'src/app/list-accounts/list-accounts.component';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class AccountDataService {

  constructor(
    private http : HttpClient
  ) { }

  getAllAccounts() : Observable<Account[]>{
    return this.http.get<Account[]>('http://localhost:8080/accounts')
  }

  getAllAccountsByUserId(userId : string) : Observable<Account[]> {
    return this.http.get<Account[]>(`http://localhost:8080/users/${userId}/accounts`)
  }

  getAccountById(userId : string, accountId : string) : Observable<Account> {
    return this.http.get<Account>(`http://localhost:8080/users/${userId}/accounts/${accountId}`)
  }

  deleteAccountById(userId : string, accountId : string) : Observable<Account> {
    return this.http.delete<Account>(`http://localhost:8080/users/${userId}/accounts/${accountId}`)
  }

}
