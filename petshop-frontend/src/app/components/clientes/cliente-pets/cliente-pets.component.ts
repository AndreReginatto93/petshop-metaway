import { Component, Input } from '@angular/core';
import { PetEntity } from '../../../services/pets/pets.service';

@Component({
  selector: 'app-cliente-pets',
  imports: [],
  templateUrl: './cliente-pets.component.html',
  styleUrl: './cliente-pets.component.scss'
})
export class ClientePetsComponent {
  @Input() pets: PetEntity[] = [];

}
