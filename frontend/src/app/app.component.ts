import { Component } from '@angular/core';
import { AuthService } from './auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Kontaktid';
  token :any = "";
  haveSession : boolean = false;



  constructor(private authService: AuthService, private router: Router){
    if(authService.getToken() != ""){
      this.token = authService.getToken()?.toString();
      this.haveSession = true;
      this.router.navigateByUrl('/dashboard');
    }else{
      this.router.navigateByUrl('/login');
    }
  }
}
