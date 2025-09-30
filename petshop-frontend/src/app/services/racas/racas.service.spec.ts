import { TestBed } from '@angular/core/testing';

import { RacasService } from './racas.service';

describe('RacasService', () => {
  let service: RacasService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RacasService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
