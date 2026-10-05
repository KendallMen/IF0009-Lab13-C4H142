import { Component, inject } from '@angular/core';
import { AbstractControl, FormArray, FormBuilder, ReactiveFormsModule, ValidationErrors, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { MedpharmService } from './services/medpharm.service';
import { Medicamento } from './models';

export function positivoValidator(control: AbstractControl): ValidationErrors | null { const value = Number(control.value); return Number.isInteger(value) && value > 0 ? null : { positivo: true }; }

@Component({ selector: 'app-receta-form', standalone: true, imports: [ReactiveFormsModule, RouterLink], template: `
<div class="shell"><header><div><p class="eyebrow">MEDPHARM EXPRESS</p><h1>Nueva receta</h1></div><a routerLink="/recetas" class="secondary">Volver al tablero</a></header><main><section class="form-card"><p class="muted">Complete los datos del paciente y agregue los medicamentos prescritos.</p><form [formGroup]="form" (ngSubmit)="submit()"><label>Nombre del paciente<input formControlName="pacienteNombre"></label>@if (form.controls.pacienteNombre.invalid && form.controls.pacienteNombre.touched) { <small>El nombre es requerido y debe tener 5 caracteres.</small> }
<div formArrayName="detalles" class="details"><div class="section-title"><h2>Medicamentos</h2><button type="button" class="secondary" (click)="addDetail()">+ Agregar línea</button></div>@for (group of details.controls; track $index) {<div class="detail-row" [formGroupName]="$index"><select formControlName="medicamentoId"><option value="">Seleccione un medicamento</option>@for (med of medicamentos; track med.id) {<option [value]="med.id">{{ med.nombre }} · stock {{ med.stock }}</option>}</select><input type="number" min="1" formControlName="cantidad" placeholder="Cantidad"><input formControlName="dosisIndicada" placeholder="Dosis indicada"><button type="button" class="icon-button" (click)="removeDetail($index)" [disabled]="details.length === 1">×</button>@if (group.get('cantidad')?.invalid && group.get('cantidad')?.touched) { <small>Cantidad debe ser un entero mayor que 0.</small> }</div>}</div>@if (error) { <div class="error">{{ error }}</div> }<button class="primary" type="submit" [disabled]="form.invalid || saving">{{ saving ? 'Guardando...' : 'Registrar receta' }}</button></form></section></main></div>` })
export class RecetaFormComponent {
  private fb = inject(FormBuilder); private service = inject(MedpharmService); private router = inject(Router); medicamentos: Medicamento[] = []; error = ''; saving = false;
  form = this.fb.nonNullable.group({ pacienteNombre: ['', [Validators.required, Validators.minLength(5)]], detalles: this.fb.array([this.detailGroup()]) });
  constructor() { this.service.getMedicamentos().subscribe({ next: data => this.medicamentos = data, error: () => this.error = 'No fue posible cargar el catálogo.' }); }
  detailGroup() { return this.fb.nonNullable.group({ medicamentoId: ['', Validators.required], cantidad: [1, [Validators.required, positivoValidator]], dosisIndicada: ['', Validators.required] }); }
  get details() { return this.form.controls.detalles as FormArray; }
  addDetail() { this.details.push(this.detailGroup()); }
  removeDetail(index: number) { this.details.removeAt(index); }
  submit() { if (this.form.invalid) { this.form.markAllAsTouched(); return; } this.saving = true; this.service.crearReceta(this.form.getRawValue()).subscribe({ next: () => this.router.navigateByUrl('/recetas'), error: err => { this.saving = false; this.error = err.error?.detail ?? 'No fue posible registrar la receta.'; } }); }
}
