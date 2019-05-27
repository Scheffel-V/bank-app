import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Transaction } from 'src/app/transaction/transaction.component';


@Injectable({
  providedIn: 'root'
})
export class TransactionDataService {

  constructor(
    private http : HttpClient
  ) { }

  getAllTranscations() {
    return this.http.get<Transaction>('http://localhost:8080/users')
  }

  getAllTransactionsByUserAndAccountId(userId : string, accountId : string) {
    return this.http.get<Transaction[]>(`http://localhost:8080/users/${userId}/accounts/${accountId}/transactions`)
  }
}
