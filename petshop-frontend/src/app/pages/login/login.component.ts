import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { LoginService } from '../../services/login.service';

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
    private loginService: LoginService
  ){
    this.loginForm = new FormGroup({
      login: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required])
    }, { updateOn: 'submit' })
  }

  submit(){
    try{
      //if (this.loading == true) return;
      this.loading = true;
      this.errorMessage = "";
      if (this.loginForm.invalid) {
        this.loginForm.markAllAsTouched(); // força exibir erros
        console.log("markAllAsTouched");
        return;
      }

      const { login, password } = this.loginForm.value;
      console.log('user: %s, passsword: %s', login, password);

      this.loginService.login(login, password).subscribe({
        next: (res) => {
          console.log('Login ok!', res);
          localStorage.setItem('token', res.token); // exemplo
          alert('Login realizado com sucesso!');
        },
        error: (err) => {
          console.error(err);
          this.errorMessage = 'Usuário ou senha inválidos';
        }
      });
    }finally{
      this.loading = false;
    }
  }

  navigate(){
    
  }
}
