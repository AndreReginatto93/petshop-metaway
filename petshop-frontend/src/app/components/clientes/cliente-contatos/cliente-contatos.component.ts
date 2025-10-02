import { Component, Input } from '@angular/core';
import { DataTableComponent, TableColumn } from '../../shared/data-table/data-table.component';
import { ContatoEntity, ContatosService } from '../../../services/contatos/contatos.service';
import { MatDialog } from '@angular/material/dialog';
import { ClienteContatoFormComponent } from './cliente-contato-form/cliente-contato-form.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cliente-contatos',
  templateUrl: './cliente-contatos.component.html',
  styleUrl: './cliente-contatos.component.scss',
  imports: [DataTableComponent]
})
export class ClienteContatosComponent {
  @Input() contatos: ContatoEntity[] = [];

  columns: TableColumn[] = [
    { field: 'id', header: 'ID' },
    { field: 'tipo', header: 'Tipo' },
    { field: 'valor', header: 'Valor' },
  ];
  
  constructor(private contatoService: ContatosService,
              private dialog: MatDialog,
              private router: Router,
  ) {}

  adicionarContato(contato: any) {
    this.contatos.unshift(contato);
  }

  onEdit(contato: ContatoEntity) {
    console.log('Editar', contato);

    const dialogRef = this.dialog.open(ClienteContatoFormComponent, {
      data: { contato: contato }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        const index = this.contatos.findIndex(a => a.id === result.id);
        if (index !== -1) this.contatos[index] = result;
      }
    });
  }

  onDelete(contato: ContatoEntity) {
    if (!confirm(`Deseja realmente excluir o contato ${contato.id}?`)) return;


    this.contatoService.delete(contato.id).subscribe({
      next: () => {
        this.contatos = this.contatos.filter(a => a.id !== contato.id);
        console.log('Excluído com sucesso');
      },
      error: (err) => {
        console.error('Erro ao excluir', err);
        alert('Não foi possível excluir o contato.');
      }
    });
  }

  isUserPath(){
    const currentUrl = this.router.url;
    return currentUrl.includes("/profile");
  }
}
