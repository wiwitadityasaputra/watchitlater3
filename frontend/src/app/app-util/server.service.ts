import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import 'rxjs/Rx';

import { AuthModel } from './Auth.model';

@Injectable()
export class ServerService {
  private domain: string = 'http://localhost:8080';
  private token: string;

  constructor(private http: Http) {}

  getGitStatus() {
    const url: string = this.domain +  '/api/git-status?' + this.getAuthQueryParam();
    return this.http.get(url);
  }

  refreshToken() {
    const url: string = this.domain +  '/api/auth/refresh-token?' + this.getAuthQueryParam();
    this.http.get(url).map(
      (res: Response) => {
        let data: any = res.json();
        this.token = data.token;
        return data;
      }
    ).subscribe((value: any) =>{
      console.log('afterRefreshToken ', value);
    });
  }

  me() {
    const url: string = this.domain +  '/api/auth/me?' + this.getAuthQueryParam();
    return this.http.get(url).map(
      (res: Response) => {
        let data: any = res.json();

        this.refreshToken();

        return data;
      }
    );
  }

  login() {
    const url: string = this.domain +  '/api/auth/signin';
    return this.http.post(url, {}).map(
      (res: Response) => {
        let data: any = res.json();
        this.token = data.token;
        return data;
      }
    );
  }

  logout() {
    const url: string = this.domain +  '/api/auth/signout';
    return this.http.post(url, {});
  }

  getAuthQueryParam() {
    return '&query-param-auth=' + this.token;
  }
}