package com.bridgelabz.fundooapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundooapi.dao.ICollaboratorDao;
import com.bridgelabz.fundooapi.dao.IUserDao;
import com.bridgelabz.fundooapi.model.User;
import com.bridgelabz.fundooapi.response.CollaboratorResponse;
import com.bridgelabz.fundooapi.util.JwtTokenUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CollaboratorService implements ICollaboratorService {

	@Autowired
	ICollaboratorDao collaboratorDao;
	@Autowired
	IUserDao userdao;

	@Autowired
	JwtTokenUtil tokenUtil;

	public ResponseEntity<CollaboratorResponse> addCollaborator(String token, Long noteId, String email) {
		log.info("collaboratorservice" + email);
		Long userId = getUserIdFromToken(token);
		if (userdao.getUserById(userId) != null) {
			User user = userdao.getUserByEmail(email);
			if (user == null) {
				return ResponseEntity.status(HttpStatus.ACCEPTED)
						.body(new CollaboratorResponse(201, "Email not Registered"));
			}
			if (collaboratorDao.addCollaborator(user.getId(), noteId)) {
				log.info("collaboratorservice 1st");
				return ResponseEntity.status(HttpStatus.ACCEPTED)
						.body(new CollaboratorResponse(201, "Collaborator Added"));
			} else {
				log.info("collaboratorservice 2st");
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body(new CollaboratorResponse(409, "Collaborator Can't Added Internal Server Added"));
			}
		} else {
			log.info("collaboratorservice 3rd");
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new CollaboratorResponse(201, "User Does Not Exist"));
		}
	}

	public long getUserIdFromToken(String token) {
		return tokenUtil.parseToken(token);
	}

	@Override
	public ResponseEntity<CollaboratorResponse> getCollaboratorNotes(String token) {
		Long userId = getUserIdFromToken(token);
		if (userdao.getUserById(userId) != null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new CollaboratorResponse(201, "List of Notes:", collaboratorDao.getNotes(userId)));
		} else {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new CollaboratorResponse(201, "User Note Exist"));
		}
	}

	public ResponseEntity<CollaboratorResponse> getCollaboratorsList(String token, Long noteId) {
		Long userId = getUserIdFromToken(token);
		if (userdao.getUserById(userId) != null) {
			if (collaboratorDao.getCollaboratorsList(noteId) == null) {
				return ResponseEntity.status(HttpStatus.ACCEPTED)
						.body(new CollaboratorResponse(201, "No Collaborators:"));
			} else {
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(new CollaboratorResponse(201,
						"List of collaborators", collaboratorDao.getCollaboratorsList(noteId)));
			}
		} else {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new CollaboratorResponse(201, "User doesn't Exist"));
		}
	}
	@Override
	public ResponseEntity<CollaboratorResponse> removeCollaborator(String token, int userId,Long noteId) {
		Long userid = getUserIdFromToken(token);
		if (userdao.getUserById(userid) != null) {
			if (collaboratorDao.removeCollaborator(userId,noteId) > 0) {
				return ResponseEntity.status(HttpStatus.ACCEPTED)
						.body(new CollaboratorResponse(201, "Collaborator Removed"));
			}else {
				return ResponseEntity.status(HttpStatus.ACCEPTED)
						.body(new CollaboratorResponse(201, "Error while Remove Collaborator"));
			}
			
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED)
				.body(new CollaboratorResponse(201, "User token expire Login again"));
	}
}
