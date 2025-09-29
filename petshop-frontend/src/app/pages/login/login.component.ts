import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { LoginService } from '../../services/login/login.service';
import { firstValueFrom } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [
    ReactiveFormsModule
  ],
  providers:[
    LoginService,
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  loginForm!: FormGroup;
  errorMessage: String = "";
  loading: boolean = false;

  constructor(
    private loginService: LoginService,
    private router: Router
  ){
    this.loginForm = new FormGroup({
      login: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required])
    }, { updateOn: 'submit' })
  }

  async submit(){
    try{
      if (this.loading == true) return;
      this.loading = true;
      
      this.errorMessage = "";
      if (this.loginForm.invalid) {
        this.loginForm.markAllAsTouched();
        return;
      }

      const { login, password } = this.loginForm.value;

      try {
        const res = await firstValueFrom(this.loginService.login(login, password));
        localStorage.setItem('token', res.token); // exemplo
        //alert('Login realizado com sucesso!');
        console.log(res);
        this.navigate();
      } catch (err: any) {
        console.error(err);
        this.errorMessage = 'Usuário ou senha inválidos';
      }
    }finally{
      this.loading = false;
    }
  }

  navigate(){
    this.router.navigate(['/atendimentos']);
  }
}
