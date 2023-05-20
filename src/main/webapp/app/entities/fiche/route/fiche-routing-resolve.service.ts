import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFiche } from '../fiche.model';
import { FicheService } from '../service/fiche.service';

@Injectable({ providedIn: 'root' })
export class FicheRoutingResolveService implements Resolve<IFiche | null> {
  constructor(protected service: FicheService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFiche | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((fiche: HttpResponse<IFiche>) => {
          if (fiche.body) {
            return of(fiche.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
