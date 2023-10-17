// login.component.ts
import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username: string = '';
  password: string = '';

  constructor(private http: HttpClient, private authService: AuthService, private router: Router) { }

  submit() {
    const url = 'http://localhost:8080/login';
  
    const requestData = {
      username: this.username,
      password: this.password
    };
  
    this.http.post<any>(url, requestData).toPromise().then((res: any) => {
      if (typeof res.token === 'string') {
        console.log('Token JWT:', res.token);
        this.authService.setToken(res.token);
        localStorage.setItem("token", res.token)
        this.router.navigate(['/buscador']);
      } else {
        console.log('Respuesta del servidor:', res);
      }
    }).catch(error => {
      console.error('Error:', error);
    });
  }

  register(){
    this.router.navigate(['/register']);
  }
}
