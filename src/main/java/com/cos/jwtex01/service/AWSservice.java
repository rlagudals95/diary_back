package com.cos.jwtex01.service;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cos.jwtex01.config.DateUtil;
import com.cos.jwtex01.config.Constants;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;


@Service("awsService")
public class AWSservice {
	
	@SuppressWarnings("unused")
	private static final String S3_URL = Constants.S3_URL;
    private static final String BUCKET_NAME = "s3diary";
    private static final String ACCESS_KEY = Constants.ACCESS_KEY;
    private static final String SECRET_KEY = Constants.SECRET_KEY;
    private AmazonS3 amazonS3;
    private static final String prefix = "diary";
    private static final String s3Url = "https://s3diary.s3.ap-northeast-2.amazonaws.com/diary_file/";
    
    @SuppressWarnings("deprecation")
	public void S3Service() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
        amazonS3 = new AmazonS3Client (awsCredentials);
        System.out.println("아마존 크레딧 :" +  amazonS3);
    }
 
    public String uploadFile(MultipartFile[] files) throws IOException {
    	S3Service();
    	String returnUrl = "";
    	for(MultipartFile img : files) {
 
			if(img != null && !img.isEmpty()) {
				if (amazonS3 != null) {
//					ObjectMetadata omd = new ObjectMetadata();
//			        omd.setContentType(img.getContentType());
//			        omd.setContentLength(img.getSize());
//			        omd.setHeader("filename", img.getOriginalFilename());		
			        		        
			        //File uploadTempFile = new File("s3diary" + "/" + img.getOriginalFilename());
			        System.out.println("파일이름 : " + img.getOriginalFilename());		  
					System.out.println("인풋스트림 : " + img.getInputStream());
					
					Date today = new Date();
					Locale currentLocale = new Locale("KOREAN", "KOREA");
					String pattern = "yyyyMMddHHmmss"; //hhmmss로 시간,분,초만 뽑기도 가능
					SimpleDateFormat formatter = new SimpleDateFormat(pattern,
							currentLocale);
					String nowTime = formatter.format(today);
					System.out.println("날짜 형식 확인" + formatter.format(today));
					
					String fName = img.getOriginalFilename();				
					String ext = fName.substring(fName.lastIndexOf(".")+1, fName.length());
					String uploadKey = /* "diary_file/" + */nowTime + "." + ext;
					
					System.out.println("이걸 : "+uploadKey);
					
		            try {
		                PutObjectRequest putObjectRequest =
		                        new PutObjectRequest(BUCKET_NAME , uploadKey , img.getInputStream(), null);
		                putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead); // file permission
		                amazonS3.putObject(putObjectRequest); // upload file
		                
		                String fileUrl = s3Url + uploadKey ;
		                returnUrl = fileUrl;
		            } catch (AmazonServiceException ase) {
		                ase.printStackTrace();
		            } finally {
		                amazonS3 = null;
		            }
		        }
			}
			
		}
    	return returnUrl;
    }

}
 
