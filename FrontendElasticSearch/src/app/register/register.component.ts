import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  username: string = '';
  password: string = '';

  constructor(private http: HttpClient, private authService: AuthService, private router: Router) { }
  submit() {
    const url = 'http://localhost:8080/register';
  
    const requestData = {
      username: this.username,
      password: this.password
    };
  
    this.http.post<any>(url, requestData).toPromise().then((res) => {
      this.router.navigate(['/login']);
    }).catch(error => {
      console.error('Error:', error);
    });
  }
}
