import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClienteContatoFormComponent } from './cliente-contato-form.component';

describe('ClienteContatoFormComponent', () => {
  let component: ClienteContatoFormComponent;
  let fixture: ComponentFixture<ClienteContatoFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClienteContatoFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ClienteContatoFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
