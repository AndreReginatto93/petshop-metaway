import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { TableColumn, DataTableComponent } from '../../shared/data-table/data-table.component';
import { AtendimentoEntity, AtendimentosService } from '../../../services/atendimentos/atendimentos.service';
import { MatDialog } from '@angular/material/dialog';
import { AtendimentoFormComponent } from '../atendimento-form/atendimento-form.component';

@Component({
  selector: 'app-atendimento-list',
  templateUrl: './atendimentos-list.component.html',
  styleUrls: ['./atendimentos-list.component.scss'],
  imports: [DataTableComponent]
})
export class AtendimentoListComponent implements OnInit {
  atendimentos: AtendimentoEntity[] = [];
  loading = true;

  columns: TableColumn[] = [
    { field: 'id', header: 'ID' },
    { field: 'descricao', header: 'Descricao' },
    { field: 'pet.nome', header: 'Nome do Pet' },
    { field: 'pet.raca.descricao', header: 'Raça' },
    { field: 'valor', header: 'Valor' },
    { field: 'dataAtendimento', header: 'Data do atendimento', type: 'date'  }
  ];
  
  constructor(private atendimentoService: AtendimentosService,
              private dialog: MatDialog,
  ) {}

  ngOnInit() {
    this.atendimentoService.getItens().subscribe({
      next: (res) => {
        this.atendimentos = res;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      }
    });
  }

  adicionarAtendimento(atendimento: any) {
    this.atendimentos.push(atendimento);
  }

  onEdit(atendimento: AtendimentoEntity) {
    console.log('Editar', atendimento);

    const dialogRef = this.dialog.open(AtendimentoFormComponent, {
      data: atendimento
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        const index = this.atendimentos.findIndex(a => a.id === result.id);
        if (index !== -1) this.atendimentos[index] = result;
      }
    });
  }

  onDelete(atendimento: AtendimentoEntity) {
    if (!confirm(`Deseja realmente excluir o atendimento ${atendimento.id}?`)) return;


    this.atendimentoService.delete(atendimento.id).subscribe({
      next: () => {
        this.atendimentos = this.atendimentos.filter(a => a.id !== atendimento.id);
        console.log('Excluído com sucesso');
      },
      error: (err) => {
        console.error('Erro ao excluir', err);
        alert('Não foi possível excluir o atendimento.');
      }
    });
  }
}
