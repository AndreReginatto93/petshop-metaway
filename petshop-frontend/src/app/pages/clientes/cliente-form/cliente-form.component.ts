import { Component, ViewChild } from '@angular/core';
import { ClienteDadosComponent } from "../../../components/clientes/cliente-dados/cliente-dados.component";
import { ActivatedRoute, Router } from '@angular/router';
import { ClienteEntity, ClientesService } from '../../../services/clientes/clientes.service';
import { MatTabsModule } from '@angular/material/tabs';
import { ClienteContatosComponent } from "../../../components/clientes/cliente-contatos/cliente-contatos.component";
import { ClienteEnderecosComponent } from "../../../components/clientes/cliente-enderecos/cliente-enderecos.component";
import { ClientePetsComponent } from "../../../components/clientes/cliente-pets/cliente-pets.component";
import { MatDialog } from '@angular/material/dialog';
import { ClienteContatoFormComponent } from '../../../components/clientes/cliente-contatos/cliente-contato-form/cliente-contato-form.component';
import { ClientePetFormComponent } from '../../../components/clientes/cliente-pets/cliente-pet-form/cliente-pet-form.component';
import { ClienteEnderecoFormComponent } from '../../../components/clientes/cliente-enderecos/cliente-endereco-form/cliente-endereco-form.component';


@Component({
  selector: 'app-cliente-form',
  imports: [ClienteDadosComponent, MatTabsModule, ClienteContatosComponent, ClienteEnderecosComponent, ClientePetsComponent],
  templateUrl: './cliente-form.component.html',
  styleUrl: './cliente-form.component.scss'
})
export class ClienteFormComponent {
  @ViewChild(ClienteContatosComponent) contatoListComponent!: ClienteContatosComponent;
  @ViewChild(ClientePetsComponent) petListComponent!: ClientePetsComponent;
  @ViewChild(ClienteEnderecosComponent) enderecoListComponent!: ClienteEnderecosComponent;
  cliente: ClienteEntity | null = null;      // null para criação, objeto para edição
  editing: boolean = false;
  loading: boolean = true;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private clienteService: ClientesService,
    private dialog: MatDialog,
  ) {}

  ngOnInit(): void {
    this.loading = true;
  
    const id = this.route.snapshot.paramMap.get('id');

    if (id) {
      // edição
      this.editing = true;
      this.clienteService.findById(+id).subscribe({
        next: (res) => {
          this.cliente = res;
          this.loading = false;
        },
        error: (err) => {
          console.error(err);
          if (err.status === 404) {
            // cliente não encontrado, volta para lista
            this.router.navigate(['/clientes']);
          } else {
            // trata outros erros, opcional
            alert('Ocorreu um erro ao carregar o cliente.');
          }
          this.loading = false;
        }
      });
    } else {
      // criação
      this.editing = false;
      this.cliente = null; // pode deixar null ou inicializar com valores padrão
      this.loading = false;
    }
    
  }

  // chamado pelo app-cliente-dados
  onSalvarCliente(clienteSalvo: any) {
    this.cliente = clienteSalvo; // atualiza o cliente com o que veio do backend

    // caso seja criação, agora podemos liberar as outras abas
    if (!this.editing) {
      // navegar para /clientes/:id para padronizar a URL
      this.router.navigate(['/clientes', clienteSalvo.id]);
    }
  }


  createContato() {
    const dialogRef = this.dialog.open(ClienteContatoFormComponent, {
      data: { clienteId: this.cliente?.id }
    });
  
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.contatoListComponent.adicionarContato(result);
      }
    });
  }

  createEndereco() {
    const dialogRef = this.dialog.open(ClienteEnderecoFormComponent, {
      data: { clienteId: this.cliente?.id }
    });
  
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.enderecoListComponent.adicionarEndereco(result);
      }
    });
  }

  createPet() {
    const dialogRef = this.dialog.open(ClientePetFormComponent, {
      data: { clienteId: this.cliente?.id }
    });
  
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.petListComponent.adicionarPet(result);
      }
    });
  }
}