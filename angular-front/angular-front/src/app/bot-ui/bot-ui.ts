import {Component, inject} from '@angular/core';
import {HttpClient, HttpDownloadProgressEvent, HttpEventType} from '@angular/common/http';
import {map, Observable} from 'rxjs';
import {AsyncPipe} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {MarkdownComponent} from 'ngx-markdown';
import {LoadingService} from '../services/loading';

@Component({
  selector: 'app-bot-ui',
  imports: [
    AsyncPipe,
    FormsModule,
    MarkdownComponent
  ],
  templateUrl: './bot-ui.html',
  styleUrl: './bot-ui.css',
})
export class BotUi {
  private http = inject(HttpClient);
  query:any;
  response$!: Observable<any>
  public loadingService = inject(LoadingService);

  protected askAgent() {
    this.response$=this.http.get("http://localhost:9999/EBANK-BOT/chat?query="+this.query , {responseType:"text"});

  }
  askAgentStream() {
    this.response$ = this.http
      .get("http://localhost:9999/EBANK-BOT/chatStream?query="+this.query,
        {responseType:'text', observe : "events" , reportProgress: true})
      .pipe(map(event => {
        switch (event.type){
          case HttpEventType.Sent:
            return {'type': 'Sent'}
          case HttpEventType.DownloadProgress:
            return {type: "Respponse", content : (event as HttpDownloadProgressEvent).partialText }
          case HttpEventType.Response:
            return {type : "Response", content : event.body}
          default :
            return {type : 'Other', data : event}
        }
      }))
  }
}
