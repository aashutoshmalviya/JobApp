package com.illusion.jobapprest.Controller;

import com.illusion.jobapprest.Model.Jobs;
import com.illusion.jobapprest.Service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class JobsRestControler {
    @Autowired
    private JobService jobService;

    @GetMapping(path = "jobs",produces = {"application/json"})
    public List<Jobs> getAllJobs(){
        return jobService.viewJobs(1,10);
    }
    @GetMapping("job/{jobId}")
    public Jobs getAllJobs(@PathVariable("jobId") int jobId){
        return jobService.viewJob(jobId);
    }
    @PostMapping(path = "job",consumes = {"application/json"})
    public int addJob(@RequestBody Jobs jobs){
        return jobService.addJob(jobs);
    }

    @PutMapping("job")
    public  Jobs updateJobFull(@RequestBody Jobs job){
        jobService.updateJobFull(job);
        return jobService.viewJob(job.getJobId());
    }

    @DeleteMapping("job/{jobId}")
    public int deleteJob(@PathVariable int jobId){
        return jobService.deleteJob(jobId);
    }
    @GetMapping("keyword/{keyword}")
    public List<Jobs> searchJobsByKeyword(@PathVariable("keyword") String keyword){
        return jobService.findJobByKeyword(keyword);
    }
}

