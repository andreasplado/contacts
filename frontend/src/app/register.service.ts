import { Injectable } from '@angular/core';

import { Observable, catchError, of, retry, throwError } from 'rxjs';
import { MessageService } from './message.service';
import { Contact } from './contact';
import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { ContactSearch } from './contactSearch';
import { AuthService } from './auth.service';
import { User } from './user';

@Injectable({ providedIn: 'root' })
export class RegisterService {
  contacts: any;
  contact: any;

  constructor(private messageService: MessageService, private http: HttpClient) {
  }


  signup(user: any): Observable<User>{
    this.http.post('http://localhost:8082/users/signup', user).pipe(
      catchError(this.handleError)
    ).subscribe(() => {

    });
    this.messageService.add(`Lisati kasutaja: ${user.username}`);
    return of(user);
  }

  private handleError(error: HttpErrorResponse) {
    if (error.status === 0) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      console.error(
        `Backend returned code ${error.status}, body was: `, error.error);
    }
    // Return an observable with a user-facing error message.
    return throwError(() => new Error('Something bad happened; please try again later.'));
  }
}
