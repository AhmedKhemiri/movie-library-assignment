import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {MovieListComponent} from './movie-list/movie-list.component';
import {HomeComponent} from './home/home.component';
import {AuthorComponent} from './author/author.component';


const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'movies', component: MovieListComponent },
  { path: 'author', component: AuthorComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
