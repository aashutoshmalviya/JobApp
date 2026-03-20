import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { toSignal } from '@angular/core/rxjs-interop';
import { Jobs } from '../Model/Jobs';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class JobService {
  private apiUrl = 'http://localhost:8080/';
  private http = inject(HttpClient);
  public jobs = toSignal(this.http.get<Jobs[]>(`${this.apiUrl}jobs`), {
    initialValue: [],
  });
  addJobs(
    jobName: string,
    jobDesc: string,
    reqExp: number,
    techStack: string[],
  ): Observable<Jobs> {
    const job: Jobs = {
      
      jobName: jobName,
      jobDesc: jobDesc,
      reqExp: reqExp,
      techStack: techStack,
    };
    console.log(job);
    return this.http.post<Jobs>(`${this.apiUrl}job`, job);
  }
}
