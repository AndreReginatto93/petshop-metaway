import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { AuthService } from '../services/auth/auth.service';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    if (this.authService.isAdmin()) {
      return true;
    } else {
      // rota atual que tentou acessar
      const url = state.url;

      if (url.startsWith('/atendimentos')) {
        this.router.navigate(['/atendimentos/me']);
      } else if (url.startsWith('/clientes')) {
        this.router.navigate(['/profile']);
      } else {
        // fallback genérico
        this.router.navigate(['/atendimentos/me']);
      }

      return false;
    }
  }
}