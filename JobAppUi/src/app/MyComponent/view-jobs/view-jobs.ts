import { Component, inject } from '@angular/core';
import { RouterLink } from "@angular/router";
import { JobService } from '../../Service/job-service';
import { JobItem } from "../job-item/job-item";
// import { Jobs } from '../../Model/Jobs';

@Component({
  selector: 'app-view-jobs',
  standalone: true,
  imports: [RouterLink, JobItem],
  templateUrl: './view-jobs.html',
  styleUrl: './view-jobs.scss',
})
export class ViewJobs {
  private jobService = inject(JobService);
  jobs = this.jobService.jobs;
}