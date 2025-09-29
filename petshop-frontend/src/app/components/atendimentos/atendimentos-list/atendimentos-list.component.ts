import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { TableColumn, DataTableComponent } from '../../shared/data-table/data-table.component';
import { AtendimentoEntity, AtendimentosService } from '../../../services/atendimentos/atendimentos.service';

@Component({
  selector: 'app-atendimento-list',
  templateUrl: './atendimentos-list.component.html',
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
  
  constructor(private atendimentoService: AtendimentosService) {}

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

  onEdit(atendimento: AtendimentoEntity) {
    console.log('Editar', atendimento);
  }

  onDelete(atendimento: AtendimentoEntity) {
    if (!confirm(`Deseja realmente excluir o atendimento ${atendimento.id}?`)) return;


    this.atendimentoService.delete(atendimento.id).subscribe({
      next: () => {
        // remover da lista local para atualizar a tabela
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
