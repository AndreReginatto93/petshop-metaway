import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClienteEnderecoFormComponent } from './cliente-endereco-form.component';

describe('ClienteEnderecoFormComponent', () => {
  let component: ClienteEnderecoFormComponent;
  let fixture: ComponentFixture<ClienteEnderecoFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClienteEnderecoFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ClienteEnderecoFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
