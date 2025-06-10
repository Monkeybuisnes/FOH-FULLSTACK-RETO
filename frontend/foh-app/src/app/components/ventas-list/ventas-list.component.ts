import { Component, OnInit } from '@angular/core';
import { Venta } from '../../models/venta.model';
import { VentaService } from '../../services/venta.service';
import { MatTableDataSource } from '@angular/material/table';
import { FormControl } from '@angular/forms';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-ventas-list',
  templateUrl: './ventas-list.component.html',
  styleUrls: ['./ventas-list.component.scss']
})
export class VentasListComponent implements OnInit {
  ventas: Venta[] = [];
  dataSource = new MatTableDataSource<Venta>([]);
  displayedColumns: string[] = ['id', 'clienteId', 'fecha', 'acciones'];
  fechaDesdeControl = new FormControl();
  fechaHastaControl = new FormControl();
  loading = false;
  error: string | null = null;

  constructor(private ventaService: VentaService, private datePipe: DatePipe) {}

  ngOnInit(): void {
    // Inicializar fecha Desde = hoy -7, Hasta = hoy
    const hoy = new Date();
    const hace7 = new Date();
    hace7.setDate(hoy.getDate() - 7);
    this.fechaDesdeControl.setValue(hace7);
    this.fechaHastaControl.setValue(hoy);
    this.buscar();
  }

  buscar(): void {
    const desdeDate: Date | null = this.fechaDesdeControl.value;
    const hastaDate: Date | null = this.fechaHastaControl.value;
    if (!desdeDate || !hastaDate) {
      this.error = 'Debe ingresar ambas fechas.';
      return;
    }
    if (desdeDate > hastaDate) {
      this.error = 'Fecha Desde no puede ser posterior a Fecha Hasta.';
      return;
    }
    this.error = null;
    this.loading = true;
    // Convertir a yyyy-MM-dd
    const desdeStr = this.datePipe.transform(desdeDate, 'yyyy-MM-dd');
    const hastaStr = this.datePipe.transform(hastaDate, 'yyyy-MM-dd');
    if (!desdeStr || !hastaStr) {
      this.error = 'Error al formatear fechas.';
      this.loading = false;
      return;
    }
    this.ventaService.obtenerPorRangoFecha(desdeStr, hastaStr).subscribe({
      next: data => {
        this.ventas = data;
        this.dataSource.data = data;
        this.loading = false;
      },
      error: err => {
        this.error = err.error?.error || 'Error al obtener ventas';
        this.loading = false;
      }
    });
  }
}
