import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FicheComponent } from '../list/fiche.component';
import { FicheDetailComponent } from '../detail/fiche-detail.component';
import { FicheUpdateComponent } from '../update/fiche-update.component';
import { FicheRoutingResolveService } from './fiche-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const ficheRoute: Routes = [
  {
    path: '',
    component: FicheComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FicheDetailComponent,
    resolve: {
      fiche: FicheRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FicheUpdateComponent,
    resolve: {
      fiche: FicheRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FicheUpdateComponent,
    resolve: {
      fiche: FicheRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(ficheRoute)],
  exports: [RouterModule],
})
export class FicheRoutingModule {}
