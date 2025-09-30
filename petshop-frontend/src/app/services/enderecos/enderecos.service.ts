import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

export interface EnderecoEntity {
  id: number;
  tag: string;
  tipo: string;
  valor: string;
}

@Injectable({
  providedIn: 'root'
})
export class EnderecosService {

  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getItens(): Observable<EnderecoEntity[]> {
    const headers = new HttpHeaders({ 
      'Content-Type': 'application/json'
    });

    console.log(`${this.apiUrl}/enderecos`);

    return this.http.get<EnderecoEntity[]>(`${this.apiUrl}/enderecos`, { headers });
  }

  delete(id:number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/enderecos/${id}`);
  }

  create(endereco: any) {
    return this.http.post(`${this.apiUrl}/enderecos`, endereco);
  }

  update(id: number, endereco: any) {
    return this.http.put(`${this.apiUrl}/enderecos/${id}`, endereco);
  }
}
