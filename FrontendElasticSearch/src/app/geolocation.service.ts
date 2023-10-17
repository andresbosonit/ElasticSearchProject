import { Injectable } from '@angular/core';
import axios from 'axios';

@Injectable({
  providedIn: 'root'
})
export class GeolocationService {
  private apiKey = '11455b95b9fd0d6bc2498b5a73403dd0'; // Reemplaza con tu clave API de Ipstack

  getCountryByIp(ip: string) {
    const apiUrl = `http://api.ipstack.com/${ip}?access_key=${this.apiKey}`;
    return axios.get(apiUrl);
  }
}
