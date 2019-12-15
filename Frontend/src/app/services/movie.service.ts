import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Movie} from '../model/Movie';

@Injectable()
export class MovieService {

  private baseUrl = 'http://localhost:8080/movies';

  constructor(private http: HttpClient) { }

  getMovie(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${id}`);
  }

  createMovie(movie: Movie): Observable<any> {
    return this.http.post(`${this.baseUrl}`, movie);
  }

  updateMovie(movie: Movie): Observable<any> {
    return this.http.put(`${this.baseUrl}`, movie);
  }

  deleteMovie(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`, { responseType: 'text' });
  }

  getMovieList(): Observable<any> {
    return this.http.get(`${this.baseUrl}`);
  }
}
