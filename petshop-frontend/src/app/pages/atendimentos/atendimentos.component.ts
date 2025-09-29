import { Component, ViewChild } from '@angular/core';
import { AtendimentoListComponent } from "../../components/atendimentos/atendimentos-list/atendimentos-list.component";
import { AtendimentosService } from '../../services/atendimentos/atendimentos.service';
import { MatDialog } from '@angular/material/dialog';
import { AtendimentoFormComponent } from '../../components/atendimentos/atendimento-form/atendimento-form.component';

@Component({
  selector: 'app-atendimentos',
  imports: [AtendimentoListComponent],
  templateUrl: './atendimentos.component.html',
  styleUrl: './atendimentos.component.scss'
})
export class AtendimentosComponent {
  @ViewChild(AtendimentoListComponent) listComponent!: AtendimentoListComponent;
  
  constructor(private atendimentoService: AtendimentosService,
              private dialog: MatDialog,
  ) {}

  abrirNovoAtendimento() {
    console.log('Abrir formulário de novo atendimento');
    
    const dialogRef = this.dialog.open(AtendimentoFormComponent);
  
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.listComponent.adicionarAtendimento(result);
      }
    });
  }

}
