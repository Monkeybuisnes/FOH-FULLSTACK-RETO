import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { VentasListComponent } from './components/ventas-list/ventas-list.component';
import { VentaDetailComponent } from './components/venta-detail/venta-detail.component';

const routes: Routes = [
  { path: 'ventas', component: VentasListComponent },
  { path: 'ventas/:id', component: VentaDetailComponent },
  { path: '', redirectTo: '/ventas', pathMatch: 'full' },
  { path: '**', redirectTo: '/ventas' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
