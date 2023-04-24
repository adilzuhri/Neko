import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Cat } from 'src/app/models';
import { CatService } from 'src/app/services/cat.service';
import { SavedCatsService } from 'src/app/services/savedcats.service';

@Component({
  selector: 'app-detailCats',
  templateUrl: './detailCats.component.html',
  styleUrls: ['./detailCats.component.css']
})
export class DetailCatsComponent implements OnInit {

  constructor(private ar: ActivatedRoute,
              private catSvc: CatService,
              private savedCatSvc: SavedCatsService,
              private route: Router,
              private location: Location) { }

  query!: string
  querySub$!: Subscription

  id!: string
  catIdSub$!: Subscription

  num!: string
  numSub$!: Subscription
  isSaved!: boolean;

  cat: Cat = {
    cat_id: '',
    storedUUID: '',
    label: '',
    image: '',
    link: '',
  }

  ngOnInit(): void {
    if (this.ar.snapshot.params['query']) {
      this.query = this.ar.snapshot.params['query']
      this.querySub$ = this.ar.params.subscribe(v => {
        console.info('>subscribe: ', v)
        // @ts-ignore
        this.query = v.query
      })
    }
    if (this.ar.snapshot.params['id']) {
      this.id = this.ar.snapshot.params['id']
      this.catIdSub$ = this.ar.params.subscribe(b => {
        console.info('>subscribe: ', b)
        // @ts-ignore
        this.catId = b.id
      })
    }
    if (this.ar.snapshot.params['num']) {
      this.num = this.ar.snapshot.params['num']
      this.numSub$ = this.ar.params.subscribe(n => {
        console.info('>subscribe: ', n)
        // @ts-ignore
        this.num = n.num
      })
    }

    this.catSvc.getCatDetails(this.id)
      .then(result => {
        this.cat = result.cat
        this.isSaved = result.saved
      })
      .catch(error => console.info("error in get cat details: " + error))
  }

  alterSavedCats(cat_id: string, cat_label: string, alteration: string) {
    console.info(">>>> cat_label: " + cat_label)
    this.savedCatSvc.alterSavedCats(cat_id, cat_label, alteration)
      .then(result => {
        console.info("result of saving cat:" + result)
      })
      .catch()
    if (alteration.includes('add')) {
      this.isSaved = true;
    } else {
      this.isSaved = false;
    }
  }

  reRouteBack(){
    // [routerLink]="['/Neko/search', query, num]"
    // if (!this.query && ! this.num) {
    //   this.route.navigate(['/Neko/profile/savedCats'])
    // } else {
    //   this.route.navigate(['/Neko/search', this.query, this.num])
    // }
    this.location.back()
  }

}
