import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AppCookieService } from 'src/app/services/cookie.service';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent implements OnInit {

  constructor(private route: Router,
              private cookieSvc: AppCookieService) { }


  ngOnInit(): void {
  }

  loggedIn(): boolean {
    let url = window.location.href;
    if (!url.includes("Neko")) {
      return false;
    } 
    return true;
  }

  logout() {
    this.cookieSvc.remove("token")
    this.cookieSvc.remove("username")
    this.cookieSvc.remove("email")
    this.route.navigate(['/']);
  }
    
  routeLandingPage() {
    this.route.navigate(['/']);
  }
}
