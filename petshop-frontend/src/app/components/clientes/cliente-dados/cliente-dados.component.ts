import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ClienteEntity, ClientesService } from '../../../services/clientes/clientes.service';
import { MatInputModule } from "@angular/material/input";
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-cliente-dados',
  imports: [MatInputModule, ReactiveFormsModule, CommonModule],
  templateUrl: './cliente-dados.component.html',
  styleUrl: './cliente-dados.component.scss'
})
export class ClienteDadosComponent {
  @Input() cliente: ClienteEntity | null = null;
  @Output() salvar = new EventEmitter<any>(); 

  form!: FormGroup;
  editing: boolean = false;

  constructor(private fb: FormBuilder, 
              private clienteService: ClientesService,
              private router: Router,
  ) {}

  ngOnInit(): void {
    this.editing = !!this.cliente;

    console.log(`ng init, cliente: ${this.cliente}`)

    this.form = this.fb.group({
      nome: [this.cliente?.nome || '', Validators.required],
      cpf: [this.cliente?.cpf || '', [Validators.required, Validators.pattern(/^\d{11}$/)]],
      dataCadastro: [this.cliente?.dataCadastro || '', Validators.required]
    }, { updateOn: 'submit' });


    if (this.editing) {
      this.form.get('cpf')?.disable();
    }

    this.form.get('dataCadastro')?.disable();
  }

  onSalvar() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const dadosCliente = this.form.value;

    if (this.isUserPath()){
      this.clienteService.updateMe(this.cliente!.id, dadosCliente).subscribe({
          next: (res) => this.salvar.emit(res),
          error: (err) => console.error(err)
        });
    }else{
      if (this.editing) {
        this.clienteService.update(this.cliente!.id, dadosCliente).subscribe({
          next: (res) => this.salvar.emit(res),
          error: (err) => console.error(err)
        });
      } else {
        this.clienteService.create(dadosCliente).subscribe({
          next: (res) => this.salvar.emit(res),
          error: (err) => console.error(err)
        });
      }
    }
  }

  isUserPath(){
    const currentUrl = this.router.url;
    return currentUrl.includes("/profile");
  }
}