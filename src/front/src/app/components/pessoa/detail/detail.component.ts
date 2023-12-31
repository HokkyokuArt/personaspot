import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialog, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatInputModule } from '@angular/material/input';
import { PessoaService } from '../pessoa.service';
import { ActivatedRoute, Router } from '@angular/router';
import { finalize } from 'rxjs';
import { LayoutService } from '../../layout/layout.service';

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit {

  form!: FormGroup;

  submitted = false;
  isNew = false;
  isEdit = false;
  uuid?= '';

  config = [
    { label: 'Nome', property: 'nome' },
    { label: 'Telefone', property: 'telefone' },
    { label: 'Email', property: 'email' }
  ];


  get listPorperties() {
    const list = this.config.map(s => s.property);
    list.push('actions');
    return list;
  }

  constructor(
    private fb: FormBuilder,
    public dialog: MatDialog,
    protected service: PessoaService,
    protected router: Router,
    protected layoutService: LayoutService,
    private route: ActivatedRoute,
  ) { }

  ngOnInit(): void {
    const routePath = this.route.snapshot.url[0].path;
    this.uuid = this.route.snapshot.url[1]?.path;

    if (routePath === 'view') {
      this.isEdit = false;
      this.isNew = false;
    }

    if (routePath === 'edit') {
      this.isEdit = true;
      this.isNew = false;
    }

    if (routePath === 'new') {
      this.isEdit = true;
      this.isNew = true;
    }

    this.form = this.fb.group({
      uuid: this.fb.control(''),
      version: this.fb.control(0),
      nome: this.fb.control({ value: '', disabled: !this.isEdit && !this.isNew }, Validators.required),
      cpf: this.fb.control({ value: '', disabled: !this.isEdit && !this.isNew }, Validators.required),
      dataNascimento: this.fb.control({ value: '', disabled: !this.isEdit && !this.isNew }, Validators.required),
      contatos: this.fb.control({ value: [], disabled: !this.isEdit && !this.isNew })
    });

    if (!this.isNew) {
      this.service.getOne(this.uuid).subscribe({
        next: v => {
          this.form.patchValue(v);
        }
      });
    }
  }

  openDialog() {
    const dialogRef = this.dialog.open(DialogAnimationsExampleDialog, {
      width: '500px'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result.nome)
        this.form.get('contatos')?.setValue([...this.form.get('contatos')?.value, result]);
    });
  }


  onDelete(e: { nome: string, telefone: string, email: string; }) {
    const filtered = this.form.get('contatos')?.value
      .filter((s: any) => s.nome != e.nome && s.telefone !== e.telefone && s.email !== e.email);
    this.form.get('contatos')?.setValue(filtered);
  }

  disabled = false;
  onSave() {
    this.disabled = true;

    const dn = new Date(this.form.get('dataNascimento')?.value).toISOString().split('T')[0];
    this.form.get('dataNascimento')?.setValue(dn);
    this.service.save(this.form.getRawValue())
      .pipe(finalize(() => this.disabled = false))
      .subscribe({
        next: v => {
          this.router.navigate(['/pessoa']);
        },
        error: er => {
          this.layoutService.error('Erro');
        },
      });
  }

  onUpdate() {
    this.disabled = true;
    const dn = new Date(this.form.get('dataNascimento')?.value).toISOString().split('T')[0];
    this.form.get('dataNascimento')?.setValue(dn);

    this.service.update(this.uuid!, this.form.getRawValue())
      .pipe(finalize(() => this.disabled = false))
      .subscribe({
        next: v => {
          this.router.navigate(['/pessoa']);
        },
        error: er => {
          this.layoutService.error('Erro');
        },
      });
  }

  onClick() {
    if (this.isNew) {
      this.onSave();
    } else {
      this.onUpdate();
    }
  }

}


@Component({
  selector: 'dialog-animations-example-dialog',
  template: `<h1 mat-dialog-title>Adicionar contato</h1>
  <form [formGroup]="form" mat-dialog-content>
    <mat-form-field style="width: 100%;">
      <mat-label>Nome *</mat-label>
      <input formControlName="nome" matInput type="text" autofocus>
    </mat-form-field>

    <mat-form-field style="width: 100%;">
      <mat-label>Telefone *</mat-label>
      <input formControlName="telefone" matInput type="text" >
    </mat-form-field>

    <mat-form-field style="width: 100%;">
      <mat-label>Email *</mat-label>
      <input formControlName="email" matInput type="text" autofocus>
    </mat-form-field>
  </form>
  <div mat-dialog-actions>
    <button mat-button mat-dialog-close>Cancelar</button>
    <button [disabled]="!form.valid" mat-button [mat-dialog-close]="form.getRawValue()" cdkFocusInitial>Salvar</button>
  </div>`,
  standalone: true,
  imports: [MatDialogModule, MatButtonModule, MatInputModule, ReactiveFormsModule,
    FormsModule,],
})
export class DialogAnimationsExampleDialog {
  constructor(
    public dialogRef: MatDialogRef<DialogAnimationsExampleDialog>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private fb: FormBuilder
  ) { }

  form!: FormGroup;
  ngOnInit(): void {
    this.form = this.fb.group({
      uuid: this.fb.control(''),
      version: this.fb.control(0),
      nome: this.fb.control('', Validators.required),
      telefone: this.fb.control('', Validators.required),
      email: this.fb.control('', Validators.required)
    });
  }
}
