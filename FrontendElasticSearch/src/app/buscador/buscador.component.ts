import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { of } from 'rxjs';
import { GeolocationService } from '../geolocation.service';

@Component({
  selector: 'app-buscador',
  templateUrl: './buscador.component.html',
  styleUrls: ['./buscador.component.css']
})
export class BuscadorComponent implements OnInit {
  lista: string[] = [];
  ipAddress = 'IP_A_VERIFICAR'; // Reemplaza con la IP que quieras verificar
  country: any;
  categoriaSeleccionada: string = ""; // Variable para almacenar la categoría seleccionada

  constructor(private http: HttpClient, private geolocationService: GeolocationService) { }

  ngOnInit(): void {
    this.obtenerIP();
  }

  // Método que se llama cuando se produce un evento de entrada
  onInputChange(event: Event): void {
    const inputElement = event.target as HTMLInputElement;
    const valor = inputElement.value;

    this.obtenerLista(valor).subscribe(
        (data: string[]) => {
          console.log(data)
          this.lista = data;
        },
        (error: any) => {
          console.error('Error al obtener la lista:', error);
        }
    );
 }

  onCategoriaChange(event: any): void {
      this.categoriaSeleccionada = event.target.value;
  }

  obtenerIP() {
    this.http.get<any>('https://api.ipify.org/?format=json').subscribe(res => {
      this.ipAddress = res.ip
      console.log(this.ipAddress);
      this.geolocationService.getCountryByIp(this.ipAddress)
      .then(response => {
        this.country = response.data.country_name;
        console.log(this.country)
      })
      .catch(error => {
        console.error('Error al obtener la geolocalización: ', error);
      });
    });  }

  obtenerLista(valor: String): Observable<any> {
    if(valor == ""){
      return of([]);
    }else{
      const url = 'http://localhost:8080/api/autoSuggest/' + this.country + "/" + valor;
      return this.http.get<string[]>(url);
    }
  }

  seleccionarItem(item: string): void {
    const inputElement = document.getElementById("buscador") as HTMLInputElement;
    inputElement.value = item;
    this.lista = []
  }
}
