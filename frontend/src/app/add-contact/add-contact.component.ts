import { Component } from '@angular/core';
import { ContactService } from '../contact.service';
import { Contact } from '../contact';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-add-contact',
  templateUrl: './add-contact.component.html',
  styleUrls: ['./add-contact.component.css']
})
export class AddContactComponent {
  contact: Contact | undefined;
  haveSession: boolean = false;

  constructor(
    private router: Router,
    private contactService: ContactService,
    private formBuilder: FormBuilder,
    private authService: AuthService
  ) {
  }


  addContactForm = this.formBuilder.group({
    name: '',
    code: '',
    phone: ''
  });


  ngAfterContentInit() : void{
    this.haveSession = this.authService.getToken() == null;
    if(this.haveSession){
      this.router.navigateByUrl('/login')
    }
  }

  onSubmit(): void {
    if (this.addContactForm.valid) {
      var contact = this.addContactForm.value;
      this.contactService.addContact(contact).subscribe(contact => {
        this.addContactForm.reset();
        this.router.navigateByUrl('/contacts');
        
      });
    } else {
      
    }

  }
  ngOnChanges(){
    var contact = this.addContactForm.value;
    if(contact.code!= null && contact.name!= null && contact.phone != null){

    }else{
    }
  }
  cryptSecretPhrase(){
    var contact = this.addContactForm.value;
    if(contact.code!= null && contact.name!= null && contact.phone != null){
      var url = "http://localhost:8082/contact/hash-secret?secret=" + contact.code + "&name=" + contact.name;
      window.open(url, "_blank");
    }else{

    }
  
  }

}
