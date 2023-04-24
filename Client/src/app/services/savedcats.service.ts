import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { firstValueFrom } from "rxjs";
import { AppCookieService } from "./cookie.service";

@Injectable()
export class SavedCatsService {

    constructor(private httpClient: HttpClient,
                private cookieSvc: AppCookieService) { }
            
    email = this.cookieSvc.get("email")
    _catId!: string

    setCatId(catId: string) {
        this._catId = catId
    }
    getCatId() {
        return this._catId
    }

    alterSavedCats(cat_id: string, cat_label: string, alteration: string) {
        const params = new HttpParams()
            .set("email", this.email)
            .set("cat_label", cat_label)
            .set("cat_id", cat_id)
            .set("alteration", alteration)

        return firstValueFrom(
            this.httpClient.get<any>('/saved/alterSaved', {params})
        )
    }

    getAllSavedCats() {
        const params = new HttpParams()
            .set("email", this.email)
        
        return firstValueFrom(
            this.httpClient.get<any>("/saved/allCats", {params})
        )
    }
}