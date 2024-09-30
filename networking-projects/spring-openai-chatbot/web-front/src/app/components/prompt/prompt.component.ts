import {AfterViewInit, Component, ElementRef, Input, ViewChild} from '@angular/core';
import {SubUniverseSelectorComponent} from "../sub-universe-selector/sub-universe-selector.component";
import {Universe} from "../../model/universe";
import {SessionService} from "../../services/session.service";
import {FormsModule} from "@angular/forms";
import {StateService} from "../../services/state.service";
import {QueryService} from "../../services/query.service";
import {CommonModule} from "@angular/common";

@Component({
  selector: 'app-prompt',
  standalone: true,
  imports: [
    SubUniverseSelectorComponent,
    FormsModule,
    CommonModule
  ],
  templateUrl: './prompt.component.html',
  styleUrl: './prompt.component.scss'
})
export class PromptComponent implements AfterViewInit{
  @Input() universe!: Universe | undefined;
  @ViewChild("query") queryInput: ElementRef = new ElementRef(null);
  queryInputValue: string = '';
  get loading(): boolean {
    return this.stateService.loading;
  }

  constructor(private sessionService: SessionService,
              private stateService: StateService,
              private queryService: QueryService) {
  }

  ngAfterViewInit(): void {
        this.queryInput.nativeElement.focus();
    }

  async sendQuery() {
    if (!!this.queryInputValue) {
      this.stateService.loading = true;
      this.sessionService.addQuery(this.queryInputValue);
      let response = this.queryService.getResponseFor(this.queryInputValue, this.stateService.currentUniverse!, this.stateService.currentSubUniverse)
        .subscribe(
          body => {
            console.log(body);
            this.sessionService.addResponse(this.formatResponse(body.results[0].output.content));
            this.stateService.loading = false;
          }
        );
      this.queryInputValue = '';

    }
  }

  private formatResponse(response: string) {
    let newResponse =  response.replace(/\*\*(.*?)\*\*/g, "<span class='bold'>$1</span>");
    newResponse = newResponse.replace(/\n/g, '<br/>');
    newResponse = newResponse.replace(/<image>(.*?)<\/image>/g, "<img src=\"$1\"/>");
    return newResponse;
  }
}
