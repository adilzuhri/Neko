import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Cat } from 'src/app/models';
import { CatService } from 'src/app/services/cat.service';

@Component({
  selector: 'app-ListCats',
  templateUrl: './listCats.component.html',
  styleUrls: ['./listCats.component.css']
})
export class ListCatsComponent implements OnInit {

  constructor(private catSvc: CatService,
    private route: Router,
    private ar: ActivatedRoute) { }

  linkSub$!: Subscription
  nextURL = ""
  noNext = true
  cats: Cat[] = []
  query!: string
  noPrev = true
  numPage!: number
  serviceCalledButError = false;

  ngOnInit(): void {
    if (this.ar.snapshot.params['query'] && this.ar.snapshot.params['num']) {
      this.query = this.ar.snapshot.params['query']
      this.numPage = this.ar.snapshot.params['num']
      this.linkSub$ = this.ar.params.subscribe(v => {
        this.cats = []
        console.info('>subscribe: ', v)
        // @ts-ignore
        this.query = v.query
        //@ts-ignore
        this.numPage = v.num
        if (this.numPage != 1) {
          this.noPrev = false
        }
        this.callGetCatsSvc()
      })
    }
  }

  getCatDetails(cat_id: string) {
    this.route.navigate(["/Neko/search", this.query, this.numPage, cat_id])
  }

  getNext(contValue: string) {
    this.cats = [];
    this.numPage = Number(this.numPage) + 1
    this.route.navigate(["/Neko/search", this.query, this.numPage])
    this.callGetCatsSvc()
    this.noPrev = false;
    // KIV on contValue
  }

  getPrev() {
    this.numPage = Number(this.numPage) - 1;
    this.cats = [];
    if (this.numPage == 1) {
      this.noPrev = true;
    }
    this.route.navigate(["/Neko/search", this.query, this.numPage])
    this.callGetCatsSvc();
  }

  callGetCatsSvc() {
    this.catSvc.getCats(this.query, this.numPage, this.nextURL)
      .then(result => {
        this.nextURL = result.nextURL
        this.noNext = false
        this.cats = result.cats;
      })
      .catch(error => {
        this.serviceCalledButError = true
        this.noNext = true;
        console.info(">>>> error: " + error)
      })
  }
}
