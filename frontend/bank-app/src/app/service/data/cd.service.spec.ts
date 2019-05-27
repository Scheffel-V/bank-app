import { TestBed } from '@angular/core/testing';

import { CdService } from './cd.service';

describe('CdService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CdService = TestBed.get(CdService);
    expect(service).toBeTruthy();
  });
});
