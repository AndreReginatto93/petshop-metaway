import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientePetFormComponent } from './cliente-pet-form.component';

describe('ClientePetFormComponent', () => {
  let component: ClientePetFormComponent;
  let fixture: ComponentFixture<ClientePetFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClientePetFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ClientePetFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
