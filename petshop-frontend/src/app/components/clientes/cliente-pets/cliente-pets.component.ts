import { Component, Input } from '@angular/core';
import { PetEntity, PetsService } from '../../../services/pets/pets.service';
import { TableColumn, DataTableComponent } from '../../shared/data-table/data-table.component';
import { MatDialog } from '@angular/material/dialog';
import { ClientePetFormComponent } from './cliente-pet-form/cliente-pet-form.component';

@Component({
  selector: 'app-cliente-pets',
  imports: [DataTableComponent],
  templateUrl: './cliente-pets.component.html',
  styleUrl: './cliente-pets.component.scss'
})
export class ClientePetsComponent {
  @Input() pets: PetEntity[] = [];

  columns: TableColumn[] = [
    { field: 'id', header: 'ID' },
    { field: 'nome', header: 'Nome' },
    { field: 'raca.descricao', header: 'Raça' },
    { field: 'dataNascimento', header: 'Data de Nascimento', type: 'date'},
  ];
  
  constructor(private petService: PetsService,
              private dialog: MatDialog,
  ) {}

  adicionarPet(pet: any) {
    this.pets.unshift(pet);
  }

  onEdit(pet: PetEntity) {
    console.log('Editar', pet);

    const dialogRef = this.dialog.open(ClientePetFormComponent, {
      data: { pet: pet }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        const index = this.pets.findIndex(a => a.id === result.id);
        if (index !== -1) this.pets[index] = result;
      }
    });
  }

  onDelete(pet: PetEntity) {
    if (!confirm(`Deseja realmente excluir o pet ${pet.id}?`)) return;


    this.petService.delete(pet.id).subscribe({
      next: () => {
        this.pets = this.pets.filter(a => a.id !== pet.id);
        console.log('Excluído com sucesso');
      },
      error: (err) => {
        console.error('Erro ao excluir', err);
        alert('Não foi possível excluir o pet.');
      }
    });
  }
}

