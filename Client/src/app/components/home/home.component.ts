import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor() { }

  slides: string[] = [
    '/assets/home1.png',
    '/assets/home2.jpg',
    '/assets/home3.jpg',
    '/assets/home4.jpg',
  ]

  ngOnInit(): void {
  }
}
