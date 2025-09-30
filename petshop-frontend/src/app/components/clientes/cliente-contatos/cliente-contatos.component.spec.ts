import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClienteContatosComponent } from './cliente-contatos.component';

describe('ClienteContatosComponent', () => {
  let component: ClienteContatosComponent;
  let fixture: ComponentFixture<ClienteContatosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClienteContatosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ClienteContatosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
