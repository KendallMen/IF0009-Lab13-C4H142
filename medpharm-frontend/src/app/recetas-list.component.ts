import { Component, computed, inject, signal } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { DatePipe } from '@angular/common';
import { Receta } from './models';
import { AuthService } from './services/auth.service';
import { MedpharmService } from './services/medpharm.service';

@Component({ selector: 'app-recetas-list', standalone: true, imports: [RouterLink, DatePipe], template: `
<div class="shell"><header><div><p class="eyebrow">MEDPHARM EXPRESS</p><h1>Recetas médicas</h1></div><div class="header-actions"><span>{{ auth.session()?.username }}</span><button class="secondary" (click)="logout()">Salir</button></div></header>
<main><div class="toolbar"><div><p class="muted">{{ filtered().length }} recetas encontradas</p></div><div class="filters">@for (option of statuses; track option) { <button [class.active]="status() === option" (click)="status.set(option)">{{ option }}</button> }<a routerLink="/nueva-receta" class="primary">+ Nueva receta</a></div></div>
@if (error()) { <div class="error">{{ error() }}</div> }<section class="table-wrap"><table><thead><tr><th>Receta</th><th>Paciente</th><th>Médico</th><th>Emisión</th><th>Estado</th><th>Acción</th></tr></thead><tbody>@for (receta of filtered(); track receta.id) {<tr><td><strong>{{ receta.codigoReceta }}</strong><small>{{ receta.detalles.length }} medicamento(s)</small></td><td>{{ receta.pacienteNombre }}</td><td>{{ receta.medico }}</td><td>{{ receta.fechaEmision | date:'dd/MM/yyyy HH:mm' }}</td><td><span class="badge" [class]="receta.estado.toLowerCase()">{{ receta.estado }}</span></td><td>@if (receta.estado === 'PENDIENTE') { <button class="action" (click)="changeStatus(receta, 'DESPACHADA')">Despachar</button><button class="cancel" (click)="changeStatus(receta, 'CANCELADA')">Cancelar</button> } @else { <span class="muted">Sin acciones</span> }</td></tr>} @empty {<tr><td colspan="6" class="empty">No hay recetas para este filtro.</td></tr>}</tbody></table></section></main></div>` })
export class RecetasListComponent {
  readonly statuses = ['TODAS', 'PENDIENTE', 'DESPACHADA', 'CANCELADA']; readonly recetas = signal<Receta[]>([]); readonly status = signal('TODAS'); readonly filtered = computed(() => this.status() === 'TODAS' ? this.recetas() : this.recetas().filter(r => r.estado === this.status()));
  readonly error = signal(''); private service = inject(MedpharmService); readonly auth = inject(AuthService); private router = inject(Router);
  constructor() { this.load(); }
  load() { this.service.getRecetas().subscribe({ next: data => this.recetas.set(data), error: (err) => this.error.set(`Error HTTP ${err.status || 'desconocido'}: no fue posible cargar las recetas sin autorización.`) }); }
  changeStatus(receta: Receta, estado: string) { this.service.cambiarEstado(receta.id, estado).subscribe({ next: updated => this.recetas.update(items => items.map(item => item.id === updated.id ? updated : item)), error: () => this.error.set('No fue posible actualizar el estado.') }); }
  logout() { this.auth.logout(); this.router.navigateByUrl('/login'); }
}
