import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import {
  HttpClient,
  HttpHeaders,
  HttpErrorResponse,
  HttpEventType,
} from '@angular/common/http';
import { Router } from '@angular/router';
import { User } from './user';
@Injectable({
  providedIn: 'root',
})
export class AuthService {
  endpoint: string = 'http://localhost:8082';
  headers = new HttpHeaders().set('Content-Type', 'application/json');
  currentUser = {};
  constructor(private http: HttpClient, public router: Router) {}
  // Sign-up
  signUp(user: User): Observable<any> {
    let api = `${this.endpoint}/users/signup`;
    return this.http.post(api, user).pipe(catchError(this.handleError));
  }
  // Sign-in
  login(username: any, password: any) {
    const user = {username: username, password: password};

    const httpOptions: { headers: any; observe: any; } = {
      headers: new HttpHeaders({
          'Content-Type':  'application/json'
      }),
      observe: 'response'
  };
    return this.http.post<any>(`${this.endpoint}/user-login`, user, httpOptions)
    .pipe(map(event => {
      //the checking of the HttpEventType is not strictly necessary
      if (event.type == HttpEventType.Response)
      {
          let user = event.body;
          let headers = event.headers;

          
          return user;
      }
    }), catchError(this.handleError)
    );
  
  }
  getToken() {
    return localStorage.getItem('access_token');
  }
  setToken(token: any) {
    return localStorage.setItem('access_token', token);
  }
  get isLoggedIn(): boolean {
    let authToken = localStorage.getItem('access_token');
    return authToken !== null ? true : false;
  }
  logout() {
    let removeToken = localStorage.removeItem('access_token');
    if (removeToken == null) {
      this.router.navigateByUrl('/login')
    }
  }
  // User profile
  getUserProfile(id: any): Observable<any> {
    let api = `${this.endpoint}/user-profile/${id}`;
    return this.http.get(api, { headers: this.headers }).pipe(
      catchError(this.handleError)
    );
  }
  // Error
  private handleError(error: HttpErrorResponse) {
    if (error.status === 0) {
      // A client-side or network error occurred. Handle it accordingly.
      alert('An error occurred:' + error.error);
    }else if(error.status === 403){
      alert("Vale kasutajanimi ja parool")
    }else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      alert(
        `Backend returned code ${error.status}, body was: `+ error.error);
    }
    // Return an observable with a user-facing error message.
    return throwError(() => new Error('Something bad happened; please try again later.'));
  }
}