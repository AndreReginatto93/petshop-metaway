import { Component } from '@angular/core';
import { DataTableComponent, TableColumn } from '../../shared/data-table/data-table.component';
import { LoginService, UsuarioEntity } from '../../../services/login/login.service';
import { MatDialog } from '@angular/material/dialog';
import { UsuariosFormComponent } from '../usuarios-form/usuarios-form.component';

@Component({
  selector: 'app-usuarios-list',
  imports: [DataTableComponent],
  templateUrl: './usuarios-list.component.html',
  styleUrl: './usuarios-list.component.scss'
})
export class UsuariosListComponent {
  usuarios: UsuarioEntity[] = [];
  loading = true;

  columns: TableColumn[] = [
    { field: 'id', header: 'ID' },
    { field: 'login', header: 'Login' },
    { field: 'nome', header: 'Nome' },
    { field: 'role', header: 'Role'},
  ];
  
  constructor(private usuarioService: LoginService,
              private dialog: MatDialog,
  ) {}

  ngOnInit() {
    this.usuarioService.getItens().subscribe({
      next: (res) => {
        this.usuarios = res;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      }
    });
  }

  adicionarUsuario(usuario: any) {
    this.usuarios.unshift(usuario);
  }

  onEdit(usuario: UsuarioEntity) {
    console.log('Editar', usuario);

    const dialogRef = this.dialog.open(UsuariosFormComponent, {
      data: { usuario: usuario }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        const index = this.usuarios.findIndex(a => a.id === result.id);
        if (index !== -1) this.usuarios[index] = result;
      }
    });
  }

  onDelete(usuario: UsuarioEntity) {
    if (!confirm(`Deseja realmente excluir o usuario ${usuario.id}?`)) return;

    this.usuarioService.delete(usuario.id).subscribe({
      next: () => {
        this.usuarios = this.usuarios.filter(a => a.id !== usuario.id);
        console.log('Excluído com sucesso');
      },
      error: (err) => {
        console.error('Erro ao excluir', err);
        alert('Não foi possível excluir o usuario.');
      }
    });
  }
}

