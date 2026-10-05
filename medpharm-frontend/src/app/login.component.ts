import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from './services/auth.service';

@Component({ selector: 'app-login', standalone: true, imports: [ReactiveFormsModule], template: `
<main class="login-page"><section class="login-panel"><p class="eyebrow">MEDPHARM EXPRESS</p><h1>Acceso al portal clínico</h1><p class="muted">Gestione recetas y despachos desde un solo lugar.</p>
<form [formGroup]="form" (ngSubmit)="submit()"><label>Usuario<input formControlName="username" autocomplete="username"></label>@if (invalid('username')) { <small>Ingrese su usuario.</small> }
<label>Contraseña<input type="password" formControlName="password" autocomplete="current-password"></label>@if (invalid('password')) { <small>Use al menos 6 caracteres.</small> }
@if (error) { <div class="error">{{ error }}</div> }<button type="submit" [disabled]="form.invalid || loading">{{ loading ? 'Ingresando...' : 'Iniciar sesión' }}</button></form><p class="hint">Demo: medico / medico123</p></section></main>` })
export class LoginComponent {
  private fb = inject(FormBuilder); private auth = inject(AuthService); private router = inject(Router);
  loading = false; error = '';
  form = this.fb.nonNullable.group({ username: ['', Validators.required], password: ['', [Validators.required, Validators.minLength(6)]] });
  invalid(name: 'username' | 'password') { const control = this.form.controls[name]; return control.invalid && control.touched; }
  submit() { if (this.form.invalid) { this.form.markAllAsTouched(); return; } this.loading = true; this.error = ''; this.auth.login(this.form.getRawValue()).subscribe({ next: () => this.router.navigateByUrl('/recetas'), error: () => { this.loading = false; this.error = 'Credenciales inválidas.'; } }); }
}
