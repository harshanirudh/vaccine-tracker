import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Form, FormControl, FormGroup, FormGroupDirective, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Observable, Subscription } from 'rxjs';
import { GlobalDialogComponent } from '../global-dialog/global-dialog.component';
import { SubscriptionService } from './subscription.service';

@Component({
  selector: 'app-subscription',
  templateUrl: './subscription.component.html',
  styleUrls: ['./subscription.component.css']
})
export class SubscriptionComponent implements OnInit,OnDestroy {
  @Input() type: boolean | undefined;
  vaccineForm!: FormGroup;
  load: boolean = false;
  unsubSevice!: Subscription;
  subService!:Subscription;
  districts = [
    { id: 581, value: 'Hyderabad' },
    { id: 603, value: 'Ranga Reddy' }
  ]
  vaccines = [
    { value: 'COVAXIN' },
    { value: 'COVISHIELD' },
    { value:'SPUTNIK V'}
  ]

  constructor(private service: SubscriptionService, public dialog: MatDialog) { }

  ngOnInit(): void {
    console.log(this.type);
    this.vaccineForm = new FormGroup({
      districtId: new FormControl('', Validators.required),
      vaccineType: new FormControl('', Validators.required),
      email: new FormControl('', [Validators.email, Validators.required])
    });
  }
  onSubmit(form: FormGroup, formDirective: FormGroupDirective) {
    this.load = true;
    console.log(form.value);
    if (this.type) {
      this.subService = this.service.subscribeToJob(form.value).subscribe(Response => {
        this.load = false;
        this.dialog.open(GlobalDialogComponent, {
          data: {
            title: 'Success',
            message: Response.message
          }
        });
        this.vaccineForm.reset();
        formDirective.resetForm();

      }, error => {
        this.load = false;
        this.dialog.open(GlobalDialogComponent, {
          data: {
            title: 'Failed',
            message: error.error.errorMessage
          }
        })
      });
    }
    else {

      this.unsubSevice = this.service.unsubscribeToJob(form.value).subscribe(Response => {
        this.load = false;
        this.dialog.open(GlobalDialogComponent, {
          data: {
            title: 'Success',
            message: Response.message
          }
        });
        this.vaccineForm.reset()
        formDirective.resetForm();
      }, error => {
        this.load = false;
        this.dialog.open(GlobalDialogComponent, {
          data: {
            title: 'Failed',
            message: error.error.errorMessage
          }
        })
      });
    }
  }
  ngOnDestroy(): void {
    this.subService.unsubscribe();
    this.unsubSevice.unsubscribe();
  }
}
