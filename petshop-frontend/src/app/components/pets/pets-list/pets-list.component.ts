import { Component, Input } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PetEntity, PetsService } from '../../../services/pets/pets.service';
import { DataTableComponent, TableColumn } from '../../shared/data-table/data-table.component';
import { PetsFormComponent } from '../pets-form/pets-form.component';

@Component({
  selector: 'app-pets-list',
  imports: [DataTableComponent],
  templateUrl: './pets-list.component.html',
  styleUrl: './pets-list.component.scss'
})
export class PetsListComponent {
  pets: PetEntity[] = [];
  loading = true;

  columns: TableColumn[] = [
    { field: 'id', header: 'ID' },
    { field: 'nome', header: 'Nome' },
    { field: 'raca.descricao', header: 'Raça' },
    { field: 'dataNascimento', header: 'Data de Nascimento', type: 'date'},
  ];
  
  constructor(private petService: PetsService,
              private dialog: MatDialog,
  ) {}

  ngOnInit() {
    this.petService.getItens().subscribe({
      next: (res) => {
        this.pets = res;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      }
    });
  }

  adicionarPet(pet: any) {
    this.pets.unshift(pet);
  }

  onEdit(pet: PetEntity) {
    console.log('Editar', pet);

    const dialogRef = this.dialog.open(PetsFormComponent, {
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

