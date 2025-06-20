import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';
import { Venta } from '../models/venta.model';

@Injectable({
  providedIn: 'root'
})
export class VentaService {
  private baseUrl = `${environment.apiBaseUrl}/ventas`;

  constructor(private http: HttpClient) {}

  crearVenta(venta: Venta): Observable<Venta> {
    return this.http.post<Venta>(this.baseUrl, venta);
  }

  obtenerPorId(id: number): Observable<Venta> {
    return this.http.get<Venta>(`${this.baseUrl}/${id}`);
  }

  obtenerPorRangoFecha(desde: string, hasta: string): Observable<Venta[]> {
    const params = new HttpParams().set('desde', desde).set('hasta', hasta);
    return this.http.get<Venta[]>(this.baseUrl, { params });
  }
}
