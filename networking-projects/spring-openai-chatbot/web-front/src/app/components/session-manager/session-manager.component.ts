import {Component, Input} from '@angular/core';
import {Universe} from "../../model/universe";
import {PromptComponent} from "../prompt/prompt.component";
import {SessionContentComponent} from "../session-content/session-content.component";
import {SessionHistoryComponent} from "../session-history/session-history.component";
import {SessionService} from "../../services/session.service";

@Component({
  selector: 'app-session-manager',
  standalone: true,
  imports: [
    PromptComponent,
    SessionContentComponent,
    SessionHistoryComponent
  ],
  templateUrl: './session-manager.component.html',
  styleUrl: './session-manager.component.scss'
})
export class SessionManagerComponent {
  @Input() universe!: Universe | undefined;

  constructor() {
  }

}
