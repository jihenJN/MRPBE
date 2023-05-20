import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { FicheService } from '../service/fiche.service';

import { FicheComponent } from './fiche.component';

describe('Fiche Management Component', () => {
  let comp: FicheComponent;
  let fixture: ComponentFixture<FicheComponent>;
  let service: FicheService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'fiche', component: FicheComponent }]), HttpClientTestingModule],
      declarations: [FicheComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(FicheComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FicheComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(FicheService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 'ABC' }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.fiches?.[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
  });

  describe('trackId', () => {
    it('Should forward to ficheService', () => {
      const entity = { id: 'ABC' };
      jest.spyOn(service, 'getFicheIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getFicheIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
