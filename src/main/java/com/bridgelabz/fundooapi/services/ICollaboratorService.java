package com.bridgelabz.fundooapi.services;

import org.springframework.http.ResponseEntity;

import com.bridgelabz.fundooapi.response.CollaboratorResponse;

public interface ICollaboratorService {

	public ResponseEntity<CollaboratorResponse> addCollaborator(String token,Long noteId,String email);
	public ResponseEntity<CollaboratorResponse> getCollaboratorNotes(String token);
	public ResponseEntity<CollaboratorResponse> getCollaboratorsList(String token,Long noteId);
	public ResponseEntity<CollaboratorResponse> removeCollaborator(String token, int userId,Long noteId);

}
