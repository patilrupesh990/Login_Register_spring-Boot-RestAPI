package com.bridgelabz.fundooapi.dao;


import com.bridgelabz.fundooapi.model.User;

public interface UserDao 
{
	/**
	 * @param user
	 * @return registering user into DB.
	 */
	public Integer registerUser(User user);

	/**
	 * @param email
	 * @param password
	 * @return logs in user by authenticating user. if authentication success,
	 *         returns true else false
	 */
	public boolean loginUser(String email, String password);

	/**
	 * @param id
	 *            activates a registered user by setting isValid to true
	 */
	public void activateUser(long id);

	/**
	 * @param email
	 * @param user
	 * @return
	 * retrieves user object by email.
	 * returns null if not found
	 */
	public User getUserByEmail(String email);

	/**
	 * @param user
	 * @return
	 * checks if user exists already in DB
	 */
	public boolean userExists(User user);

	/**
	 * @param id
	 * @param user
	 * @return
	 * retrieves user object by taking user id as input
	 * return null if not user found
	 */
	public User getUserById(Long id);

	/**
	 * @param email
	 * @param password
	 * resets password for particular email
	 */
	public void resetPassword(User user);

}
