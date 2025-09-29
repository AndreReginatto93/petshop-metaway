import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

export interface AtendimentoEntity {
  id: number;
  pet: any;
  descricao: string;
  valor: number;
  dataAtendimento: string;
}

@Injectable({
  providedIn: 'root'
})
export class AtendimentosService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getItens(): Observable<AtendimentoEntity[]> {
    const headers = new HttpHeaders({ 
      'Content-Type': 'application/json'
    });

    console.log(`${this.apiUrl}/atendimentos`);

    return this.http.get<AtendimentoEntity[]>(`${this.apiUrl}/atendimentos`, { headers });
  }

  delete(id:number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/atendimentos/${id}`);
  }
}
