import { Component, Input } from '@angular/core';
import { EnderecoEntity } from '../../../services/enderecos/enderecos.service';

@Component({
  selector: 'app-cliente-enderecos',
  imports: [],
  templateUrl: './cliente-enderecos.component.html',
  styleUrl: './cliente-enderecos.component.scss'
})
export class ClienteEnderecosComponent {
  @Input() enderecos: EnderecoEntity[] = [];
}
