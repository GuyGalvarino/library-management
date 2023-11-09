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

import com.lms.library.entities.Book;
import com.lms.library.services.AuthorizationService;
import com.lms.library.services.IssueService;

@RestController
public class IssueController {
	@Autowired
	private IssueService issueService;
	@Autowired
	private AuthorizationService authorizationService;
	
	private static class IssueRequest {
		private Integer bookId;

		public Integer getBookId() {
			return this.bookId;
		}
	}

	@GetMapping("/issues/{userId}")
	public ResponseEntity<?> getIssues(@PathVariable Integer userId, @RequestHeader String authorization) {
		if(!authorizationService.verifyToken(userId, authorization)) {			
			return ResponseEntity.status(403).build();
		}
		return ResponseEntity.of(Optional.of(issueService.getIssues(userId)));
	}

	@PostMapping(path = "/issues/{userId}", consumes = "application/json")
	public ResponseEntity<?> addIssue(@PathVariable Integer userId, @RequestBody IssueRequest issueRequest,
			@RequestHeader String authorization) {
		if(!authorizationService.verifyToken(userId, authorization)) {			
			return ResponseEntity.status(403).build();
		}
		Integer bookId = issueRequest.getBookId();
		return ResponseEntity.of(Optional.of(issueService.addIssue(bookId, userId)));
	}

	@DeleteMapping(path = "/issues/{userId}", consumes = "application/json")
	public ResponseEntity<?> removeIssue(@PathVariable Integer userId, @RequestBody IssueRequest deleteRequest,
			@RequestHeader String authorization) {
		if(!authorizationService.verifyToken(userId, authorization)) {			
			return ResponseEntity.status(403).build();
		}
		Integer bookId = deleteRequest.getBookId();
		Book removedBook = issueService.removeIssue(bookId, userId);
		if(removedBook == null) {
			return ResponseEntity.status(404).build();
		}
		return ResponseEntity.of(Optional.of(removedBook));
	}
}
