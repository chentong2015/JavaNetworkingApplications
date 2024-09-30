import {Injectable} from '@angular/core';
import {Session} from "../model/session";
import {StateService} from "./state.service";

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  _sessions: Session[] = [];
  _currentSession: Session | undefined;

  constructor(private stateService: StateService) {
  }

  startNewSession() {
    this._sessions = this._sessions.filter(s => s.paragraphs.length !== 0);
    this._currentSession = {
      startDate: new Date(),
      universe: this.stateService.currentUniverse!,
      paragraphs: []
    }
    this._sessions.push(this._currentSession);
  }

  get sessions(): Session[] {
    if (this._sessions.length === 0) {
      this.startNewSession();
    }
    return this._sessions;
  }

  get currentSession(): Session | undefined {
    return this._currentSession;
  }

  set currentSession(session: Session) {
    this.stateService.currentUniverse = session.universe;
    this._currentSession = session;
  }

  addQuery(queryInput: string) {
    this.currentSession!.paragraphs.push({
      author: 'USER',
      text: queryInput
    });
  }

  addResponse(response: string) {
    this.currentSession!.paragraphs.push({
      author: 'SPT',
      text: response
    });
  }
}
