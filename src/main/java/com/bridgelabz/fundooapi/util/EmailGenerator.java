package com.bridgelabz.fundooapi.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailGenerator {

	@Autowired
    private JavaMailSender javaMailSender;


	public void sendEmail(String useremail,String subject,String message) {

        SimpleMailMessage mail=new SimpleMailMessage();
        mail.setTo(useremail);

        mail.setSubject(subject);
        mail.setText(message);

        javaMailSender.send(mail);
	}

}