import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { FicheFormService, FicheFormGroup } from './fiche-form.service';
import { IFiche } from '../fiche.model';
import { FicheService } from '../service/fiche.service';
import { eactivite } from 'app/entities/enumerations/eactivite.model';
import { escolarise } from 'app/entities/enumerations/escolarise.model';

@Component({
  selector: 'jhi-fiche-update',
  templateUrl: './fiche-update.component.html',
})
export class FicheUpdateComponent implements OnInit {
  isSaving = false;
  fiche: IFiche | null = null;
  eactiviteValues = Object.keys(eactivite);
  escolariseValues = Object.keys(escolarise);

  editForm: FicheFormGroup = this.ficheFormService.createFicheFormGroup();

  constructor(
    protected ficheService: FicheService,
    protected ficheFormService: FicheFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fiche }) => {
      this.fiche = fiche;
      if (fiche) {
        this.updateForm(fiche);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fiche = this.ficheFormService.getFiche(this.editForm);
    if (fiche.id !== null) {
      this.subscribeToSaveResponse(this.ficheService.update(fiche));
    } else {
      this.subscribeToSaveResponse(this.ficheService.create(fiche));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFiche>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(fiche: IFiche): void {
    this.fiche = fiche;
    this.ficheFormService.resetForm(this.editForm, fiche);
  }
}
