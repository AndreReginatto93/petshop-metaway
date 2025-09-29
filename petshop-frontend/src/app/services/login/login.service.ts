import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginResponse } from '../../types/login-response';
import { tap } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private apiUrl = 'http://localhost:8080/api/v1/auth/login';
  
  constructor(private httpClient: HttpClient) { }

  login(login: string, password: string){
    console.log(this.apiUrl)

    const json = {
        login: login,
        password: password
    }

    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.httpClient.post<LoginResponse>(this.apiUrl, json, { headers });
  }
}
