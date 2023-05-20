import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFiche, NewFiche } from '../fiche.model';

export type PartialUpdateFiche = Partial<IFiche> & Pick<IFiche, 'id'>;

type RestOf<T extends IFiche | NewFiche> = Omit<T, 'dateMaj'> & {
  dateMaj?: string | null;
};

export type RestFiche = RestOf<IFiche>;

export type NewRestFiche = RestOf<NewFiche>;

export type PartialUpdateRestFiche = RestOf<PartialUpdateFiche>;

export type EntityResponseType = HttpResponse<IFiche>;
export type EntityArrayResponseType = HttpResponse<IFiche[]>;

@Injectable({ providedIn: 'root' })
export class FicheService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/fiches');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(fiche: NewFiche): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fiche);
    return this.http.post<RestFiche>(this.resourceUrl, copy, { observe: 'response' }).pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(fiche: IFiche): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fiche);
    return this.http
      .put<RestFiche>(`${this.resourceUrl}/${this.getFicheIdentifier(fiche)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(fiche: PartialUpdateFiche): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fiche);
    return this.http
      .patch<RestFiche>(`${this.resourceUrl}/${this.getFicheIdentifier(fiche)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestFiche>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestFiche[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFicheIdentifier(fiche: Pick<IFiche, 'id'>): string {
    return fiche.id;
  }

  compareFiche(o1: Pick<IFiche, 'id'> | null, o2: Pick<IFiche, 'id'> | null): boolean {
    return o1 && o2 ? this.getFicheIdentifier(o1) === this.getFicheIdentifier(o2) : o1 === o2;
  }

  addFicheToCollectionIfMissing<Type extends Pick<IFiche, 'id'>>(
    ficheCollection: Type[],
    ...fichesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const fiches: Type[] = fichesToCheck.filter(isPresent);
    if (fiches.length > 0) {
      const ficheCollectionIdentifiers = ficheCollection.map(ficheItem => this.getFicheIdentifier(ficheItem)!);
      const fichesToAdd = fiches.filter(ficheItem => {
        const ficheIdentifier = this.getFicheIdentifier(ficheItem);
        if (ficheCollectionIdentifiers.includes(ficheIdentifier)) {
          return false;
        }
        ficheCollectionIdentifiers.push(ficheIdentifier);
        return true;
      });
      return [...fichesToAdd, ...ficheCollection];
    }
    return ficheCollection;
  }

  protected convertDateFromClient<T extends IFiche | NewFiche | PartialUpdateFiche>(fiche: T): RestOf<T> {
    return {
      ...fiche,
      dateMaj: fiche.dateMaj?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restFiche: RestFiche): IFiche {
    return {
      ...restFiche,
      dateMaj: restFiche.dateMaj ? dayjs(restFiche.dateMaj) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestFiche>): HttpResponse<IFiche> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestFiche[]>): HttpResponse<IFiche[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
