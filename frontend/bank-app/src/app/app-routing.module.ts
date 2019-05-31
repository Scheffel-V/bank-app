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
import { AllUsersComponent } from './all-users/all-users.component';
import { AllAccountsComponent } from './all-accounts/all-accounts.component';
import { AllTransactionsComponent } from './all-transactions/all-transactions.component';
import { SignInComponent } from './sign-in/sign-in.component';

const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'welcome/:username', component: WelcomeComponent, canActivate:[RouteGuardService] },
  { path: 'login', component: LoginComponent },
  { path: 'my_accounts', component: ListAccountsComponent, canActivate:[RouteGuardService] },
  { path: 'my_accounts/:accountId', component: AccountComponent, canActivate:[RouteGuardService] },
  { path: 'my_accounts/:accountId/my_transactions', component: ListTransactionsComponent, canActivate:[RouteGuardService] },
  { path: 'my_accounts/:accountId/my_transactions/:transactionId', component: TransactionComponent, canActivate:[RouteGuardService] },
  { path: 'all_users', component: AllUsersComponent, canActivate:[RouteGuardService]},
  { path: 'all_accounts', component: AllAccountsComponent, canActivate:[RouteGuardService]},
  { path: 'all_accounts/:userId', component: AllAccountsComponent, canActivate:[RouteGuardService]},
  { path: 'all_transactions', component: AllTransactionsComponent, canActivate:[RouteGuardService]},
  { path: 'all_transactions/:userId/:accountId', component: AllTransactionsComponent, canActivate:[RouteGuardService]},
  { path: 'sign_in', component: SignInComponent },
  { path: 'logout', component: LogoutComponent, canActivate:[RouteGuardService]},
  { path: '**', component: ErrorComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
