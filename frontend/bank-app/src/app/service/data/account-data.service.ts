import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Account } from 'src/app/account/account.component';


@Injectable({
  providedIn: 'root'
})
export class AccountDataService {

  constructor(
    private http : HttpClient
  ) { }

  getAllAccounts() : Observable<Account[]>{
    return this.http.get<Account[]>(
      'http://localhost:8080/accounts',
      { 
        headers : this.createBasicAuthenticationHttpHeader()
      }
      )
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

  createBasicAuthenticationHttpHeader() {
    let username = 'brsufirefox'
    let password = 'Bludakeia1'
    let basicAuthHeaderString = 'Basic ' + window.btoa(username + ":" + password)
    
    return new HttpHeaders({
      Authorization : basicAuthHeaderString
    })
  }
}
