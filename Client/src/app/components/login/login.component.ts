import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { LoginService } from 'src/app/services/login.service';
import { User } from 'src/app/models';
import { Router } from '@angular/router';
import { AppCookieService } from 'src/app/services/cookie.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  form!: FormGroup;
  email = new FormControl<string>('', [Validators.email, Validators.required])
  hide = true;
  userExists = true;

  constructor(private fb: FormBuilder, private loginSvc: LoginService,
    private route: Router, private cookieSvc: AppCookieService) { }

  ngOnInit(): void {
    this.form = this.createForm();
  }

  createForm() {
    return this.fb.group({
      email: this.email,
      password: this.fb.control<string>('', [Validators.required])
    })
  }

  getErrorMessage() {
    return this.email.hasError('email') ? 'Not a valid email' :
      "";
  }

  processForm() {
    const user: User = this.form.value as User;
    this.loginSvc.authenticateUser(user)
      .then(response => {
        let token = response.body?.token as string
        user.username = response.body.username;
        this.cookieSvc.set("username", user.username)
        this.cookieSvc.set("email", user.email)
        this.cookieSvc.set("token", token)
        this.loginSvc.getLandingExplorePage()
        .then(result => {
          this.route.navigate([`Neko/explore/latest`])
        })
        .catch(error => console.info(">>>> error login in to explore page: " + error))
      })
      .catch(error => {
        this.userExists = false;
        console.info(">>>> user does not exist! " + error)
      })
  }

}
