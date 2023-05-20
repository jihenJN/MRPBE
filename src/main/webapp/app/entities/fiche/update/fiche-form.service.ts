import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFiche, NewFiche } from '../fiche.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFiche for edit and NewFicheFormGroupInput for create.
 */
type FicheFormGroupInput = IFiche | PartialWithRequiredKeyOf<NewFiche>;

type FicheFormDefaults = Pick<NewFiche, 'id' | 'homocystinurie' | 'leucinose' | 'tyrosinemie' | 'travail'>;

type FicheFormGroupContent = {
  id: FormControl<IFiche['id'] | NewFiche['id']>;
  homocystinurie: FormControl<IFiche['homocystinurie']>;
  leucinose: FormControl<IFiche['leucinose']>;
  tyrosinemie: FormControl<IFiche['tyrosinemie']>;
  dateMaj: FormControl<IFiche['dateMaj']>;
  activite: FormControl<IFiche['activite']>;
  travail: FormControl<IFiche['travail']>;
  scolarise: FormControl<IFiche['scolarise']>;
};

export type FicheFormGroup = FormGroup<FicheFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FicheFormService {
  createFicheFormGroup(fiche: FicheFormGroupInput = { id: null }): FicheFormGroup {
    const ficheRawValue = {
      ...this.getFormDefaults(),
      ...fiche,
    };
    return new FormGroup<FicheFormGroupContent>({
      id: new FormControl(
        { value: ficheRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      homocystinurie: new FormControl(ficheRawValue.homocystinurie),
      leucinose: new FormControl(ficheRawValue.leucinose),
      tyrosinemie: new FormControl(ficheRawValue.tyrosinemie),
      dateMaj: new FormControl(ficheRawValue.dateMaj),
      activite: new FormControl(ficheRawValue.activite),
      travail: new FormControl(ficheRawValue.travail),
      scolarise: new FormControl(ficheRawValue.scolarise),
    });
  }

  getFiche(form: FicheFormGroup): IFiche | NewFiche {
    return form.getRawValue() as IFiche | NewFiche;
  }

  resetForm(form: FicheFormGroup, fiche: FicheFormGroupInput): void {
    const ficheRawValue = { ...this.getFormDefaults(), ...fiche };
    form.reset(
      {
        ...ficheRawValue,
        id: { value: ficheRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): FicheFormDefaults {
    return {
      id: null,
      homocystinurie: false,
      leucinose: false,
      tyrosinemie: false,
      travail: false,
    };
  }
}
