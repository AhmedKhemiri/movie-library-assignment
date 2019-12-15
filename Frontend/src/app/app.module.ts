import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {MovieListComponent} from './movie-list/movie-list.component';
import {MovieService} from './services/movie.service';
import {HttpClientModule} from '@angular/common/http';
import {HomeComponent} from './home/home.component';
import {NgbModalModule, NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {DatePipe} from '@angular/common';
import { AuthorComponent } from './author/author.component';

@NgModule({
  declarations: [
    AppComponent,
    MovieListComponent,
    HomeComponent,
    AuthorComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgbModalModule,
    NgbModule,
    ReactiveFormsModule
  ],
  providers: [MovieService, DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule {
}
