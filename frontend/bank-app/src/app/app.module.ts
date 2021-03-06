import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { WelcomeComponent } from './welcome/welcome.component';
import { ErrorComponent } from './error/error.component';
import { ListAccountsComponent } from './list-accounts/list-accounts.component';
import { MenuComponent } from './menu/menu.component';
import { FooterComponent } from './footer/footer.component';
import { LogoutComponent } from './logout/logout.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { UserComponent } from './user/user.component';
import { AccountComponent } from './account/account.component';
import { TransactionComponent } from './transaction/transaction.component';
import { HttpIntercepterBasicAuthService } from './service/http/http-intercepter-basic-auth.service';
import { ListTransactionsComponent } from './list-transactions/list-transactions.component';
import { AllUsersComponent } from './all-users/all-users.component';
import { AllAccountsComponent } from './all-accounts/all-accounts.component';
import { AllTransactionsComponent } from './all-transactions/all-transactions.component';
import { SignInComponent } from './sign-in/sign-in.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    WelcomeComponent,
    ErrorComponent,
    ListAccountsComponent,
    MenuComponent,
    FooterComponent,
    LogoutComponent,
    UserComponent,
    AccountComponent,
    TransactionComponent,
    ListTransactionsComponent,
    AllUsersComponent,
    AllAccountsComponent,
    AllTransactionsComponent,
    SignInComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [
    { provide : HTTP_INTERCEPTORS, useClass : HttpIntercepterBasicAuthService, multi : true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
