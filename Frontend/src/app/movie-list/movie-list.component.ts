import {Component, OnInit} from '@angular/core';
import {MovieService} from '../services/movie.service';
import {Movie} from '../model/Movie';
import {Router} from '@angular/router';
import {NgbModal, ModalDismissReasons, NgbDate} from '@ng-bootstrap/ng-bootstrap';
import {DatePipe} from '@angular/common';
import * as $ from 'jquery';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Type} from '../model/Type';
import {BehaviorSubject} from 'rxjs';


@Component({
  selector: 'app-movie-list',
  templateUrl: './movie-list.component.html',
  styleUrls: ['./movie-list.component.css']
})
export class MovieListComponent implements OnInit {

  movieForm: FormGroup;
  movies: Movie[];
  moviesToDisplay: Movie[];
  closeResult: string;
  releaseDate: NgbDate;
  movie: Movie = new Movie();
  action = '';
  sortType: string;
  asc = 1;
  searchText = '';
  options: string[];
  director: '';
  private directorMovie: BehaviorSubject<string> = new BehaviorSubject<string>('');

  constructor(private movieService: MovieService,
              private router: Router,
              private modalService: NgbModal,
              private datepipe: DatePipe,
              private fb: FormBuilder) {
  }

  ngOnInit() {
    this.getListMovie();
    this.movieForm = this.fb.group({
      title: ['', Validators.required],
      director: ['', Validators.required],
      releaseDate: ['', Validators.required],
      type: ['', Validators.required]
    });

    this.options = Object.keys(Type);
  }

  setType(type: string) {
    this.movie.type = type;
  }

  getListMovie() {
    this.movieService.getMovieList().subscribe(
      data => {
        this.movies = data;
        this.moviesToDisplay = data;
      }, error => {
        console.log(error);
      }
    );
  }

  getMovie = (id: number) => this.movies.find(movie => movie.id === id);

  createMovie(movie: Movie) {
    this.movieService.createMovie(movie).subscribe(
      data => {
        this.getListMovie();
      }, error => {
        console.log(error);
      }
    );
  }

  deleteMovie(id: number) {
    this.movieService.deleteMovie(id)
      .subscribe(
        data => {
          console.log(data);
          this.getListMovie();
        },
        error => console.log(error));
  }

  updateMovie(movie: Movie) {
    this.movieService.updateMovie(movie).subscribe(
      data => {
        this.getListMovie();
      }, error => {
        console.log(error);
      }
    );
  }

  open(content, id: number) {
    if (id === 0) {
      this.action = 'Create Movie';
      this.movie = new Movie();
    } else {
      this.action = 'Update Movie';
      this.movie = this.getMovie(id);
      const dt: string[] = this.movie.releaseDate.split('/');
      this.releaseDate = new NgbDate(parseInt(dt[2], 10), parseInt(dt[1], 10), parseInt(dt[0], 10));
    }
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
      console.log(this.releaseDate);
      /*this.movie.releaseDate = this.datepipe.transform(this.releaseDate, 'dd/MM/yyyy');*/
      if (id === 0) {
        this.movie.releaseDate = this.datepipe.transform(new Date(this.releaseDate.year, this.releaseDate.month, this.releaseDate.day),
          'dd/MM/yyyy');
        this.createMovie(this.movie);
      } else {
        this.movie.releaseDate = this.datepipe.transform(new Date(this.releaseDate.year, this.releaseDate.month, this.releaseDate.day),
          'dd/MM/yyyy');
        this.updateMovie(this.movie);
      }
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }

  compare(movie1: Movie, movie2: Movie) {
    return movie1.title.localeCompare(movie2.title) * this.asc;
  }

  sortMovie(sortType: string) {
    this.asc = this.asc * -1;
    this.sortType = sortType;
    this.moviesToDisplay.sort((a, b) => a[sortType].localeCompare(b[sortType]) * this.asc);
  }

  searchMovie() {
    this.moviesToDisplay = this.searchText === '' ? this.movies :
      this.moviesToDisplay = this.movies.filter(movie => {
        return movie.title.toLowerCase().indexOf(this.searchText) !== -1
          || movie.director.toLowerCase().indexOf(this.searchText) !== -1
          || movie.releaseDate.indexOf(this.searchText) !== -1
          || movie.type.toLowerCase().indexOf(this.searchText) !== -1;
      });
  }

  addDirector() {
    if (this.movie.director !== '') {
      this.movie.director += ', ';
    }
    this.movie.director += this.director;
    this.director = '';
  }

  clearDirector() {
    this.movie.director = '';
  }
}
