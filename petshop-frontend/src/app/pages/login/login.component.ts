import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
    loginForm!: FormGroup;

  constructor(
  ){
    this.loginForm = new FormGroup({
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required, Validators.minLength(6)])
    }, { updateOn: 'submit' })
  }

  submit(){
    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched(); // força exibir erros
      return;
    }

    const { email, password } = this.loginForm.value;
    alert(`Login com sucesso: ${email}`);
  }

  navigate(){
    
  }
}
