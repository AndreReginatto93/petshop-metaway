import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { PrimaryInputComponent } from "./components/primary-input/primary-input.component";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, PrimaryInputComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'petshop-frontend';
}
