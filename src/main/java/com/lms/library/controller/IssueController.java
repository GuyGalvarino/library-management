package com.lms.library.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.lms.library.services.IssueService;

@RestController
public class IssueController {
	@Autowired
	private IssueService issueService;

	@GetMapping("/issues/{userId}")
	public ResponseEntity<?> getIssues(@RequestBody Integer userId, @RequestHeader String authorization) {
		// TODO token-based authentication
		if (!userId.equals(Integer.parseInt(authorization))) {
			return ResponseEntity.status(403).build();
		}
		return ResponseEntity.of(Optional.of(issueService.getIssues(userId)));
	}
	

}
