import { VentaItem } from './venta-item.model';

export interface Venta {
  id?: number;
  clienteId: number;
  fecha?: string; // ISO string
  items: VentaItem[];
}
