import { Component, computed, inject, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { JobService } from '../../Service/job-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-job',
  imports: [ReactiveFormsModule],
  templateUrl: './add-job.html',
  styleUrl: './add-job.scss',
})
export class AddJob {
  techStackList = signal([
    'Java',
    'Spring Boot',
    'React',
    'Python',
    'AWS',
    'SQL',
    'Docker',
    'Kubernetes',
    // Backend & Languages
    'Node.js',
    'Express',
    'NestJS',
    'Go',
    'Rust',
    'C#',
    '.NET Core',
    'Ruby',
    'Rails',
    'PHP',
    'Laravel',
    'Django',
    'Flask',
    'FastAPI',
    'Koa',
    'Elixir',
    'Phoenix',
    'C++',
    'Scala',
    'Kotlin',
    // Frontend & Mobile
    'Vue.js',
    'Angular',
    'Svelte',
    'Next.js',
    'Nuxt.js',
    'TypeScript',
    'Redux',
    'Tailwind CSS',
    'Bootstrap',
    'jQuery',
    'React Native',
    'Flutter',
    'Swift',
    'Android SDK',
    'Ionic',
    'Dart',
    // Databases & Caching
    'PostgreSQL',
    'MySQL',
    'MongoDB',
    'Redis',
    'Cassandra',
    'Elasticsearch',
    'Firebase',
    'Oracle DB',
    'MariaDB',
    'SQLite',
    'DynamoDB',
    'Neo4j',
    'CouchDB',
    'ClickHouse',
    // Cloud & DevOps
    'Google Cloud (GCP)',
    'Azure',
    'Terraform',
    'Ansible',
    'Jenkins',
    'CircleCI',
    'GitHub Actions',
    'GitLab CI',
    'Nginx',
    'Apache',
    'Prometheus',
    'Grafana',
    'Linux',
    'Unix',
    'Bash',
    'PowerShell',
    // Data Science & AI
    'TensorFlow',
    'PyTorch',
    'Pandas',
    'NumPy',
    'Scikit-learn',
    'Apache Spark',
    'Hadoop',
    'Tableau',
    'Power BI',
    'R',
    'SAS',
    'MATLAB',
    'Kafka',
    'RabbitMQ',
    'ActiveMQ',
    // Security & Networking
    'OAuth 2.0',
    'JWT',
    'GraphQL',
    'gRPC',
    'WebSockets',
    'SOAP',
    'OWASP',
    'Penetration Testing',
    'Wireshark',
    'OpenSSL',
    // Testing & Tools
    'JUnit',
    'Mockito',
    'Selenium',
    'Cypress',
    'Jest',
    'Postman',
    'Swagger',
    'Jira',
    'Confluence',
    'Trello',
    'Slack API',
    'Figma',
    'Adobe XD',
    'Unity',
    'Unreal Engine',
  ]);
  searchTerm = signal('');
  selectedStacks = signal<string[]>([]);
  filteredTechStack = computed(() => {
    const term = this.searchTerm().toLowerCase();
    return this.techStackList().filter((tech) =>
      tech.toLowerCase().includes(term),
    );
  });
  onSearch(event: Event) {
    const input = event.target as HTMLInputElement;
    this.searchTerm.set(input.value);
  }
  toggleSelection(tech: string) {
    const current = this.selectedStacks();

    if (current.includes(tech)) {
      this.selectedStacks.set(current.filter((t) => t !== tech));
    } else {
      this.selectedStacks.set([...current, tech]);
    }
  }

  private jobService = inject(JobService);
  private router = inject(Router);
  isSubmitting = signal(false);
  jobForm = new FormGroup({
    jobName: new FormControl('', {
      nonNullable: true,
    }),
    reqExp: new FormControl('', {
      nonNullable: true,
    }),
    jobDesc: new FormControl('', {
      nonNullable: true,
    }),
  });
  onSubmit() {
    if (this.jobForm.valid) {
      this.isSubmitting.set(true);
      const newJob = this.jobForm.getRawValue();
      
      // REAL runtime conversion from string to integer
      const expAsNumber = Number(newJob.reqExp); 

      this.jobService
        .addJobs(
          newJob.jobName,
          newJob.jobDesc,
          expAsNumber, 
          this.selectedStacks() // This will now have data!
        )
        .subscribe({
          next: (savedJob) => {
            console.log('Success! Backend returned:', savedJob);
            this.isSubmitting.set(false);
            this.jobForm.reset();
            this.selectedStacks.set([]);
            this.router.navigate(['/']); 
          },
          error: (err) => {
            console.error('Submission failed', err);
            this.isSubmitting.set(false);
          },
        });
    }
  }

}
