import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AppCookieService } from 'src/app/services/cookie.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

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
