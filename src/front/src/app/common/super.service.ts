import { Inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { IPageable } from './pageable.model';
import { ISuperModel } from './super.model';


@Injectable({
  providedIn: 'root'
})
export abstract class SuperService<T extends ISuperModel> {

  constructor(@Inject(String) protected path: string, protected http: HttpClient) { }

  public static BASE_URL = location.origin.replace('4200', '8080');

  public list(page: number, pageSize: number, sortDirection: 'ASC' | 'DESC', field: string): Observable<IPageable<T>> {
    return this.http.get<any>(`${SuperService.BASE_URL}/${this.path}?page=${page}&pageSize=${pageSize}&sortDirection=${sortDirection}&field=${field}`);
  }

  public getOne(uuid: string): Observable<T> {
    return this.http.get<any>(`${SuperService.BASE_URL}/${this.path}/${uuid}`);
  }

  public save(body: T): Observable<T> {
    return this.http.post<any>(`${SuperService.BASE_URL}/${this.path}`, body);
  }

  public update(uuid: string, body: T): Observable<T> {
    return this.http.put<any>(`${SuperService.BASE_URL}/${this.path}/${uuid}`, body);
  }

  public delete(uuid: string): Observable<T> {
    return this.http.delete<any>(`${SuperService.BASE_URL}/${this.path}/${uuid}`);
  }
}
