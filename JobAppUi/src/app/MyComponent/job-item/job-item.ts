import { Component, input, Input } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Jobs } from '../../Model/Jobs';

@Component({
  selector: 'app-job-item',
  imports: [RouterLink],
  templateUrl: './job-item.html',
  styleUrl: './job-item.scss',
})
export class JobItem {
  job=input.required<Jobs>()
}
