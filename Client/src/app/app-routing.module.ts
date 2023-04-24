import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ExploreComponent } from './components/cats/explore.component';
import { IdComponent } from './components/cats/id.component';
import { PostComponent } from './components/cats/post.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/login/register.component';
import { SavedCatsComponent } from './components/profile/savedCats.component';
import { CatDetailsComponent } from './components/search/detailCats.component';
import { FindCatsComponent } from './components/search/findCats.component';
import { ListCatsComponent } from './components/search/listCats.component';
import { FormComponent } from './components/upload/form.component';
import { SnapComponent } from './components/upload/snap.component';
// import { AuthorizeGuard } from './services/authorizeguard.service';

const routes: Routes = [
  { path: '', component: HomeComponent, title: 'Welcome' },
  { path: 'signUp', component: RegisterComponent, title: 'Sign Up' },
  { path: 'login', component: LoginComponent, title: 'Login' },
  {
    path: 'Neko/explore',
    component: ExploreComponent,
    title: 'Explore',
    // canActivate: [AuthorizeGuard],
    children: [
      {
        path: 'byCatId',
        component: IdComponent,
        // canActivate: [AuthorizeGuard],
        children: [
          {
            path: ':id',
            component: PostComponent,
            // canActivate: [AuthorizeGuard]
          }
        ]
      },
      {
        path: ':page',
        component: PostComponent,
        // canActivate: [AuthorizeGuard]
      },
    ]
  },

  { path: 'Neko/upload/snap', component: SnapComponent, /* canActivate: [AuthorizeGuard], */ title: 'Snap post' },
  { path: 'Neko/upload/snap/:id', component: SnapComponent, /* canActivate: [AuthorizeGuard], */ title: 'Snap post' },
  { path: 'Neko/upload/form', component: FormComponent, /* canActivate: [AuthorizeGuard], */ title: 'Upload post' },
  { path: 'Neko/upload/form/:id', component: FormComponent, /* canActivate: [AuthorizeGuard], */ title: 'Upload post' },
  {
    path: 'Neko/search',
    component: FindCatsComponent,
    title: 'Search Cats',
    children: [
      {
        path: ':query/:num',
        component: ListCatsComponent,
        // canActivate: [AuthorizeGuard]
      }
    ]
  },
  { path: 'Neko/search/:query/:num/:id', component: CatDetailsComponent, /* canActivate: [AuthorizeGuard], */ title: 'Cat Details' },
  { path: 'Neko/search/:id', component: CatDetailsComponent, /* canActivate: [AuthorizeGuard], */ title: 'Cat Details' },
  {
    path: 'Neko/profile',
    title: 'My Profile',
    component: ExploreComponent,
    // canActivate: [AuthorizeGuard],
    children: [
      {
        path: 'savedCats',
        component: SavedCatsComponent,
        // canActivate: [AuthorizeGuard]
      },
      {
        path: ':page',
        component: PostComponent,
        // canActivate: [AuthorizeGuard]
      }
    ]
  },
  { path: "**", redirectTo: '/', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
