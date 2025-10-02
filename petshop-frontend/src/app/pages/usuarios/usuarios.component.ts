import { Component, ViewChild } from '@angular/core';
import { UsuariosListComponent } from '../../components/usuarios/usuarios-list/usuarios-list.component';
import { MatDialog } from '@angular/material/dialog';
import { UsuariosFormComponent } from '../../components/usuarios/usuarios-form/usuarios-form.component';
import { LoginService } from '../../services/login/login.service';

@Component({
  selector: 'app-usuarios',
  imports: [UsuariosListComponent],
  templateUrl: './usuarios.component.html',
  styleUrl: './usuarios.component.scss'
})
export class UsuariosComponent {
  @ViewChild(UsuariosListComponent) listComponent!: UsuariosListComponent;
  
  constructor(private usuarioService: LoginService,
              private dialog: MatDialog,
  ) {}

  createUsuario() {
    console.log('Abrir formulário de novo usuario');
    
    const dialogRef = this.dialog.open(UsuariosFormComponent);
  
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.listComponent.adicionarUsuario(result);
      }
    });
  }
}

