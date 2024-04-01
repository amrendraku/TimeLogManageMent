package com.example.repository;

import com.example.entity.ResourceLeave;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceLeaveRepository extends JpaRepository<ResourceLeave, Long> {



}