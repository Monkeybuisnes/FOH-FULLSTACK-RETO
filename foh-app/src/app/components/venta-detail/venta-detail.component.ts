import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Venta } from '../../models/venta.model';
import { VentaService } from '../../services/venta.service';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-venta-detail',
  templateUrl: './venta-detail.component.html',
  styleUrls: ['./venta-detail.component.scss']
})
export class VentaDetailComponent implements OnInit {
  venta: Venta | null = null;
  dataSource = new MatTableDataSource<any>([]);
  displayedColumns: string[] = ['productoId', 'cantidad', 'precioUnitario', 'subtotal'];
  loading = false;
  error: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private ventaService: VentaService
  ) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      const id = +idParam;
      this.cargarDetalle(id);
    } else {
      this.error = 'ID de venta inválido';
    }
  }

  cargarDetalle(id: number): void {
    this.loading = true;
    this.ventaService.obtenerPorId(id).subscribe({
      next: data => {
        this.venta = data;
        // Mapear items a tabla
        if (data.items) {
          const rows = data.items.map(item => ({
            productoId: item.productoId,
            cantidad: item.cantidad,
            precioUnitario: item.precioUnitario,
            subtotal: item.precioUnitario * item.cantidad
          }));
          this.dataSource.data = rows;
        }
        this.loading = false;
      },
      error: err => {
        this.error = err.error?.error || 'Error al obtener detalle de la venta';
        this.loading = false;
      }
    });
  }

  volver(): void {
    this.router.navigate(['/ventas']);
  }

  getTotal(): number {
    if (!this.venta || !this.venta.items) return 0;
    return this.venta.items.reduce((sum, item) => sum + item.precioUnitario * item.cantidad, 0);
  }
}
