import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';


@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})
export class LogoutComponent {

  isLoggedIn : boolean = false;


  constructor(private authService: AuthService,
    private router: Router){

  }

  ngAfterViewChecked(){
    if(this.authService.getToken() != null){
      this.isLoggedIn = true;
    }else{
      this.isLoggedIn = false;
    }
  }
  
  logOut(){
    if(this.authService.getToken() != null){
      this.authService.logout();
      this.isLoggedIn = false;
    }
  }
}
