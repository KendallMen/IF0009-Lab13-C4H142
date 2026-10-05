import { Routes } from '@angular/router';
import { authGuard } from './guards/auth.guard';
import { LoginComponent } from './login.component';
import { RecetasListComponent } from './recetas-list.component';
import { RecetaFormComponent } from './receta-form.component';

export const routes: Routes = [
	{ path: '', pathMatch: 'full', redirectTo: 'recetas' },
	{ path: 'login', component: LoginComponent },
	{ path: 'recetas', component: RecetasListComponent, canActivate: [authGuard] },
	{ path: 'nueva-receta', component: RecetaFormComponent, canActivate: [authGuard] },
	{ path: '**', redirectTo: 'recetas' }
];
