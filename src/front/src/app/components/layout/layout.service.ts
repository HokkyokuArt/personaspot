import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class LayoutService {
  constructor(protected snackBar: MatSnackBar) { }

  error(er: string) {
    this.snackBar.open(er, undefined,
      {
        duration: 3000,
        horizontalPosition: 'end',
        verticalPosition: 'top',
      });
  }

}
