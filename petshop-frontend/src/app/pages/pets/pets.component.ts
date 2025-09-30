import { Component, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { PetsListComponent } from '../../components/pets/pets-list/pets-list.component';
import { PetsService } from '../../services/pets/pets.service';
import { MatDialog } from '@angular/material/dialog';
import { PetsFormComponent } from '../../components/pets/pets-form/pets-form.component';

@Component({
  selector: 'app-pets',
  imports: [PetsListComponent],
  templateUrl: './pets.component.html',
  styleUrl: './pets.component.scss'
})
export class PetsComponent {
  @ViewChild(PetsListComponent) listComponent!: PetsListComponent;
  
  constructor(private petService: PetsService,
              private dialog: MatDialog,
  ) {}

  createPet() {
    console.log('Abrir formulário de novo pet');
    
    const dialogRef = this.dialog.open(PetsFormComponent);
  
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.listComponent.adicionarPet(result);
      }
    });
  }
}
