package com.example.repository;

import com.example.entity.TimesheetDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimesheetDetailRepository extends JpaRepository<TimesheetDetail, Long> {
    // You can define custom query methods here if needed
}
