import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EnderecoEntity } from '../enderecos/enderecos.service';
import { PetEntity } from '../pets/pets.service';
import { ContatoEntity } from '../contatos/contatos.service';

export interface ClienteEntity {
  id: number;
  nome: string;
  cpf: string;
  dataCadastro: string;
  enderecos: EnderecoEntity[],
  contatos: ContatoEntity[],
  pets: PetEntity[]
}

@Injectable({
  providedIn: 'root'
})
export class ClientesService {

  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getItens(): Observable<ClienteEntity[]> {
    const headers = new HttpHeaders({ 
      'Content-Type': 'application/json'
    });

    console.log(`${this.apiUrl}/clientes`);

    return this.http.get<ClienteEntity[]>(`${this.apiUrl}/clientes`, { headers });
  }

  delete(id:number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/clientes/${id}`);
  }

  create(cliente: any) {
    return this.http.post(`${this.apiUrl}/clientes`, cliente);
  }

  update(id: number, cliente: any) {
    return this.http.put(`${this.apiUrl}/clientes/${id}`, cliente);
  }
}
