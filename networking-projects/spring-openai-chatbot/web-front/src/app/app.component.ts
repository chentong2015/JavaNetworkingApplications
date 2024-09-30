import {Component} from '@angular/core';
import {CommonModule} from "@angular/common";
import {RouterOutlet} from '@angular/router';

import {UniverseButtonComponent} from "./components/universe-button/universe-button.component";
import {Universe} from "./model/universe";
import {BreadcrumbComponent} from "./components/breadcrumb/breadcrumb.component";
import {StateService} from "./services/state.service";
import {SessionHistoryComponent} from "./components/session-history/session-history.component";
import {SessionContentComponent} from "./components/session-content/session-content.component";
import {PromptComponent} from "./components/prompt/prompt.component";
import {SessionManagerComponent} from "./components/session-manager/session-manager.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, UniverseButtonComponent, CommonModule, BreadcrumbComponent, SessionHistoryComponent, SessionContentComponent, PromptComponent, SessionManagerComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'spt-frontend';
  universes: Universe[] = [];

  constructor(private stateService: StateService) {
    // TODO load from file
    this.universes = [
      {id: 'FUNCTIONAL', label: 'Functional'},
      {id: 'TECHNICAL', label: 'Technical'},
      {id: 'U3', label: 'Everything'},
      {id: 'DEVELOPER', label: 'Developer'}
    ];
  }

  get universeSelected(): boolean {
    return this.stateService.currentUniverse !== undefined;
  }

  get selectedUniverse(): Universe | undefined {
    return this.stateService.currentUniverse;
  }

  home() {
    this.stateService.unsetUniverse();
  }
}
