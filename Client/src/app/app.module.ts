import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MaterialModule } from './material.module';
import { MatCardModule } from '@angular/material/card'

import { AppCookieService } from './services/cookie.service';
import { LoginService } from './services/login.service';
import { PostService } from './services/post.service';
import { SavedCatsService } from './services/savedcats.service';
import { HomeComponent } from './components/home/home.component';

import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/login/register.component';
import { NavbarComponent } from './components/navbar.component';
import { SavedCatsComponent } from './components/profile/savedCats.component';
import { CatDetailsComponent } from './components/search/catdetails.component';
import { FindCatsComponent } from './components/search/findcats.component';
import { ListCatsComponent } from './components/search/listcats.component';


import { WebcamModule } from 'ngx-webcam';
import { ExploreComponent } from './components/explore/explore.component';
import { IdComponent } from './components/explore/id.component';
import { PostComponent } from './components/explore/post.component';
import { SuccessComponent } from './components/login/success.component';
import { FormComponent } from './components/upload/form.component';
import { SnapComponent } from './components/upload/snap.component';
import { UploadSuccessComponent } from './components/upload/success.component';
import { UnsuccessfulComponent } from './components/upload/unsuccessful.component';
import { AuthorizeGuard } from './services/authorizeguard.service';
import { CatService } from './services/cat.service';
import { UniversalAppInterceptor } from './services/httpinterceptor.service';
import { JWTTokenService } from './services/jwt.service';
import { UploadService } from './services/upload.service';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    SavedCatsComponent,
    FindCatsComponent,
    ListCatsComponent,
    CatDetailsComponent,
    LoginComponent,
    RegisterComponent,
    NavbarComponent,
    LandingComponent,
    ExploreComponent,
    SuccessComponent,
    SnapComponent,
    FormComponent,
    PostComponent,
    IdComponent,
    UploadSuccessComponent,
    UnsuccessfulComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RouterModule,
    BrowserAnimationsModule,
    MaterialModule,
    MatCardModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    WebcamModule,
    LoginComponent
  ],
  providers: [LoginService, JWTTokenService, UploadService,
    AppCookieService, AuthorizeGuard, CatService, PostService,
    SavedCatsService,
    { provide: HTTP_INTERCEPTORS, useClass: UniversalAppInterceptor, multi: true }],
  bootstrap: [AppComponent]
})
export class AppModule { }
