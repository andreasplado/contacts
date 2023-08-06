import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';

import { Contact } from '../contact';
import { ContactService } from '../contact.service';
import { FormBuilder } from '@angular/forms';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-hero-detail',
  templateUrl: './contact-detail.component.html',
  styleUrls: [ './contact-detail.component.css' ]
})
export class ContactDetailComponent implements OnInit {
  contact: Contact | undefined;
  haveSession: boolean = false;

  updateContactForm = this.formBuilder.group({
    id: Number(this.route.snapshot.paramMap.get('id')),
    name: '',
    code: '',
    phone: ''
  });


  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private contactService: ContactService,
    private location: Location,
    private formBuilder: FormBuilder,
    private authService: AuthService
  ) {
    this.haveSession = authService.getToken() == null;
    if(this.haveSession){
      this.router.navigateByUrl('/login')
    }
  }

  ngOnInit(): void {
    this.getContact();
  }

  getContact(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.contactService.getContact(id).subscribe((response:Contact) => {
      this.contact = response;
  });;
  }

  goBack(): void {
    this.location.back();
  }

  deleteContact(): void{
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.contactService.deleteContact(id).subscribe((response:Contact) => {
      this.contact = response;
      this.router.navigateByUrl('/contacts')
    });
  }

  upload(event : any): void{
    this.file = event.target.files[0];
    this.fileChanged(event);
  }

  file:any;
  fileChanged(e: any) {
    let fileReader = new FileReader();
    fileReader.onload = (e) => {
      console.log(fileReader.result);
    }
    fileReader.readAsText(this.file);
  }

  updateContact(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (this.updateContactForm.valid) {
      var contact = this.updateContactForm.value;
      this.contactService.updateContact(id, contact).subscribe(contact => {
        this.updateContactForm.reset();
        this.router.navigateByUrl('/contacts')

      });
    } else {
    }
  }
  decryptSecretPhrase(){
    alert('Decrypt');
  }
}
