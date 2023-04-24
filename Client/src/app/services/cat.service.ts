import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { firstValueFrom, Subject } from "rxjs";
import { Cat, CatDetailsResponse, CatListResponse } from "../models";
import { AppCookieService } from "./cookie.service";

@Injectable()
export class CatService {

    constructor(private httpClient: HttpClient,
                private cookieSvc: AppCookieService) { }

    

    getCats(query: string, numPage: number, _contValue?: string) {
        let params;
        if (_contValue) {
            params = new HttpParams()
                .set("query", query)
                .set("_contValue", _contValue)
        } else {
            params = new HttpParams()
                    .set("query", query)
        }
        return firstValueFrom(this.httpClient.get<CatListResponse>(`/search/cats/${numPage.toString()}`, {params}))
    }

    getCatDetails(id: string): Promise<CatDetailsResponse> {
        const params = new HttpParams()
            .set("email", this.cookieSvc.get("email"))
        return firstValueFrom(this.httpClient.get<CatDetailsResponse>(`/search/cat/${id}`, {params}))
    }

}