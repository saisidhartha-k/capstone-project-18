// package com.capstone.licencelifecyclemanagement.services;

// import org.springframework.beans.factory.annotation.Autowired;

// import org.springframework.http.ResponseEntity;
// import org.springframework.mail.javamail.JavaMailSender;
// import org.springframework.mail.javamail.MimeMessageHelper;

// import org.springframework.stereotype.Service;

// import com.capstone.licencelifecyclemanagement.dto.ResponseDTO;

// import jakarta.mail.MessagingException;

// import jakarta.mail.internet.MimeMessage;

 

// @Service

// public class GmailService {

 

//     @Autowired

//     private JavaMailSender sender;

 

//     public ResponseEntity<ResponseDTO> sendMail() {

//         MimeMessage message = sender.createMimeMessage();

//         MimeMessageHelper helper = new MimeMessageHelper(message);

//         try {

//             helper.setTo("workertemp7@gmail.com");

//             helper.setText("Greetings :)! Test Run for Capstone NTM project!");

//             helper.setSubject("Mail From Spring Boot");

 

//         } catch (MessagingException e) {

//             ResponseDTO responseDTO = new ResponseDTO();

//             e.printStackTrace();

//             responseDTO.setResponseBody("Error while sending mail ..");

//             return ResponseEntity.ok(responseDTO);

 

//         }

//         sender.send(message);

//         ResponseDTO responseDTO = new ResponseDTO();

//         responseDTO.setResponseBody("Mail Sent Success!");

//         return ResponseEntity.ok(responseDTO);

//     }

// }