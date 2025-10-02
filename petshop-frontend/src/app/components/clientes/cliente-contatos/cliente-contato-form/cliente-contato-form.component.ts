import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';
import { ContatosService } from '../../../../services/contatos/contatos.service';

@Component({
  selector: 'app-cliente-contato-form',
  templateUrl: './cliente-contato-form.component.html',
  styleUrl: './cliente-contato-form.component.scss',
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
export class ClienteContatoFormComponent {
  form!: FormGroup;
  editing: boolean = false;
  owner?: number;

  constructor(
    private fb: FormBuilder,
    private contatoService: ContatosService,
    private dialogRef: MatDialogRef<ClienteContatoFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any, // se houver registro para edição
  ) {}

  ngOnInit(): void {
    if (this.data.contato) {
      // edição
      this.editing = true;
    } else {
      // criação
      this.editing = false;
      this.owner = this.data.clienteId;
    }

    this.form = this.fb.group({
      tipo: [this.data?.contato?.tipo || 'EMAIL', Validators.required],
      valor: [this.data?.contato?.valor || '', [Validators.required, Validators.email]],
    }, { updateOn: 'submit' });

    // Observa mudanças no tipo
    this.form.get('tipo')?.valueChanges.subscribe(tipo => {
      const valorControl = this.form.get('valor');
      valorControl?.clearValidators(); 

      if (tipo === 'EMAIL') {
        valorControl?.setValidators([Validators.required, Validators.email]);
      } else if (tipo === 'TELEFONE') {
        valorControl?.setValidators([
          Validators.required,
          Validators.pattern(/^\d{10,11}$/) 
        ]);
      } else {
        valorControl?.setValidators([Validators.required]);
      }

      valorControl?.updateValueAndValidity();
    });
  }

  salvar() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const contato = this.form.value;

    if (this.editing) {
      this.contatoService.update(this.data.contato.id, contato).subscribe({
        next: (res) => this.dialogRef.close(res),
        error: (err) => console.error(err)
      });
    } else {
      const contato = {
        ...this.form.value,
        clienteId: this.data.clienteId // veio pelo MAT_DIALOG_DATA
      };

      this.contatoService.create(contato).subscribe({
        next: (res) => this.dialogRef.close(res),
        error: (err) => console.error(err)
      });
    }
  }

  cancelar() {
    this.dialogRef.close();
  }

}
