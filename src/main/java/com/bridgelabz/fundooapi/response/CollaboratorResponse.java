package com.bridgelabz.fundooapi.response;

import java.util.List;

import org.springframework.stereotype.Component;

import com.bridgelabz.fundooapi.model.Collaborator;
import com.bridgelabz.fundooapi.model.User;

import lombok.Data;




@Component
@Data
public class CollaboratorResponse {

	int status;
	String response;
	Collaborator object;
	Object object2;
	List<User> users;
	public CollaboratorResponse(int status, String response, Collaborator object) {
		this.status = status;
		this.response = response;
		this.object = object;
	}
	public CollaboratorResponse(int status, String response) {
		this.status = status;
		this.response = response;
	}
public CollaboratorResponse(int status, String response,Object object) {
		
		this.status = status;
		this.response = response;
		this.object2=object;
	}

public CollaboratorResponse(int status, String response,List<User> users) {
	
	this.status = status;
	this.response = response;
	this.users=users;
}
	public CollaboratorResponse() {
		
	}
	
	
}
