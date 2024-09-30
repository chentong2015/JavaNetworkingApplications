import { Component } from '@angular/core';
import {StateService} from "../../services/state.service";
import {Universe} from "../../model/universe";
import {CommonModule} from "@angular/common";

@Component({
  selector: 'app-breadcrumb',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './breadcrumb.component.html',
  styleUrl: './breadcrumb.component.scss'
})
export class BreadcrumbComponent {

  constructor(private stateService: StateService) { }

  getUniverse(): Universe | undefined {
    return this.stateService.currentUniverse;
  }

  unload() {
    this.stateService.unsetUniverse();
  }

  get homeClass(): string {
    return !this.stateService.currentUniverse ? 'breadcrumb-item' : 'breadcrumb-item active';
  }
}
