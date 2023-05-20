import dayjs from 'dayjs/esm';

import { eactivite } from 'app/entities/enumerations/eactivite.model';
import { escolarise } from 'app/entities/enumerations/escolarise.model';

import { IFiche, NewFiche } from './fiche.model';

export const sampleWithRequiredData: IFiche = {
  id: '82d3cb9c-f412-4d34-ac0f-2ce500f3fc25',
};

export const sampleWithPartialData: IFiche = {
  id: '6694e9b1-96d5-4444-902d-6a4d27739c3a',
  leucinose: true,
  activite: eactivite['NP'],
  scolarise: escolarise['ECOLE_NORMAL'],
};

export const sampleWithFullData: IFiche = {
  id: 'b8a4c50a-c76d-43c0-9250-c02414f982de',
  homocystinurie: false,
  leucinose: false,
  tyrosinemie: false,
  dateMaj: dayjs('2023-05-20'),
  activite: eactivite['NON'],
  travail: false,
  scolarise: escolarise['ECOLE_INTEGRATION'],
};

export const sampleWithNewData: NewFiche = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
