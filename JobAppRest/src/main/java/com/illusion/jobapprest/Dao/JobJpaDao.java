package com.illusion.jobapprest.Dao;

import com.illusion.jobapprest.Model.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobJpaDao extends JpaRepository<Jobs, Integer> {
    List<Jobs> findAllByOrderByJobIdDesc();

    List<Jobs> findByJobNameContainingOrderByJobIdDesc(String keyword);
}
