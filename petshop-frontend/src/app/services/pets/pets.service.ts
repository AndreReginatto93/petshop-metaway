import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface PetEntity {
  id: number;
  pet: any;
  descricao: string;
  valor: number;
  dataAtendimento: string;
}

@Injectable({
  providedIn: 'root'
})
export class PetsService {

  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getItens(): Observable<PetEntity[]> {
    const headers = new HttpHeaders({ 
      'Content-Type': 'application/json'
    });

    console.log(`${this.apiUrl}/pets`);

    return this.http.get<PetEntity[]>(`${this.apiUrl}/pets`, { headers });
  }

  delete(id:number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/pets/${id}`);
  }

  create(pet: any) {
    return this.http.post(`${this.apiUrl}/pets`, pet);
  }

  update(id: number, pet: any) {
    return this.http.put(`${this.apiUrl}/pets/${id}`, pet);
  }
}
