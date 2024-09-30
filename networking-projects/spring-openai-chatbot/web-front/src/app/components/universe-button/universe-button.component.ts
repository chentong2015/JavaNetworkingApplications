import {Component, Input} from '@angular/core';
import {Universe} from "../../model/universe";
import {StateService} from "../../services/state.service";
import {SessionService} from "../../services/session.service";

@Component({
  selector: 'app-universe-button',
  standalone: true,
  imports: [],
  templateUrl: './universe-button.component.html',
  styleUrl: './universe-button.component.scss'
})
export class UniverseButtonComponent {
  @Input('universe') universe!: Universe;

  constructor(private stateService: StateService,
              private sessionService: SessionService) {
  }

  setUniverse(universe: Universe) {
    this.stateService.currentUniverse = universe;
    this.sessionService.startNewSession();
  }
}
