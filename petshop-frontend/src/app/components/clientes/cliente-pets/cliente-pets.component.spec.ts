import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientePetsComponent } from './cliente-pets.component';

describe('ClientePetsComponent', () => {
  let component: ClientePetsComponent;
  let fixture: ComponentFixture<ClientePetsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClientePetsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ClientePetsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
