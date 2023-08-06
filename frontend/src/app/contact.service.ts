import { Injectable } from '@angular/core';

import { Observable, catchError, of, retry, throwError } from 'rxjs';
import { MessageService } from './message.service';
import { Contact } from './contact';
import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { ContactSearch } from './contactSearch';
import { AuthService } from './auth.service';

@Injectable({ providedIn: 'root' })
export class ContactService {
  contacts: any;
  contact: any;

  constructor(private messageService: MessageService, private http: HttpClient, private authService: AuthService) {
  }

  getContacts(currentPage: number): Observable<Contact[]> {

    const url: string = 'http://localhost:8082/contact/few';
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.authService.getToken()}`
    })

    this.messageService.add(`Leiti kontaktid lehelt:${currentPage}`);

    return this.http.get<Contact[]>(url, {
      params: new HttpParams().set('pageSize', 4).set('pageNo', currentPage),
      headers: headers
    });
  }

  getAllContacts(): Observable<Contact[]> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.authService.getToken()}`
    })

    const url: string = 'http://localhost:8082/contact/few';
    return this.http.get<Contact[]>(url, {headers: headers});
  }

  getContact(id: number): Observable<Contact> {
    const url: string = 'http://localhost:8082/contact/' + id;
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.authService.getToken()}`
    })

    this.messageService.add(`Leiti kontakt, kelle id on:${id}`);
    let customHeaders = new Headers({ Authorization: "Bearer " + localStorage.getItem("token")});
    return this.http.get<Contact>(url, {headers: headers}).pipe(
      retry(3), // retry a failed request up to 3 times
      //catchError(this.handleError) // then handle the error
    );
  }
  getContactBySearch(contactSearch: any): Observable<Contact[]> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.authService.getToken()}`
    })
    const url: string = 'http://localhost:8082/contact/get?name=' + contactSearch.name;
    return this.http.get<Contact[]>(url, {headers: headers});
  }


  addContact(contact: any): Observable<Contact>{
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.authService.getToken()}`
    })
    this.http.post('http://localhost:8082/contact/', contact, {headers: headers}).subscribe(() => {

    });
    this.messageService.add(`Lisati kontakt: ${contact}`);
    return of(contact);
  }

  updateContact(id: number, contact: any): Observable<Contact>{
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.authService.getToken()}`
    })
    this.http.put('http://localhost:8082/contact/' + id , contact, {headers: headers}).subscribe(() => {
      this.messageService.add(`Uuendati kontakt: ${contact.name}`);
    });
    return of(contact);
  }

  deleteContact(id: number): Observable<Contact>{
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.authService.getToken()}`
    })
    this.http.delete('http://localhost:8082/contact/' + id, {headers: headers}).pipe(
      catchError(this.handleError)
    ).subscribe((data) => {
        this.contact = data;
        this.messageService.add(`Kustutati kontakt=${this.contact}`);
    });
    return of(this.contact);
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
