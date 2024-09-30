import { Component } from '@angular/core';
import {SessionService} from "../../services/session.service";
import {CommonModule} from "@angular/common";
import {Session} from "../../model/session";

@Component({
  selector: 'app-session-history',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './session-history.component.html',
  styleUrl: './session-history.component.scss'
})
export class SessionHistoryComponent {

  constructor(private sessionService: SessionService) {
  }

  get sessions() {
    return this.sessionService.sessions
  }

  loadSession(session: Session) {
    this.sessionService.currentSession = session;
  }

  startNewSession() {
    this.sessionService.startNewSession();
  }
}
