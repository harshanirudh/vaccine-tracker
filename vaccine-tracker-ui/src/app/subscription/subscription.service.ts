import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {

  baseUrl:String=environment.baseUrl;

  constructor(private http: HttpClient) { }
  public subscribeToJob(formValues:any){
    let params = new HttpParams();
    // Begin assigning parameters
    params = params.append('email', formValues.email);
    params = params.append('districtId', formValues.districtId);
    params = params.append('vaccine', formValues.vaccineType);

    return this.http.get<any>(this.baseUrl+'subscription/add',{params:params});
  }

  unsubscribeToJob(formValues: any) {
    let params = new HttpParams();
    params = params.append('email', formValues.email);
    params = params.append('districtId', formValues.districtId);
    params = params.append('vaccine', formValues.vaccineType);

    return this.http.delete<any>(this.baseUrl+'subscription/remove',{params:params});
  }
}
