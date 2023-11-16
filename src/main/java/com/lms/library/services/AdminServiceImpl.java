package com.lms.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.library.dao.AdminDao;
import com.lms.library.entities.Admin;

@Service
public class AdminServiceImpl implements AdminService {

  @Autowired
  private AdminDao adminDao;

  @Override
  public Admin getAdmin(String email) {
    return adminDao.findById(email).orElse(null);
  }
  
}
