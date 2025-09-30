import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClienteEnderecosComponent } from './cliente-enderecos.component';

describe('ClienteEnderecosComponent', () => {
  let component: ClienteEnderecosComponent;
  let fixture: ComponentFixture<ClienteEnderecosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClienteEnderecosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ClienteEnderecosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
