package com.jdc.balance.api.member.service;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PaymentSlipStorageService {

	public String save(Path slipDirectory, String code, MultipartFile slip) {
		
		try {
			var fileName = getFileName(code, slip);
			
			if(!Files.exists(slipDirectory, LinkOption.NOFOLLOW_LINKS)) {
				Files.createDirectory(slipDirectory);
			}
			
			var imagePath = slipDirectory.resolve(Path.of(fileName));
			Files.copy(slip.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
			
			return fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	private String getFileName(String code, MultipartFile slip) {
		
		var fileName = slip.getOriginalFilename();
		var array = fileName.split("\\.");
		var extension = array[array.length - 1];
		
		return "%s.%s".formatted(code, extension);
	}

}
