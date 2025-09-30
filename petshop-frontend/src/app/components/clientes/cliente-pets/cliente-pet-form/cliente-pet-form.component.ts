import { CommonModule } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatOptionModule } from '@angular/material/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { PetsService } from '../../../../services/pets/pets.service';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { RacaEntity, RacasService } from '../../../../services/racas/racas.service';

@Component({
  selector: 'app-cliente-pet-form',
  templateUrl: './cliente-pet-form.component.html',
  styleUrl: './cliente-pet-form.component.scss',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule,
    MatOptionModule,
  ]
})
export class ClientePetFormComponent  {
  form!: FormGroup;
  editing: boolean = false;
  owner?: number;
  racas: RacaEntity[] = [];

  constructor(
    private fb: FormBuilder,
    private petService: PetsService,
    private racasService: RacasService,
    private dialogRef: MatDialogRef<ClientePetFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any, // se houver registro para edição
  ) {}

  ngOnInit(): void {
    if (this.data.pet) {
      // edição
      this.editing = true;
    } else {
      // criação
      this.editing = false;
      this.owner = this.data.clienteId;
    }

    const now = new Date();
    now.setUTCHours(now.getUTCHours() -3);
    const dataHoraAtual = now.toISOString().substring(0,16);
    this.form = this.fb.group({
      racaId: [this.data?.pet?.raca?.id || '', Validators.required],
      nome: [this.data?.pet?.nome || '', Validators.required],
      dataNascimento: [this.data?.pet?.dataNascimento || dataHoraAtual, Validators.required]
    });

    // Carrega raças do backend
    this.racasService.getItens().subscribe({
      next: (res) => this.racas = res,
      error: (err) => console.error(err)
    });
  }

  salvar() {
    if (this.form.invalid) return;

    const pet = this.form.value;

    if (this.editing) {
      this.petService.update(this.data.pet.id, pet).subscribe({
        next: (res) => this.dialogRef.close(res),
        error: (err) => console.error(err)
      });
    } else {
      const pet = {
        ...this.form.value,
        clienteId: this.data.clienteId // veio pelo MAT_DIALOG_DATA
      };

      this.petService.create(pet).subscribe({
        next: (res) => this.dialogRef.close(res),
        error: (err) => console.error(err)
      });
    }
  }

  cancelar() {
    this.dialogRef.close();
  }
}
