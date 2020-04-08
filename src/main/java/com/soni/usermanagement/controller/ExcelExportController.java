package com.soni.usermanagement.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.soni.usermanagement.exception.error.EmailNotValidException;
import com.soni.usermanagement.methods.EmailMessage;
import com.soni.usermanagement.methods.EmailValidation;
import com.soni.usermanagement.repository.ContactManagementRepo;
import com.soni.usermanagement.services.EmailService;
import com.soni.usermanagement.services.ExcelExportService;

@RestController @CrossOrigin(origins = "http://localhost:4200")
public class ExcelExportController {
	
    @Autowired
    private ContactManagementRepo repo;
    
    @Autowired
    private EmailService emailService;
    
    /*

    @GetMapping("/exportExcel")
    public void downloadCsv(HttpServletResponse response) throws IOException {
        
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=ContactManagement.xlsx");
        ByteArrayInputStream stream = ExcelExportService.contactListToExcelFile(repo.findAll());
        IOUtils.copy(stream, response.getOutputStream());
    }
    */
    
    @PostMapping("/emailExcel")
    public void emailCSV(@RequestBody String sendList, HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=ContactManagement.xlsx");
        String filePath = ExcelExportService.contactListToExcelFile(repo.findAll());

        // validate all emails
        List<String> emails = Arrays.asList(sendList.split(",[ ]*"));
        /*
        for(String email: emails) 
        if (!EmailValidation.emailValidator(email)) throw new EmailNotValidException(email);
        */
        // send emails to all recipients
        for(String email: emails) {
            emailService.sendMailWithAttachment(
                email, 
                EmailMessage.makeSubjectFor("exportExcel", email), 
                "Sending you the excel file as requested. \n\n" +
                "Hope you have a great day ahead. \nBest regards. \nTeam SUP",
                filePath);
        }
    }
    
    

}
