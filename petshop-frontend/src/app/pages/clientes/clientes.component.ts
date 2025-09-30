import { Component } from '@angular/core';
import { ClientesListComponent } from "../../components/clientes/clientes-list/clientes-list.component";
import { Router } from '@angular/router';

@Component({
  selector: 'app-clientes',
  imports: [ClientesListComponent],
  templateUrl: './clientes.component.html',
  styleUrl: './clientes.component.scss'
})
export class ClientesComponent {
  constructor(private router: Router){}

  createCliente() {
    //vai para cliente form
    this.router.navigate(['/clientes/novo']);
  }
}
