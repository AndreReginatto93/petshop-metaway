import { Component } from '@angular/core';
import { ClientesListComponent } from "../../components/clientes/clientes-list/clientes-list.component";

@Component({
  selector: 'app-clientes',
  imports: [ClientesListComponent],
  templateUrl: './clientes.component.html',
  styleUrl: './clientes.component.scss'
})
export class ClientesComponent {

  createCliente() {
    
  }
}
