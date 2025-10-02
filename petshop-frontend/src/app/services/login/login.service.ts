import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginResponse } from '../../types/login-response';
import { Observable, tap } from 'rxjs';
import { environment } from '../../environments/environment';

export interface UsuarioEntity {
  id: number;
  login: string;
  nome: string;
  role: string;
}

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private apiUrl = environment.apiUrl;
  
  constructor(private httpClient: HttpClient) { }

  login(login: string, password: string){
    console.log(this.apiUrl)

    const json = {
        login: login,
        password: password
    }

    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.httpClient.post<LoginResponse>(`${this.apiUrl}/auth/login`, json, { headers });
  }

  getItens(): Observable<UsuarioEntity[]> {
    const headers = new HttpHeaders({ 
      'Content-Type': 'application/json'
    });

    console.log(`${this.apiUrl}/auth`);

    return this.httpClient.get<UsuarioEntity[]>(`${this.apiUrl}/auth`, { headers });
  }


  register(user: any) {
    user = {
      ...user,
      password: user.role.toLowerCase()
    };
    
    return this.httpClient.post(`${this.apiUrl}/auth/register`, user);
  }

  delete(id:number): Observable<void> {
    return this.httpClient.delete<void>(`${this.apiUrl}/auth/${id}`);
  }
}
