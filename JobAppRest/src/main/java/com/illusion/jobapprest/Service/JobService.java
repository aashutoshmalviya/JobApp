package com.illusion.jobapprest.Service;

import com.illusion.jobapprest.Dao.JobDao;
import com.illusion.jobapprest.Dao.JobJpaDao;
import com.illusion.jobapprest.Model.Jobs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {
    private JobDao jobDao;

    @Autowired
    public void setJobDao(JobDao jobDao) {
        this.jobDao = jobDao;
    }

    private JobJpaDao  jobJpaDao;
    @Autowired
    public void setJobJpaDao(JobJpaDao jobJpaDao) {
        this.jobJpaDao = jobJpaDao;
    }


    public int addJob(Jobs job) {
        jobJpaDao.save(job);
        return 1;
    }

    public List<Jobs> viewJobs(int page, int size) {
        return jobJpaDao.findAllByOrderByJobIdDesc();
    }

    public int getJobCount() {
//        return jobDao.getJobCount();
        return  jobJpaDao.findAll().size();
    }

    public Jobs viewJob(int jobId) {
//        return jobDao.fetchJob(jobId);
        return jobJpaDao.findById(jobId).orElse(new Jobs());
    }

    public int updateJobFull(Jobs job) {
         jobJpaDao.save(job);
         return 1;
    }

    public int deleteJob(int jobId) {
//        return jobDao.deletejob(jobId);
        jobJpaDao.deleteById(jobId);
        return 1;
    }
    public List<Jobs> findJobByKeyword(String keyword){
        return jobJpaDao.findByJobNameContainingOrderByJobIdDesc(keyword);
    }
}
