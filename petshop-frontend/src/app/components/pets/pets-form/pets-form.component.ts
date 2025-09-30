import { CommonModule } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatOptionModule } from '@angular/material/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { PetsService } from '../../../services/pets/pets.service';
import { RacaEntity, RacasService } from '../../../services/racas/racas.service';
import { ClientePetFormComponent } from '../../clientes/cliente-pets/cliente-pet-form/cliente-pet-form.component';
import { ClienteEntity, ClientesService } from '../../../services/clientes/clientes.service';

@Component({
  selector: 'app-pets-form',
  templateUrl: './pets-form.component.html',
  styleUrl: './pets-form.component.scss',
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
export class PetsFormComponent  {
  form!: FormGroup;
  editing: boolean = false;
  owner?: number;
  racas: RacaEntity[] = [];
  clientes: ClienteEntity[] = [];

  constructor(
    private fb: FormBuilder,
    private petService: PetsService,
    private racasService: RacasService,
    private clienteService: ClientesService,
    private dialogRef: MatDialogRef<ClientePetFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any, // se houver registro para edição
  ) {}

  ngOnInit(): void {
    if (this.data?.pet) {
      // edição
      this.editing = true;
    } else {
      // criação
      this.editing = false;
    }

    const now = new Date();
    now.setUTCHours(now.getUTCHours() -3);
    const dataHoraAtual = now.toISOString().substring(0,16);

    if (this.editing){
      this.form = this.fb.group({
        racaId: [this.data?.pet?.raca?.id || '', Validators.required],
        nome: [this.data?.pet?.nome || '', Validators.required],
        dataNascimento: [this.data?.pet?.dataNascimento || dataHoraAtual, Validators.required]
      });
    }else{
      this.form = this.fb.group({
        clienteId: ['', Validators.required],
        racaId: [this.data?.pet?.raca?.id || '', Validators.required],
        nome: [this.data?.pet?.nome || '', Validators.required],
        dataNascimento: [this.data?.pet?.dataNascimento || dataHoraAtual, Validators.required]
      });
    }

    this.racasService.getItens().subscribe({
      next: (res) => this.racas = res,
      error: (err) => console.error(err)
    });

    //Apenas para criar
    if (!this.editing){
      this.clienteService.getItens().subscribe({
        next: (res) => this.clientes = res,
        error: (err) => console.error(err)
      });
    }
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
