import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Account } from 'src/app/account/account.component';
import { API_URL } from 'src/app/app.constants';


@Injectable({
  providedIn: 'root'
})
export class AccountDataService {

  constructor(
    private http : HttpClient
  ) { }

  getAllAccounts() : Observable<Account[]>{
    return this.http.get<Account[]>(`${API_URL}/accounts`)
  }

  getAllAccountsByUserId(userId : string) : Observable<Account[]> {
    return this.http.get<Account[]>(`${API_URL}/users/${userId}/accounts`)
  }

  getAccount(userId : string, accountId : string) : Observable<Account> {
    return this.http.get<Account>(`${API_URL}/users/${userId}/accounts/${accountId}`)
  }

  createAccount(userId : string, account : Account) : Observable<Account> {
    return this.http.post<Account>(
      `${API_URL}/users/${userId}/accounts`,
      account
    )
  }

  updateAccount(userId : string, accountId : string, account : Account) : Observable<Account> {
    return this.http.put<Account>(
      `${API_URL}/users/${userId}/accounts/${accountId}`,
      account
    )
  }

  deleteAccount(userId : string, accountId : string) : Observable<Account> {
    return this.http.delete<Account>(`${API_URL}/users/${userId}/accounts/${accountId}`)
  }
}
