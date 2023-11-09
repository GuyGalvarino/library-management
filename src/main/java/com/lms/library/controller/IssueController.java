package com.lms.library.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.lms.library.services.IssueService;

@RestController
public class IssueController {
	@Autowired
	private IssueService issueService;

	@GetMapping("/issues/{userId}")
	public ResponseEntity<?> getIssues(@PathVariable Integer userId, @RequestHeader String authorization) {
		// TODO token-based authentication
		if (!userId.equals(Integer.parseInt(authorization))) {
			return ResponseEntity.status(403).build();
		}
		return ResponseEntity.of(Optional.of(issueService.getIssues(userId)));
	}

	@PostMapping(path = "/issues/{userId}", consumes = "application/json")
	public ResponseEntity<?> addIssue(@PathVariable Integer userId, @RequestBody Integer bookId,
			@RequestHeader String authorization) {
		// TODO token-based authentication
		if (!userId.equals(Integer.parseInt(authorization))) {
			return ResponseEntity.status(403).build();
		}
		return ResponseEntity.of(Optional.of(issueService.addIssue(bookId, userId)));
	}

	@DeleteMapping(path = "/issues/{userId}", consumes = "application/json")
	public ResponseEntity<?> removeIssue(@PathVariable Integer userId, @RequestBody Integer bookId,
			@RequestHeader String authorization) {
		// TODO token-based authentication
		if (!userId.equals(Integer.parseInt(authorization))) {
			return ResponseEntity.status(403).build();
		}
		return ResponseEntity.of(Optional.of(issueService.removeIssue(bookId, userId)));
	}
}
