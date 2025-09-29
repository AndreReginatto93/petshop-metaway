import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AtendimentosService } from '../../../services/atendimentos/atendimentos.service';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { PetsService } from '../../../services/pets/pets.service';
import { MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';

@Component({
  selector: 'app-atendimento-form',
  templateUrl: './atendimento-form.component.html',
  styleUrls: ['./atendimento-form.component.scss'],
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
export class AtendimentoFormComponent implements OnInit {
  form!: FormGroup;
  pets: any[] = [];
  editing: boolean = false;

  constructor(
    private fb: FormBuilder,
    private atendimentoService: AtendimentosService,
    private petService: PetsService,
    private dialogRef: MatDialogRef<AtendimentoFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any // se houver registro para edição
  ) {}

  ngOnInit(): void {
    this.editing = !!this.data;
    const now = new Date();
    now.setUTCHours(now.getUTCHours() -3);
    const dataHoraAtual = now.toISOString().substring(0,16);
    this.form = this.fb.group({
      petId: [this.data?.pet?.id || '', Validators.required],
      descricao: [this.data?.descricao || '', Validators.required],
      valor: [this.data?.valor || '', Validators.required],
      dataAtendimento: [this.data?.dataAtendimento || dataHoraAtual, Validators.required]
    });

    // Carrega pets do backend
    this.petService.getItens().subscribe({
      next: (res) => this.pets = res,
      error: (err) => console.error(err)
    });
  }

  salvar() {
    if (this.form.invalid) return;

    const atendimento = this.form.value;

    if (this.editing) {
      this.atendimentoService.update(this.data.id, atendimento).subscribe({
        next: (res) => this.dialogRef.close(res),
        error: (err) => console.error(err)
      });
    } else {
      this.atendimentoService.create(atendimento).subscribe({
        next: (res) => this.dialogRef.close(res),
        error: (err) => console.error(err)
      });
    }
  }

  cancelar() {
    this.dialogRef.close();
  }
}
