import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SavedCat } from 'src/app/models';
import { SavedCatsService } from 'src/app/services/savedcats.service';

@Component({
  selector: 'app-saved-cats',
  templateUrl: './savedcats.component.html',
  styleUrls: ['./savedcats.component.css']
})
export class SavedCatsComponent implements OnInit {

  constructor(private savedCatsSvc: SavedCatsService,
              private route: Router) { }

  savedCats: SavedCat[] = []
  noCatsSaved = false

  ngOnInit(): void {
    this.savedCatsSvc.getAllSavedCats()
      .then(result => {
        console.info("result of getting saved cats: " + result)
        this.savedCats = result;
        console.info("savedCats length: " + this.savedCats.length)
      })
      .catch(error => {
        this.noCatsSaved = true;
        console.info(">>>> failed to obtain saved cats: " + error)
      })
  }

  getCatDetails(cat_id: string) {
    this.savedCatsSvc.setCatId(cat_id);
    this.route.navigate(['/Neko/search/' + cat_id])
  }


}
