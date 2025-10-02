import { Component, ViewChild } from '@angular/core';
import { ClienteDadosComponent } from "../../components/clientes/cliente-dados/cliente-dados.component";
import { ActivatedRoute, Router } from '@angular/router';
import { ClienteEntity, ClientesService } from '../../services/clientes/clientes.service';
import { MatTabsModule } from '@angular/material/tabs';
import { ClienteContatosComponent } from "../../components/clientes/cliente-contatos/cliente-contatos.component";
import { ClienteEnderecosComponent } from "../../components/clientes/cliente-enderecos/cliente-enderecos.component";
import { ClientePetsComponent } from "../../components/clientes/cliente-pets/cliente-pets.component";
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-profile',
  imports: [ClienteDadosComponent, MatTabsModule, ClienteContatosComponent, ClienteEnderecosComponent, ClientePetsComponent],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss'
})
export class ProfileComponent {
  @ViewChild(ClienteContatosComponent) contatoListComponent!: ClienteContatosComponent;
  @ViewChild(ClientePetsComponent) petListComponent!: ClientePetsComponent;
  @ViewChild(ClienteEnderecosComponent) enderecoListComponent!: ClienteEnderecosComponent;
  cliente: ClienteEntity | null = null;

  loading: boolean = true;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private clienteService: ClientesService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.loading = true;

    this.clienteService.findMe().subscribe({
      next: (res) => {
        this.cliente = res;
        this.loading = false;
      },
      error: (err) => {
        console.error(err);
        alert('Ocorreu um erro ao carregar o cliente.');
        
      }
    });
  }


  onSalvarCliente(clienteSalvo: any) {
    this.cliente = clienteSalvo;

    this.snackBar.open('Alteração gravada com sucesso!', 'Fechar', {
      duration: 3000,
      horizontalPosition: 'right',
      verticalPosition: 'top',
    });
  }
}
