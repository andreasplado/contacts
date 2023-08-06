import { Component, OnInit } from '@angular/core';

import { Contact } from '../contact';
import { ContactService } from '../contact.service';
import { FormBuilder } from '@angular/forms';
import { ContactSearch } from '../contactSearch';
import { AuthService } from '../auth.service';
import { Route, Router } from '@angular/router';
import { MessageService } from '../message.service';

@Component({
  selector: 'app-heroes',
  templateUrl: './contacts.component.html',
  styleUrls: ['./contacts.component.css']
})
export class ContactsComponent implements OnInit {
  contacts: Contact[] = [];
  currentPage = 0;
  limit: number = 0;
  pages: number[] = [];
  contactSearchModel: ContactSearch | undefined;
  keyword: string = '';
  showPagination : boolean = true;
  haveSession = false;

  constructor(private contactService: ContactService,
    private authService: AuthService, private messageService : MessageService,
    private formBuilder: FormBuilder, private router: Router) {
    }

  ngOnInit(): void {
    this.getContacts();
  }
  
  ngDoCheck() : void{
    this.haveSession = this.authService.getToken() == null;
    if(this.haveSession){
      this.router.navigateByUrl('/login')
    }
  }

  onPageChange(page: number): void {
    this.currentPage = page;
    this.contactService.getContacts(this.currentPage).subscribe(contacts => this.contacts = contacts);
  }

  getContacts(): void {
    this.contactService.getContacts(0).subscribe(contacts =>  {
      this.contacts = contacts;
      this.pages = this.range(0, contacts.length);
      this.limit = contacts.length-1;
    });
  }

  range(start: number, end: number): number[] {
    return [...Array(end).keys()].map((el) => el + (start));
  }

  

  searchContactForm = this.formBuilder.group({
    name: '',
    phone: ''
  });


  findContact():void{
    var searchObject = this.searchContactForm.value;
    if(searchObject.name === ''){
      this.contactService.getAllContacts().subscribe(contacts =>{
        this.messageService.add('Leiti kõik kontaktid!');
        this.contacts = contacts;
        this.showPagination = true;
      });
    }else{
      this.contactService.getContactBySearch(searchObject).subscribe(contacts =>{
        this.contacts = contacts;
        this.showPagination = false;
        if(searchObject.name){
          this.keyword = searchObject.name;
        }else if(searchObject.name && searchObject.phone){
          this.keyword = searchObject.name + " numbriga " + searchObject.phone
        }
      });
    }

  }
}
