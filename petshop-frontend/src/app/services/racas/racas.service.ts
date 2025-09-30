import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface RacaEntity {
  id: number;
  descricao: string;
  especie: string;
}

@Injectable({
  providedIn: 'root'
})
export class RacasService {

  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getItens(): Observable<RacaEntity[]> {
    const headers = new HttpHeaders({ 
      'Content-Type': 'application/json'
    });

    console.log(`${this.apiUrl}/racas`);

    return this.http.get<RacaEntity[]>(`${this.apiUrl}/racas`, { headers });
  }

  delete(id:number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/racas/${id}`);
  }

  create(raca: any) {
    return this.http.post(`${this.apiUrl}/racas`, raca);
  }

  update(id: number, raca: any) {
    return this.http.put(`${this.apiUrl}/racas/${id}`, raca);
  }
}
