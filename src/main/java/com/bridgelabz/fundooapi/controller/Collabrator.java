package com.bridgelabz.fundooapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundooapi.response.CollaboratorResponse;
import com.bridgelabz.fundooapi.services.ICollaboratorService;

import lombok.extern.slf4j.Slf4j;

@EnableEurekaClient
@RestController
@RequestMapping("/collaborators")
@Slf4j
@CrossOrigin("*")
public class Collabrator {
	@Autowired
	ICollaboratorService service;
	
	@PostMapping("/add")
	public ResponseEntity<CollaboratorResponse> addCollaborator(@RequestHeader String token,@RequestParam("noteId")Long noteId,@RequestParam("email") String email) {
		return service.addCollaborator(token, noteId,email);		
	} 
	
	@GetMapping("/notes")
	public ResponseEntity<CollaboratorResponse> getCollaboratorNotes(@RequestHeader String token) {
		return service.getCollaboratorNotes(token);		
	} 
	@GetMapping("/users")
	public ResponseEntity<CollaboratorResponse> getCollaboratorsList(@RequestHeader String token,@RequestParam("noteId")Long noteId){
		return service.getCollaboratorsList(token, noteId);
	}
	
	@DeleteMapping("/remove")
	public ResponseEntity<CollaboratorResponse> removeCollaboratorsList(@RequestHeader String token,@RequestParam("userId")int userId,@RequestParam("noteID") Long noteId)
	{
		return service.removeCollaborator(token, userId, noteId);
	}
}
