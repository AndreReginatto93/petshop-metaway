import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';

export interface ContatoEntity {
  id: number;
  tag: string;
  tipo: string;
  valor: string;
}

@Injectable({
  providedIn: 'root'
})
export class ContatosService {

  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getItens(): Observable<ContatoEntity[]> {
    const headers = new HttpHeaders({ 
      'Content-Type': 'application/json'
    });

    console.log(`${this.apiUrl}/contatos`);

    return this.http.get<ContatoEntity[]>(`${this.apiUrl}/contatos`, { headers });
  }

  delete(id:number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/contatos/${id}`);
  }

  create(contato: any) {
    return this.http.post(`${this.apiUrl}/contatos`, contato);
  }

  update(id: number, contato: any) {
    return this.http.put(`${this.apiUrl}/contatos/${id}`, contato);
  }
}
