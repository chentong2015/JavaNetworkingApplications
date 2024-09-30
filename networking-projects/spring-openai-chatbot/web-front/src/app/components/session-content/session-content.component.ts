import {Component} from '@angular/core';
import {SessionService} from "../../services/session.service";
import {Paragraph} from "../../model/session";
import {CommonModule} from "@angular/common";

@Component({
  selector: 'app-session-content',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './session-content.component.html',
  styleUrl: './session-content.component.scss'
})
export class SessionContentComponent {
  constructor(private sessionService: SessionService) {
  }

  get paragraphs(): Paragraph[] {
    return this.sessionService.currentSession?.paragraphs || [];
  }

  get sessionUniverse(): string {
    return this.sessionService.currentSession!.universe.label
  }

  get sessionDate(): Date {
    return this.sessionService.currentSession!.startDate
  }

}
