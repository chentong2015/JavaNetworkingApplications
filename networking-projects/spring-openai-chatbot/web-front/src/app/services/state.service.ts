import { Injectable } from '@angular/core';
import {Universe} from "../model/universe";

@Injectable({
  providedIn: 'root'
})
export class StateService {

  private _currentUniverse: Universe | undefined;
  private _currentSubUniverse: Universe | undefined;
  _loading: boolean = false;

  constructor() { }

  public set currentUniverse(universe: Universe) {
    this._currentUniverse = universe;
  }

  public get currentUniverse(): Universe | undefined {
    return this._currentUniverse;
  }

  public set currentSubUniverse(universe: Universe | undefined) {
    this._currentSubUniverse = universe;
  }

  public get currentSubUniverse(): Universe | undefined {
    return this._currentSubUniverse;
  }

  get loading(): boolean {
    return this._loading;
  }
  set loading(value: boolean) {
    this._loading = value;
  }

  public unsetUniverse() {
    this._currentUniverse = undefined;
  }
}
