import { ComponentFixture, TestBed } from '@angular/core/testing';
import { VentaDetailComponent } from './venta-detail.component';
import { Venta } from '../../models/venta.model';
import { VentaService } from '../../services/venta.service';


describe('VentaDetailComponent', () => {
  let component: VentaDetailComponent;
  let fixture: ComponentFixture<VentaDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VentaDetailComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VentaDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
