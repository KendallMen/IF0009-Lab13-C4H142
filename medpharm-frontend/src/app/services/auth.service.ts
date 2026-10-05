import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs';
import { AuthResponse } from '../models';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly api = 'http://localhost:8080/api/v1';
  private readonly tokenKey = 'medpharm_token';
  readonly session = signal<AuthResponse | null>(this.readSession());
  constructor(private http: HttpClient) {}
  login(credentials: { username: string; password: string }) {
    return this.http.post<AuthResponse>(`${this.api}/auth/login`, credentials).pipe(tap(session => { localStorage.setItem(this.tokenKey, JSON.stringify(session)); this.session.set(session); }));
  }
  logout() { localStorage.removeItem(this.tokenKey); this.session.set(null); }
  getToken() { return this.session()?.token ?? null; }
  private readSession(): AuthResponse | null { try { return JSON.parse(localStorage.getItem(this.tokenKey) ?? 'null'); } catch { return null; } }
}
