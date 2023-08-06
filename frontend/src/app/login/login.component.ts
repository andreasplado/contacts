import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  isLogoutVisible : boolean = false;

  constructor(private fb: FormBuilder,
    private authService: AuthService,
    private router: Router, private formBuilder: FormBuilder){
      
  }
  isLoggedIn : boolean = false;
  loginForm = this.formBuilder.group({
    username: '',
    password: '',
  });

  login(){
    if (this.loginForm.valid) {
      var username = this.loginForm.value.username;
      var password = this.loginForm.value.password;
      if(username?.length == 0){
        alert('Paun sisestage kasutajanimi');
      }else if( password?.length == 0){
        alert('Paun sisestage parool');
      }else if( username?.length == 0 && password?.length == 0){
        alert('Paun sisestage kasutajanimi ja parool');
      }else{
      
      this.authService.login(username, password).subscribe((resp) => {
        this.loginForm.reset();
        this.authService.setToken(resp.token);
        this.isLoggedIn = true;
        this.router.navigateByUrl('/dashboard')

      });
    }
    }
    console.warn('Your have been logged in', this.loginForm.value);
  }
}
