import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Medicamento, Receta } from '../models';

@Injectable({ providedIn: 'root' })
export class MedpharmService {
  private readonly api = 'http://localhost:8080/api/v1';
  constructor(private http: HttpClient) {}
  getRecetas() { return this.http.get<Receta[]>(`${this.api}/recetas`); }
  getMedicamentos() { return this.http.get<Medicamento[]>(`${this.api}/medicamentos`); }
  crearReceta(payload: unknown) { return this.http.post<Receta>(`${this.api}/recetas`, payload); }
  cambiarEstado(id: number, estado: string) { return this.http.patch<Receta>(`${this.api}/recetas/${id}/estado`, { estado }); }
}
