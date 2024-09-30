import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Universe} from "../model/universe";

@Injectable({
  providedIn: 'root'
})
export class QueryService {

  constructor(private http: HttpClient) {

  }

  getResponseFor(queryInputValue: string, universe1: Universe, currentSubUniverse: Universe | undefined) {
    return this.http.post<any>('http://localhost:8080', {
      "userMessage": queryInputValue,
      "troubleShootingMainCategory": universe1.id,
      "troubleShootingSubCategory" : currentSubUniverse?.id || "SOC",
      "language": "english"
    });

  }
}
