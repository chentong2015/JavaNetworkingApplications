import {Component, Input, OnInit} from '@angular/core';
import {SubUniverse, Universe} from "../../model/universe";
import {CommonModule} from "@angular/common";
import {StateService} from "../../services/state.service";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-sub-universe-selector',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './sub-universe-selector.component.html',
  styleUrl: './sub-universe-selector.component.scss'
})
export class SubUniverseSelectorComponent implements OnInit {
  @Input('universe') universe!: Universe | undefined;
  subUniverses: SubUniverse[];
  selectedSubUniverse: Universe[] | undefined = undefined;
  selectedSubUniverseId: string = '';

  constructor(private stateService: StateService) {
    this.subUniverses = [{
      universeId: 'FUNCTIONAL',
      subUniverses: [
        {id: 'SB', label: 'Screening Box'},
        {id: 'PLATFORM', label: 'Platform'},
        {id: 'WUM', label: 'Watchlist Update Manager'},
        {id: 'SM', label: 'Simulation Mamager'},
        {id: 'SOC', label: 'Screening Operation Controller'}
      ]
    }, {
      universeId: 'TECHNICAL',
      subUniverses: [
        {id: 'SB', label: 'Screening Box'},
        {id: 'PLATFORM', label: 'Platform'},
        {id: 'WUM', label: 'Watchlist Update Manager'},
        {id: 'SM', label: 'Simulation Mamager'},
        {id: 'SOC', label: 'Screening Operation Controller'}
      ]
    }, {
      universeId: 'DEVELOPER',
      subUniverses: [
        {id: 'SB', label: 'Screening Box'},
        {id: 'PLATFORM', label: 'Platform'},
        {id: 'WUM', label: 'Watchlist Update Manager'},
        {id: 'SM', label: 'Simulation Mamager'},
        {id: 'SOC', label: 'Screening Operation Controller'}
      ]
    }];
  }

  ngOnInit(): void {
    this.selectedSubUniverse = this.subUniverses.find(su => su.universeId === this.universe?.id)?.subUniverses;
  }

  selectSubUniverse() {
    if (!!this.selectedSubUniverseId && this.selectedSubUniverseId !== 'ALL') {
      this.stateService.currentSubUniverse = this.selectedSubUniverse!.find(su => su.id === this.selectedSubUniverseId);
    } else {
      this.stateService.currentSubUniverse = undefined;
    }
  }
}
