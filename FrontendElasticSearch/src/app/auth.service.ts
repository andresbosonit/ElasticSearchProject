// auth.service.ts
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private token: string | null = null;

  constructor() { }

  setToken(token: string): void {
    this.token = token;
  }

  getToken() {
    return localStorage.getItem("token");
  }

  isLoggedIn(): boolean {
    return !!this.token;
  }
}
