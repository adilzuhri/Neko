import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Post } from 'src/app/models';
import { formatDate } from '@angular/common'
import { v4 as uuid } from 'uuid';
import { AppCookieService } from 'src/app/services/cookie.service';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { UploadSuccessComponent } from './success.component';
import { Subscription } from 'rxjs';
import { UnsuccessfulComponent } from './unsuccessful.component';
import { UploadService } from 'src/app/upload.service';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css']
})
export class FormComponent implements OnInit {

  imageData!: string
  imageWidth!: string
  form!: FormGroup
  placeholder = "Getting cat label..."
  cat_id_control = this.fb.control('', [Validators.required, Validators.minLength(32)])
  // reicpe_label_control!: FormControl

  constructor(private router: Router, private uploadSvc: UploadService,
    private fb: FormBuilder, private cookieSvc: AppCookieService,
    private dialog: MatDialog, private ar: ActivatedRoute) { }

  id!: string
  idSub$!: Subscription

  ngOnInit(): void {
    if (this.ar.snapshot.params['id']) {
      this.id = this.ar.snapshot.params['id']
      this.idSub$ = this.ar.params.subscribe(v => {
        console.info('>subscribe: ', v)
        // @ts-ignore
        this.id = v.id
      })
    }
    if (!this.uploadSvc.dataUrl) {
      this.router.navigate(['/Neko/upload/snap'])
      return
    }

    const w = Math.floor(window.innerWidth * .45)
    this.imageWidth = `${w}`

    this.imageData = this.uploadSvc.dataUrl

    if (this.id) {
      this.getCatLabel(this.id)
      this.form = this.fb.group({
        title: this.fb.control<string>('', [Validators.required]),
        caption: this.fb.control<string>('', [Validators.required]),
        cat_id: [{ value: this.id, disabled: true }],
        cat_label: [{ value: this.placeholder, disabled: true }],
        // cat_label: this.fb.control<string>('', [Validators.required]),
        // ratings: this.fb.control<number>(1, [Validators.required])
      })
    } else {
      this.cat_id_control = this.fb.control("", [Validators.required, Validators.minLength(32)])
      this.form = this.fb.group({
        title: this.fb.control<string>('', [Validators.required]),
        caption: this.fb.control<string>('', [Validators.required]),
        cat_id: this.cat_id_control,
        cat_label: [{ value: "Getting cat label...", disabled: true }],
        // cat_label: this.fb.control<string>('', [Validators.required]),
        // ratings: this.fb.control<number>(1, [Validators.required])
      })
    }
  }

  getLabel(event: any) {
    if (event.target.value.length === 32) {
      this.getCatLabel(event.target.value)
    }
  }

  getCatLabel(id: string) {
    this.uploadSvc.getCatLabel(id)
      .then(result => {
        console.info("cat_label: " + result)
        this.placeholder = result
      })
      .catch(error => {
        console.info("error in get cat_label: " + error)
      })
  }


  shareIt() {
    console.info('>>>> data: ', this.form.value)
    const post: Post = this.form.getRawValue() as Post;
    post.date = formatDate(new Date(), 'yyyy-MM-dd', 'en')
    const imageUUID = uuid().toString().substring(0, 8);
    post.imageUUID = imageUUID;
    post.cat_label = this.placeholder;
    post.username = this.cookieSvc.get("username")
    console.info(">>>> post cat label: " + post.cat_label)
    post.email = this.cookieSvc.get("email")
    console.info("post.email: " + post.email)
    this.uploadSvc.uploadPostAmazon(post.imageUUID)
      .then(result => {
        this.uploadToDatabase(post)
        console.info("Result of uploadPostAmazon: " + result)
      })
      .catch(error => {
        this.openDialog(false);
        console.info("error in uploadPostAmazon" + error)
      })
  }

  uploadToDatabase(post: Post) {
    this.uploadSvc.uploadPostSB(post)
    .then(result => {
      console.info("Success!")
      console.info("Result of uploadPostSB: " + result)
      this.openDialog(true);
      this.form.reset();
    })
    .catch(error => {
      this.openDialog(false);
      console.info("error in uploadPostSB: " + error)
    })
  }

  openDialog(uploaded: boolean) {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.height = '300px';
    dialogConfig.width = '800px'
    dialogConfig.hasBackdrop = true;
    if (uploaded) {
      this.dialog.open(UploadSuccessComponent, dialogConfig);
    } else {
      this.dialog.open(UnsuccessfulComponent, dialogConfig);
    }
  }

}
