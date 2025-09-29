import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { AtendimentosComponent } from './pages/atendimentos/atendimentos.component';
import { AuthGuard } from './guards/auth.guard';
import { NavbarComponent } from './components/navbar/navbar.component';
import { AdminGuard } from './guards/admin.guard';
import { ClientesComponent } from './pages/clientes/clientes.component';
import { UsuariosComponent } from './pages/usuarios/usuarios.component';
import { PetsComponent } from './pages/pets/pets.component';

export const routes: Routes = [
    {
        path: '',
        component: NavbarComponent,    
        canActivate: [AuthGuard],
        children: [
            { path: 'atendimentos', component: AtendimentosComponent },
            { path: 'pets', component: PetsComponent },
            { path: 'clientes', component: ClientesComponent, canActivate: [AdminGuard], },
            { path: 'usuarios', component: UsuariosComponent, canActivate: [AdminGuard], },
        ]
    },
    { path: 'login', component: LoginComponent },

    { path: '**', redirectTo: 'atendimentos' }
];
