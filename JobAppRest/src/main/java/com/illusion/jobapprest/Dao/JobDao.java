package com.illusion.jobapprest.Dao;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import com.illusion.jobapprest.Model.Jobs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Repository
public class JobDao {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int saveJob(Jobs job) {
        final String sql = "INSERT INTO job (jobName, jobDesc, reqExp, techStack) VALUES (?, ?, ?, ?)";
        String techStackString = "";
        if (job.getTechStack() != null) {
            techStackString = String.join(",", job.getTechStack());
        }

        return jdbcTemplate.update(sql,
                job.getJobName(),
                job.getJobDesc(),
                job.getReqExp(),
                techStackString
        );
    }

    public List<Jobs> fetchJobs() {
        final String sql = "SELECT * FROM job ORDER BY jobid DESC";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Jobs job = new Jobs();
            job.setJobId(rs.getInt("jobid"));
            job.setJobName(rs.getString("jobname"));
            job.setJobDesc(rs.getString("jobdesc"));
            job.setReqExp(rs.getInt("reqexp"));
            String stackString = rs.getString("techstack");
            if (stackString != null && !stackString.isEmpty()) {
                job.setTechStack(Arrays.asList(stackString.split(",")));
            }
            return job;
        });
    }
    public List<Jobs> fetchJobs(int page, int size) {

        int offset = (page - 1) * size;
        final String sql = "SELECT * FROM job ORDER BY jobid DESC LIMIT ? OFFSET ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Jobs job = new Jobs();
            job.setJobId(rs.getInt("jobid"));
            job.setJobName(rs.getString("jobname"));
            job.setJobDesc(rs.getString("jobdesc"));
            job.setReqExp(rs.getInt("reqexp"));

            String stackString = rs.getString("techstack");
            if (stackString != null && !stackString.isEmpty()) {
                job.setTechStack(Arrays.asList(stackString.split(",")));
            }
            return job;
        }, size, offset);
    }
    public int getJobCount() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM job", Integer.class);
    }

    public Jobs fetchJob(int jobId) {
        final String sql = "SELECT * FROM job WHERE jobid=?";
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(Jobs.class),jobId);

    }

    public int updateJobFull(Jobs job) {
        String sql = "UPDATE job SET jobName = ?, jobDesc = ?, reqExp = ?, techStack = ? WHERE jobId = ?";

        // techStack is a List, so we convert it to a String or Array depending on your DB
        String techStackString = String.join(",", job.getTechStack());

        return jdbcTemplate.update(sql,
                job.getJobName(),
                job.getJobDesc(),
                job.getReqExp(),
                techStackString,
                job.getJobId()
        );

    }

    public void updateJobPartial(int jobId, Map<String, Object> updates) {
        StringBuilder sql = new StringBuilder("UPDATE job SET ");
        List<Object> values = new ArrayList<>();

        // Iterate through the map to build "column = ?" strings
        updates.forEach((key, value) -> {
            sql.append(key).append(" = ?, ");
            values.add(value);
        });

        // Remove the trailing comma and space
        sql.delete(sql.length() - 2, sql.length());

        // Add the WHERE clause
        sql.append(" WHERE jobId = ?");
        values.add(jobId);

        jdbcTemplate.update(sql.toString(), values.toArray());
    }

    public int deletejob(int jobId) {
        final String sql = "DELETE FROM job WHERE jobid = ?";
        return jdbcTemplate.update(sql,jobId);
    }
}
