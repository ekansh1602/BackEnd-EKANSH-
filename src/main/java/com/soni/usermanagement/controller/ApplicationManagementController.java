package com.soni.usermanagement.controller;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import com.soni.usermanagement.exception.error.EmailNotValidException;
import com.soni.usermanagement.exception.error.EntryAlreadyExists;
import com.soni.usermanagement.exception.error.EntryNotFound;
import com.soni.usermanagement.exception.success.AppDeleted;
import com.soni.usermanagement.exception.success.AppUpdated;
import com.soni.usermanagement.exception.success.NewAppAdded;
import com.soni.usermanagement.methods.EmailValidation;
import com.soni.usermanagement.model.ApplicationManagement;
import com.soni.usermanagement.repository.ApplicationManagementRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class ApplicationManagementController {

    @Autowired
    private ApplicationManagementRepo repo;

    @GetMapping("/application")
    public List<ApplicationManagement> getAllApplications() {
        return repo.findAll();
    }

    @GetMapping("/application/{applicationCode}")
    public ApplicationManagement getApplication(@PathVariable("applicationCode") String applicationCode) {
        ApplicationManagement application = repo.findByApplicationCode(applicationCode).orElse(null);
        if(application == null) throw new EntryNotFound(applicationCode);
        else return application;
    }

    @PostMapping("/application")
    public void addApplication(@RequestBody ApplicationManagement newApplication) {

        // checking for invalid e-mails
        List<String> contacts = Arrays.asList(newApplication.getContacts().split(";[ ]*"));
        if(!newApplication.getContacts().equals(""))
        for(String contact: contacts)
        if(!EmailValidation.emailValidator(contact)) throw new EmailNotValidException(contact);

        // checking if entry already exists
        ApplicationManagement application = repo.findByApplicationCode(newApplication.getApplicationCode()).orElse(null);
        if(application != null) throw new EntryAlreadyExists(application.getApplicationCode(), application.getApplicationName());

        repo.save(newApplication);
        //throw new NewAppAdded(newApplication.getApplicationCode(), newApplication.getApplicationName());
    }

    @DeleteMapping("/application/{applicationCode}")
    public void deleteApplication(@PathVariable("applicationCode") String applicationCode) {
        ApplicationManagement application = repo.findByApplicationCode(applicationCode).orElse(null);
        if(application == null) throw new EntryNotFound(applicationCode);
        repo.deleteById(application.getId());
        //throw new AppDeleted(application.getApplicationCode(), application.getApplicationName());
    }

    @PutMapping("/application/{applicationCode}")
    public void updateApplication(@Valid @RequestBody ApplicationManagement newApplication, @PathVariable("applicationCode") String applicationCode) {
        ApplicationManagement application = repo.findByApplicationCode(applicationCode).orElse(null);

        if(application == null)
            throw new EntryNotFound(applicationCode);

        // checking for duplicate entry
        ApplicationManagement obj = repo.findByApplicationCode(newApplication.getApplicationCode()).orElse(null);
        if(obj != null && !obj.getApplicationCode().equals(application.getApplicationCode()))
        throw new EntryAlreadyExists(obj.getApplicationCode(), obj.getApplicationName());

        // checking for invalid contacts
        List<String> contacts = Arrays.asList(newApplication.getContacts().split(";[ ]*"));
        if(!newApplication.getContacts().equals(""))
        for(String contact: contacts)
        if (!EmailValidation.emailValidator(contact)) throw new EmailNotValidException(contact);
        
        application.setApplicationCode(newApplication.getApplicationCode());
        application.setApplicationName(newApplication.getApplicationName());
        application.setContacts(newApplication.getContacts());

        repo.save(application);
        //throw new AppUpdated(application.getApplicationCode(), application.getApplicationName());
    }
}