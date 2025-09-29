import { Component } from '@angular/core';
import { AtendimentoListComponent } from "../../components/atendimentos/atendimentos-list/atendimentos-list.component";

@Component({
  selector: 'app-atendimentos',
  imports: [AtendimentoListComponent],
  templateUrl: './atendimentos.component.html',
  styleUrl: './atendimentos.component.scss'
})
export class AtendimentosComponent {


  abrirNovoAtendimento() {
    console.log('Abrir formulário de novo atendimento');
  }
}
