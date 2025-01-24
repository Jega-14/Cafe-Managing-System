import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class SnackbarService {

  constructor(private snakBar:MatSnackBar) { }

  openSnackBar(message:string,action:string){
    if(action == 'error'){
      this.snakBar.open(message,'',{
        horizontalPosition:'center',
        verticalPosition:'top',
        duration:2,
        panelClass:['black-snackbar']
      });
    }
    else{
      this.snakBar.open(message,'',{
        horizontalPosition:'center',
        verticalPosition:'top',
        duration:2,
        panelClass:['green-snackbar']
      });
    }
  }
}
