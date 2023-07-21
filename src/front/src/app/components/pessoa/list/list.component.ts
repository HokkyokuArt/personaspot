import { Component } from "@angular/core";
import { ListSuperComponent } from "src/app/common/list-super.component";
import { LayoutService } from "../../layout/layout.service";
import { IPessoa } from "../pessoa.model";
import { PessoaService } from "../pessoa.service";
import { Router } from "@angular/router";


@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent extends ListSuperComponent<IPessoa, PessoaService>  {

  constructor(_service: PessoaService, layoutService: LayoutService, router: Router) {
    super(_service, layoutService, router);

    this.config = [
      { label: 'Nome', property: 'nome' },
      { label: 'CPF', property: 'cpf' },
      { label: 'Data de nascimento', property: 'dataNascimento' }
    ];
  }

  iptValue: string = '';

  get listPorperties() {
    const list = this.config.map(s => s.property);
    list.push('actions');
    return list;
  }

  onSearch() {
    if (this.iptValue) {
      this.getOneByCPF(this.iptValue);
    } else {
      this.getList(undefined, undefined, undefined, 'nome');
    }
  }

  onClear() {
    this.iptValue = '';
    this.onSearch();
  }

  onClickNew() {
    this.router.navigate(['/pessoa/new']);
  }

  onClickEdit(uuid: string) {
    this.router.navigate(['/pessoa/edit/' + uuid]);
  }

  onClickView(uuid: string) {
    this.router.navigate(['/pessoa/view/' + uuid]);
  }

  public getOneByCPF(cpf: string): void {
    this.service.getOneByCPF(cpf).subscribe({
      next: v => {
        if (this.list)
          this.list.content = Array.of(v);
      },
      error: er => {
        this.layoutService.error('Erro');
      }
    });
  }

  override gerSortOrderList(): string {
    return 'nome';
  }
}
