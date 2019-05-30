import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Transaction } from 'src/app/transaction/transaction.component';
import { API_URL } from 'src/app/app.constants';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class TransactionDataService {

  constructor(
    private http : HttpClient
  ) { }

  getAllTransactions() : Observable<Transaction[]> {
    return this.http.get<Transaction[]>(`${API_URL}/transactions`)
  }

  getAllTransactionsByUserAndAccountId(userId : string, accountId : string) : Observable<Transaction[]> {
    return this.http.get<Transaction[]>(`${API_URL}/users/${userId}/accounts/${accountId}/transactions`)
  }

  getTransaction(userId : string, accountId : string, transactionId : string) : Observable<Transaction> {
    return this.http.get<Transaction>(`${API_URL}/users/${userId}/accounts/${accountId}/transactions/${transactionId}`)
  }

  createTransaction(userId : string, originAccountId : string, transaction : Transaction) : Observable<Transaction> {
    return this.http.post<Transaction>(
      `${API_URL}/users/${userId}/accounts/${originAccountId}/transactions`,
      transaction
    )
  }

  updateTransaction(userId : string, accountId : string, transactionId : string, transaction : Transaction) : Observable<Transaction> {
    return this.http.put<Transaction>(
      `${API_URL}/users/${userId}/accounts/${accountId}/transactions/${transactionId}`,
      transaction
    )
  }

  deleteTransaction(userId : string, accountId : string, transactionId : string) {
    return this.http.delete(`${API_URL}/users/${userId}/accounts/${accountId}/transactions/${transactionId}`)
  }

}
