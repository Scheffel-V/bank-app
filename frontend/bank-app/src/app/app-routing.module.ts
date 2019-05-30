import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { WelcomeComponent } from './welcome/welcome.component';
import { ErrorComponent } from './error/error.component';
import { ListAccountsComponent } from './list-accounts/list-accounts.component';
import { LogoutComponent } from './logout/logout.component';
import { RouteGuardService } from './service/route-guard.service';
import { AccountComponent } from './account/account.component';
import { ListTransactionsComponent } from './list-transactions/list-transactions.component';
import { TransactionComponent } from './transaction/transaction.component';

const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'welcome/:username', component: WelcomeComponent, canActivate:[RouteGuardService] },
  { path: 'login', component: LoginComponent },
  { path: 'my_accounts', component: ListAccountsComponent, canActivate:[RouteGuardService] },
  { path: 'my_accounts/:accountId', component: AccountComponent, canActivate:[RouteGuardService] },
  { path: 'my_accounts/:accountId/my_transactions', component: ListTransactionsComponent, canActivate:[RouteGuardService] },
  { path: 'my_accounts/:accountId/my_transactions/:transactionId', component: TransactionComponent, canActivate:[RouteGuardService] },
  { path: 'logout', component: LogoutComponent, canActivate:[RouteGuardService]},
  { path: '**', component: ErrorComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
