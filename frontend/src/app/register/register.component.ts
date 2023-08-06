import { Component } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { RegisterService } from '../register.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  constructor(
    private router: Router,
    private registerService: RegisterService,
    private formBuilder: FormBuilder,
  ) {
  }


  addUserForm = this.formBuilder.group({
    username: '',
    password: ''
  });

  onSubmit(): void {
    if (this.addUserForm.valid) {
      var user = this.addUserForm.value;
      if(user.password === "" && user.username === ""){
        alert('Palun sisestage parool ja kasutaja');
      }else if(user.password === ""){
        alert('Palun sisestage parool');
      }else if(user.username === ""){
        alert('Palun sisestage kasutaja');
      }else{
      this.registerService.signup(user).subscribe(user => {
        this.addUserForm.reset();
        this.router.navigateByUrl('/login');
      });
    }
    } else {
      alert('Vorm ei ole valiidne');
    }

  }
}
