import {Component, OnInit} from '@angular/core';
import {Director} from '../model/Director';
import {Movie} from '../model/Movie';
import {MovieService} from '../services/movie.service';

@Component({
  selector: 'app-author',
  templateUrl: './author.component.html',
  styleUrls: ['./author.component.css']
})
export class AuthorComponent implements OnInit {

  directors: Director[] = [];

  constructor(private movieService: MovieService) {
  }

  ngOnInit() {
    this.getListDirector();
  }

  getListDirector() {
    this.movieService.getMovieList().subscribe(
      data => {
        data.map(value => value.director).map(value => {
          const listDirector = value.split(',');
          for (let i = 0; i < listDirector.length; i++) {
            const d: Director = new Director();
            d.name = listDirector[i];
            d.movies = data.filter(_ => _.director === value);
            if (this.directors.filter(_ => _.name === value).length === 0) {
              this.directors.push(d);
            }
          }
        });
      }, error => {
        console.log(error);
      }
    );
  }


  getDirectorMovie(movies: Movie[]) {
    return movies.map(value => value.title).join(', ');
  }
}
