import { Component, OnInit } from '@angular/core';
import { TableColumn, DataTableComponent } from '../../shared/data-table/data-table.component';
import { MatDialog } from '@angular/material/dialog';
import { ClienteEntity, ClientesService } from '../../../services/clientes/clientes.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-clientes-list',
  templateUrl: './clientes-list.component.html',
  styleUrl: './clientes-list.component.scss',
  imports: [DataTableComponent]
})
export class ClientesListComponent {
  clientes: ClienteEntity[] = [];
  loading = true;

  columns: TableColumn[] = [
    { field: 'id', header: 'ID' },
    { field: 'nome', header: 'Nome' },
    { field: 'cpf', header: 'CPF' },
    { field: 'dataCadastro', header: 'Data do cadastro', type: 'date'  }
  ];
  
  constructor(private clienteService: ClientesService,
              private dialog: MatDialog,
              private router: Router
  ) {}

  ngOnInit() {
    this.clienteService.getItens().subscribe({
      next: (res) => {
        this.clientes = res;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      }
    });
  }

  adicionarCliente(cliente: any) {
    this.clientes.push(cliente);
  }

  onEdit(cliente: ClienteEntity) {
    console.log('Editar', cliente);
    //vai para cliente form
    this.router.navigate([`/clientes/${cliente.id}`]);
  }

  onDelete(cliente: ClienteEntity) {
    if (!confirm(`Deseja realmente excluir o cliente ${cliente.id}?`)) return;


    this.clienteService.delete(cliente.id).subscribe({
      next: () => {
        this.clientes = this.clientes.filter(a => a.id !== cliente.id);
        console.log('Excluído com sucesso');
      },
      error: (err) => {
        console.error('Erro ao excluir', err);
        alert('Não foi possível excluir o cliente.');
      }
    });
  }
}
