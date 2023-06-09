import { HttpClient, HttpParams } from "@angular/common/http";
import { ThisReceiver } from "@angular/compiler";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { firstValueFrom, Observable, Subject } from "rxjs";
import { User } from "../models";
import { AppCookieService } from "./cookie.service";

@Injectable()
export class LoginService {

    constructor(private httpClient: HttpClient,
                private route: Router,
                private cookieSvc: AppCookieService) { }

    private _token!: string
    private _user!: User

    authenticateUser(user: User) {
        return firstValueFrom(
            this.httpClient.post<any>("/authenticate", user, {observe: 'response', headers:{skip:"true"}}))
    }

    getLandingExplorePage() {
        return firstValueFrom(
            this.httpClient.get<any>("/explore")
        )
    }

    createUser(user: User) {
        return firstValueFrom(
            this.httpClient.post<any>("/signUp", user, {observe: 'response', headers: {skip:"true"}})
        )
    }

    setCookie(user: User, token: string) {
        this.cookieSvc.set("username", user.username)
        this.cookieSvc.set("email", user.email)
        this.cookieSvc.set("token", token)
    }
}