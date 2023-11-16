package com.lms.library.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.library.entities.Admin;

public interface AdminDao extends JpaRepository<Admin, String> {}
