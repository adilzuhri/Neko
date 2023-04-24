import { Component, OnInit, Output } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-explore',
  templateUrl: './explore.component.html',
  styleUrls: ['./explore.component.css']
})
export class ExploreComponent implements OnInit {

  constructor(private ar: ActivatedRoute,
              private route: Router) { }
              
  profileTab = false
  tab!: string
  activeLink!: string
  links!: [
    {
      label: string,
      path: string
    },
    {
      label: string,
      path: string
    },
    {
      label: string,
      path: string
    },
  ]

  @Output()
  changeTab = new Subject<String>();

  ngOnInit(): void {
    const page = window.location.href;

    if (page.includes("explore")) {
      this.links = [
        { label: 'Latest', path: '/Neko/explore/latest' },
        { label: 'Popular', path: '/Neko/explore/popular' },
        { label: 'Search by Cat ID', path: '/Neko/explore/byCatId' }
      ]
    } else if (page.includes("profile")) {
      this.links = [
        { label: 'My Posts', path: '/Neko/profile/myPosts' },
        { label: 'Liked Posts', path: '/Neko/profile/likedPosts' },
        { label: 'Saved Cats', path: '/Neko/profile/savedCats' }
      ]
    }
  }
}
