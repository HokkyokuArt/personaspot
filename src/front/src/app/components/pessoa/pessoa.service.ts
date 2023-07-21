import { Injectable } from '@angular/core';
import { SuperService } from 'src/app/common/super.service';
import { IPessoa } from './pessoa.model';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PessoaService extends SuperService<IPessoa>{
  private static REQUEST_PATH = 'pessoa';

  constructor(http: HttpClient) {
    super(PessoaService.REQUEST_PATH, http);
  }

  public getOneByCPF(cpf: string): Observable<IPessoa> {
    return this.http.get<any>(`${SuperService.BASE_URL}/${PessoaService.REQUEST_PATH}/cpf/${cpf}`);
  }

};
