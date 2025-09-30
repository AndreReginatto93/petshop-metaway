import { CommonModule } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatOptionModule } from '@angular/material/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { EnderecosService } from '../../../../services/enderecos/enderecos.service';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-cliente-endereco-form',
  templateUrl: './cliente-endereco-form.component.html',
  styleUrl: './cliente-endereco-form.component.scss',
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
export class ClienteEnderecoFormComponent {
  form!: FormGroup;
  editing: boolean = false;
  owner?: number;

  constructor(
    private fb: FormBuilder,
    private enderecoService: EnderecosService,
    private dialogRef: MatDialogRef<ClienteEnderecoFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any, // se houver registro para edição
  ) {}

  ngOnInit(): void {
    if (this.data.endereco) {
      // edição
      this.editing = true;
    } else {
      // criação
      this.editing = false;
      this.owner = this.data.clienteId;
    }

    this.form = this.fb.group({
      logradouro: [this.data?.endereco?.logradouro || '', Validators.required],
      cidade: [this.data?.endereco?.cidade || '', Validators.required],
      bairro: [this.data?.endereco?.bairro || '', Validators.required],
      complemento: [this.data?.endereco?.complemento || ''],
    });
  }

  salvar() {
    if (this.form.invalid) return;

    const endereco = this.form.value;

    if (this.editing) {
      this.enderecoService.update(this.data.endereco.id, endereco).subscribe({
        next: (res) => this.dialogRef.close(res),
        error: (err) => console.error(err)
      });
    } else {
      const endereco = {
        ...this.form.value,
        clienteId: this.data.clienteId // veio pelo MAT_DIALOG_DATA
      };

      this.enderecoService.create(endereco).subscribe({
        next: (res) => this.dialogRef.close(res),
        error: (err) => console.error(err)
      });
    }
  }

  cancelar() {
    this.dialogRef.close();
  }
}
