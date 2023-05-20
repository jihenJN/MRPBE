import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FicheComponent } from './list/fiche.component';
import { FicheDetailComponent } from './detail/fiche-detail.component';
import { FicheUpdateComponent } from './update/fiche-update.component';
import { FicheDeleteDialogComponent } from './delete/fiche-delete-dialog.component';
import { FicheRoutingModule } from './route/fiche-routing.module';

@NgModule({
  imports: [SharedModule, FicheRoutingModule],
  declarations: [FicheComponent, FicheDetailComponent, FicheUpdateComponent, FicheDeleteDialogComponent],
})
export class FicheModule {}
