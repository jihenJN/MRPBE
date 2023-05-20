import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FicheDetailComponent } from './fiche-detail.component';

describe('Fiche Management Detail Component', () => {
  let comp: FicheDetailComponent;
  let fixture: ComponentFixture<FicheDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FicheDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ fiche: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(FicheDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(FicheDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load fiche on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.fiche).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
