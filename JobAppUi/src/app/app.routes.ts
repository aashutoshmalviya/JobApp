import { Routes } from '@angular/router';
import { Home } from './MyComponent/home/home';
import { AddJob } from './MyComponent/add-job/add-job';
import { ViewJobs } from './MyComponent/view-jobs/view-jobs';

export const routes: Routes = [
    {path:"",component:Home},
    {path:"addJobs",component:AddJob},
    {path:"viewJobs",component:ViewJobs},
    {path:"**",redirectTo:""}
];
