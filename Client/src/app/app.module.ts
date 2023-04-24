import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from './material.module';
import { MatCardModule } from '@angular/material/card';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { CatService } from './services/cat.service';
import { AppCookieService } from './services/cookie.service';
import { UniversalAppInterceptor } from './services/httpinterceptor.service';
import { LoginService } from './services/login.service';
import { PostService } from './services/post.service';
import { SavedCatsService } from './services/savedcats.service';
import { UploadService } from './upload.service';
import { WebcamModule } from 'ngx-webcam';
// import { AuthorizeGuard } from './services/authorizeguard.service';
// import { JWTTokenService } from './services/jwt.service';

import { ExploreComponent } from './components/cats/explore.component';
import { IdComponent } from './components/cats/id.component';
import { PostComponent } from './components/cats/post.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/login/register.component';
import { SuccessComponent } from './components/login/success.component';
import { NavComponent } from './components/nav/nav.component';
import { SavedCatsComponent } from './components/profile/savedCats.component';
import { DetailCatsComponent } from './components/search/detailCats.component';
import { FindCatsComponent } from './components/search/findCats.component';
import { ListCatsComponent } from './components/search/listCats.component';
import { FormComponent } from './components/upload/form.component';
import { SnapComponent } from './components/upload/snap.component';
import { UploadSuccessComponent } from './components/upload/success.component';
import { UnsuccessfulComponent } from './components/upload/unsuccessful.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    SavedCatsComponent,
    FindCatsComponent,
    ListCatsComponent,
    DetailCatsComponent,
    LoginComponent,
    RegisterComponent,
    NavComponent,
    HomeComponent,
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
    BrowserAnimationsModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    WebcamModule,
  ],
  providers: [LoginService, /* JWTTokenService,  */UploadService,
    AppCookieService, /* AuthorizeGuard,  */CatService, PostService,
    SavedCatsService,
    { provide: HTTP_INTERCEPTORS, useClass: UniversalAppInterceptor, multi: true }],
  bootstrap: [AppComponent]
})
export class AppModule { }
