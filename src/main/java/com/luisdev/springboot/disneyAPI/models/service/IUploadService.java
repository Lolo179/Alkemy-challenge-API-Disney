package com.luisdev.springboot.disneyAPI.models.service;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IUploadService {

	public Resource load(String filename) throws MalformedURLException;
	
	public boolean delete(String filename);
	
	public void init() throws IOException;
	
	public String copy(MultipartFile file) throws IOException;
	
	public void deleteAll();
	
}
