package com.sliit.ssd.oauth.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.sliit.ssd.oauth.model.FileModel;
import com.sliit.ssd.oauth.service.GoogleDriveService;

/**
 * @author Saranki 
 * Root mapping for the index page
 */

@Controller
public class MainController {
	
	@Autowired
	GoogleDriveService googleDriveService;

	@GetMapping("/")
	public String showIndex() throws IOException{
		return googleDriveService.isUserAuthenticated()?"home.html":"index.html";
	}
	
	@GetMapping("/googlesignin")
	public void goGoogleSignIn(HttpServletResponse response) throws IOException{
		googleDriveService.googleSignIn(response);
	}
	
	@GetMapping("/oauth")
	public String storeCredentialsFromGoogle(HttpServletRequest request) throws IOException{
		return googleDriveService.isStoreAuthorizationCode(request)?"home.html":"index.html";
	}
	
	@PostMapping("/upload")
	public String uploadFile(HttpServletRequest servletRequest,
            @ModelAttribute FileModel file) throws IllegalStateException, IOException{
		googleDriveService.uploadFileToDrive(file.getMultipartFile());
		return "home.html";
	}

}
