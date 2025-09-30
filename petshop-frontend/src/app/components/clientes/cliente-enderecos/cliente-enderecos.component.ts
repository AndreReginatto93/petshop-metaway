import { Component, Input } from '@angular/core';
import { EnderecoEntity, EnderecosService } from '../../../services/enderecos/enderecos.service';
import { DataTableComponent, TableColumn } from '../../shared/data-table/data-table.component';
import { MatDialog } from '@angular/material/dialog';
import { ClienteEnderecoFormComponent } from './cliente-endereco-form/cliente-endereco-form.component';

@Component({
  selector: 'app-cliente-enderecos',
  templateUrl: './cliente-enderecos.component.html',
  styleUrl: './cliente-enderecos.component.scss',
  imports: [DataTableComponent],
})
export class ClienteEnderecosComponent {
  @Input() enderecos: EnderecoEntity[] = [];
  
  columns: TableColumn[] = [
    { field: 'id', header: 'ID' },
    { field: 'logradouro', header: 'Logradouro' },
    { field: 'cidade', header: 'Cidade' },
    { field: 'bairro', header: 'Bairro' },
    { field: 'complemento', header: 'Complemento' },
  ];
  
  constructor(private enderecoService: EnderecosService,
              private dialog: MatDialog,
  ) {}

  adicionarEndereco(endereco: any) {
    this.enderecos.unshift(endereco);
  }

  onEdit(endereco: EnderecoEntity) {
    console.log('Editar', endereco);

    const dialogRef = this.dialog.open(ClienteEnderecoFormComponent, {
      data: { endereco: endereco }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        const index = this.enderecos.findIndex(a => a.id === result.id);
        if (index !== -1) this.enderecos[index] = result;
      }
    });
  }

  onDelete(endereco: EnderecoEntity) {
    if (!confirm(`Deseja realmente excluir o endereço ${endereco.id}?`)) return;


    this.enderecoService.delete(endereco.id).subscribe({
      next: () => {
        this.enderecos = this.enderecos.filter(a => a.id !== endereco.id);
        console.log('Excluído com sucesso');
      },
      error: (err) => {
        console.error('Erro ao excluir', err);
        alert('Não foi possível excluir o endereço.');
      }
    });
  }
}
