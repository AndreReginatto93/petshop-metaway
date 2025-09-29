import { Injectable } from '@angular/core';
import { jwtDecode } from "jwt-decode";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  getRole(): string | null {
    const token = localStorage.getItem('token');
    if (!token) return null;

    const decoded: any = jwtDecode(token);
    // console.log(token);
    // console.log(decoded);
    return decoded.role;
  }

  isAdmin(): boolean {
    return this.getRole() === 'ADMIN';
  }
}
