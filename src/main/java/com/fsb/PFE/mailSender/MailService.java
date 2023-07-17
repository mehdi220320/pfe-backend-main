package com.fsb.PFE.mailSender;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.Random;

@RestController
public class MailService {

    @Autowired private JavaMailSender javaMailSender;



    @PutMapping("/sendEmailToCustomer")
    @ApiOperation(value = "Send an email to customer of the Library", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok: Email successfully sent"),
            @ApiResponse(code = 404, message = "Not Found: no customer found, or wrong email"),
            @ApiResponse(code = 403, message = "Forbidden: Email cannot be sent")
    })
    public ResponseEntity<Boolean> sendMailToCustomer(@RequestBody int n) {
        MailDTO loanMailDto = new MailDTO();
        loanMailDto.setEmailSubject("CODE VERIFICATION");
        loanMailDto.setEmailContent(Integer.toString(n));
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(loanMailDto.MAIL_FROM);
        mail.setTo("saoudioussama700@gmail.com");
        return getBooleanResponseEntity(loanMailDto, mail);
    }


    @PutMapping("/sendMailToUpcycling")
    @ApiOperation(value = "Send an email to customer of the Library", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok: Email successfully sent"),
            @ApiResponse(code = 404, message = "Not Found: no customer found, or wrong email"),
            @ApiResponse(code = 403, message = "Forbidden: Email cannot be sent")
    })
    public ResponseEntity<Boolean> sendMailToUpcycling(@RequestBody MessageDTO mssg) {
        MailDTO loanMailDto = new MailDTO();
        loanMailDto.setEmailSubject("FROM CONTACT PAGE");

        /*Context context = new Context();
        context.setVariable("message", mssg);
        String htmlContent = templateEngine.process("email-template", context);*/

        loanMailDto.setEmailContent(mssg.toString());
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(loanMailDto.MAIL_FROM);
        mail.setTo("upcyclingartpfe@gmail.com");
        return getBooleanResponseEntity(loanMailDto, mail);
    }

    private ResponseEntity<Boolean> getBooleanResponseEntity(MailDTO loanMailDto, SimpleMailMessage mail) {
        mail.setSentDate(new Date());
        mail.setSubject(loanMailDto.getEmailSubject());
        mail.setText(loanMailDto.getEmailContent());
        System.out.println("mail///////////////////////////// : " + mail);
        try {
            javaMailSender.send(mail);
            System.out.println("mail sent ...");

        } catch (MailException e) {
            return new ResponseEntity<Boolean>(false, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }


}
