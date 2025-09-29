import { Component } from '@angular/core';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule, MatNavList } from '@angular/material/list';
import { AuthService } from '../../services/auth/auth.service';

@Component({
  selector: 'app-navbar',
  providers: [AuthService],
  imports: [RouterOutlet, RouterLink, MatListModule, MatSidenavModule, MatNavList, ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent {

  constructor(
    private router: Router, private authService: AuthService){}

  isAdmin(){
    return this.authService.isAdmin();
  }

  logout(){
    localStorage.removeItem('token');

    this.router.navigate(['/login']);
  }
}
