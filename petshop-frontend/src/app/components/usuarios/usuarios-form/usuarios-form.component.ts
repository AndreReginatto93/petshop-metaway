import { CommonModule } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatOptionModule } from '@angular/material/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { LoginService } from '../../../services/login/login.service';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-usuarios-form',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule,
    MatOptionModule,
  ],
  templateUrl: './usuarios-form.component.html',
  styleUrl: './usuarios-form.component.scss'
})
export class UsuariosFormComponent {
  form!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private usuarioService: LoginService,
    private dialogRef: MatDialogRef<UsuariosFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any, // se houver registro para edição
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      login: ['',  [Validators.required, Validators.pattern(/^\d{11}$/)]],
      nome: ['', Validators.required],
      role: ['USER', Validators.required],
    }, { updateOn: 'submit' });
    
  }

  salvar() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const usuario = this.form.value;

   
    this.usuarioService.register(usuario).subscribe({
      next: (res) => this.dialogRef.close(res),
      error: (err) => console.error(err)
    });
    
  }

  cancelar() {
    this.dialogRef.close();
  }
}
