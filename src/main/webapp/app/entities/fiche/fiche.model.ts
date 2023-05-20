import dayjs from 'dayjs/esm';
import { eactivite } from 'app/entities/enumerations/eactivite.model';
import { escolarise } from 'app/entities/enumerations/escolarise.model';

export interface IFiche {
  id: string;
  homocystinurie?: boolean | null;
  leucinose?: boolean | null;
  tyrosinemie?: boolean | null;
  dateMaj?: dayjs.Dayjs | null;
  activite?: eactivite | null;
  travail?: boolean | null;
  scolarise?: escolarise | null;
}

export type NewFiche = Omit<IFiche, 'id'> & { id: null };
