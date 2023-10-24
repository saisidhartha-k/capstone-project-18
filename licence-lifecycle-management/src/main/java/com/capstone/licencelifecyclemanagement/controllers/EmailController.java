// package com.capstone.licencelifecyclemanagement.controllers;

// import com.capstone.licencelifecyclemanagement.dto.EmailRequest;
// import com.capstone.licencelifecyclemanagement.services.JavaSmtpGmailSenderService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.context.event.ApplicationReadyEvent;
// import org.springframework.context.event.EventListener;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// @RestController
// @RequestMapping("/email")
// public class EmailController {

//     @Autowired
//     private JavaSmtpGmailSenderService emailSenderService;

//     @PostMapping("/send")
//     public void sendMail() {
//         emailSenderService.sendEmail("workertemp7@gmail.com", "This is subject", "This is email body");
//     }
// }
