package com.cos.jwtex01.service;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cos.jwtex01.config.DateUtil;

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
    private static final String BUCKET_NAME = "s3diary";
    private static final String ACCESS_KEY = "AKIAYEBJVE42S46DX6XO";
    private static final String SECRET_KEY = "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";
    private AmazonS3 amazonS3;
    private static final String prefix = "diary";
    private static final String s3Url = "https://s3diary.s3.ap-northeast-2.amazonaws.com/diary_file/";
    
    @SuppressWarnings("deprecation")
	public void S3Service() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
        amazonS3 = new AmazonS3Client (awsCredentials);
        System.out.println("아마존 크레딧 :" +  amazonS3);
    }
 
    public void uploadFile(MultipartFile[] files) throws IOException {
    	S3Service();
    	
    	for(MultipartFile img : files) {
    		
			if(img != null && !img.isEmpty()) {
				if (amazonS3 != null) {
					ObjectMetadata omd = new ObjectMetadata();
			        omd.setContentType(img.getContentType());
			        omd.setContentLength(img.getSize());
			        omd.setHeader("filename", img.getOriginalFilename());		
			        
			        //File uploadTempFile = new File("s3diary" + "/" + img.getOriginalFilename());
			        System.out.println("파일이름 : " + img.getName());
					System.out.println("인풋스트림 : " + img.getInputStream());
		            try {
		                PutObjectRequest putObjectRequest =
		                        new PutObjectRequest(BUCKET_NAME , "diary_file/" + img.getOriginalFilename(), img.getInputStream(), null);
		                putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead); // file permission
		                amazonS3.putObject(putObjectRequest); // upload file
		 
		            } catch (AmazonServiceException ase) {
		                ase.printStackTrace();
		            } finally {
		                amazonS3 = null;
		            }
		        }
			}
		}
    }
    
    public int upload(Map<String, Object> param) throws Exception{

		try {
			//param.put("branch_no", 123);
			MultipartFile[] img_arr = (MultipartFile[]) param.get("files");
			if(img_arr !=null) {
				//파일 업로드 후 w_work_facility_check_img insert
				for(MultipartFile img : img_arr) {
					if(img != null && !img.isEmpty()) {

						String fName = img.getOriginalFilename();
						String ext = fName.substring(fName.lastIndexOf(".")+1, fName.length());


						File uploadTempFile = new File("s3diary" + "/" + img.getOriginalFilename());

						System.out.println("work.upload.temp.dir : [" + "s3diary" + "/" + img.getOriginalFilename() + "]");

					    img.transferTo(uploadTempFile);

						if(uploadTempFile.exists()) {

							String upload_key = "diary_img/"
									.concat(DateUtil.getCurrentTime("yyyyMMddHHmmsss"))		
									.concat(ext);

							//s3Service.fileUpload(s3_bucket, upload_key , uploadTempFile, CannedAccessControlList.PublicRead);
			        		uploadTempFile.delete();

			        		//String file_url = s3_url.concat("/").concat(s3_bucket).concat("/").concat(upload_key);
			        	}
					}
				}
			}

		} catch (Exception e) {

		}
		return 1;
	}
    
    
    
    
//    public List<Map<String,Object>> saveImgInfo(Map<String, Object> param) throws Exception {
//
//		List<Map<String,Object>> resultList = new ArrayList<Map<String, Object>>();
//
//		MultipartFile[] files = (MultipartFile[]) param.get("files");
//		if(files == null) return resultList;
//
//		for(MultipartFile img : files) {
//
//			if(img != null && !img.isEmpty()) {
//				System.out.println("img : "+ img);
//				String img_type = (String)param.get("category_" + img.getName());	// get img_type
//
//				String file_name = img.getOriginalFilename();
//				//String ext = fName.substring(fName.lastIndexOf(".")+1, fName.length());
//				//String ext = StringUtil.substrIndex(file_name, ".");
//				String temp_dir = getUpladPath();	// image upload directory
//				String mask = String.valueOf(System.nanoTime());
//				long img_size = img.getSize();
//
//				File uploadTempFile = new File(temp_dir + mask + "." + ext);
//				img.transferTo(uploadTempFile);
//				
//				String key_no = param.get("key_no").toString();
//
//				Long create_no = 0L;
//				
//				if(param.get("create_no") != null) {
//					create_no = Long.valueOf(param.get("create_no").toString()); 
//				}else if(param.get("update_no") != null) {
//					create_no = Long.valueOf(param.get("update_no").toString()); 
//				}
//						
//
//				/* upload s3 start ****************************************/
//				String upload_key = "work_img/"
//						.concat(DateUtil.getCurrentTime("yyyyMMddHHmmssSSS"))
//						.concat("_")
//						.concat(key_no)
//						.concat("_")
//						.concat(StringUtil.randomString(3))
//						.concat(".")
//						.concat(ext);
//
//				String s3_bucket = "workerman-upload-real";
//				String s3_url = "https://s3.ap-northeast-2.amazonaws.com/";
//
//				s3Service.uploadFile(s3_bucket, upload_key , uploadTempFile, CannedAccessControlList.PublicRead);
//
//        		//uploadTempFile.delete();
//
//        		String file_url = s3_url.concat("/").concat(s3_bucket).concat("/").concat(upload_key);
//				/* upload s3 end *****************************************/
//
//				/* insert image information */
//				Map<String, Object> imgMap = new HashMap<String, Object>();
//				imgMap.put("img_type", img_type); // image
//				imgMap.put("img_name", file_name);
//				imgMap.put("img_url", file_url);
//				imgMap.put("img_size", img_size);
//				imgMap.put("img_ext", ext);
//				imgMap.put("key_no", key_no);
//				imgMap.put("create_no", create_no);
//				
//				resultList.add(imgMap);
//				//TODO
//				// file size 조절(aws 조절가능 여부 확인 필요.)
//			}
//		}
//
//		return resultList;
//	}
}
 
