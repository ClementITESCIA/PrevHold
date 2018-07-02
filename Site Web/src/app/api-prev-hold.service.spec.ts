import { TestBed, inject } from '@angular/core/testing';

import { ApiPrevHoldService } from './api-prev-hold.service';

describe('ApiPrevHoldService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ApiPrevHoldService]
    });
  });

  it('should be created', inject([ApiPrevHoldService], (service: ApiPrevHoldService) => {
    expect(service).toBeTruthy();
  }));
});
