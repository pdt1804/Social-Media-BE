package com.example.demo.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.demo.entities.User;
import com.example.demo.repositories.BlogRepository;
import com.example.demo.services.BlogService;
import com.example.demo.services.UserService;

@Configuration
public class CloudinaryService {

	@Autowired
	private UserService userService;
	
	@Autowired 
	private BlogRepository blogRepository;

	@Bean
	public Cloudinary getCloudinary()
	{
		var config = new HashMap<>();
        config.put("cloud_name", "dguickmkg");
        config.put("api_key", "337925671823776");
        config.put("api_secret", "yOHPBilWervow9a2HVUVkeC6DKg");
        config.put("secure", true);
        return new Cloudinary(config);
	}
	
	public String uploadImageAvatar(MultipartFile file, String userName)
	{
		try{
	           Map<String, String> data = this.getCloudinary().uploader().upload(file.getBytes(), Map.of());
	           User user = userService.GetUserByUsername(userName);
				if (user == null)
				{ 
					return "Không tồn tại tài khoản";
				}
				else
				{
					if (user.getInformation().getPublicID() != null)
					{
						this.getCloudinary().uploader().destroy(user.getInformation().getPublicID(), ObjectUtils.asMap("type", "upload", "resource_type", "image"));
					}
					
					user.getInformation().setPublicID(data.get("public_id"));
					user.getInformation().setImage(data.get("url"));
					userService.changeAvatar(user);
					System.out.println("thanhf coong");
					return "Thay đổi ảnh thành công";
				}
	        }catch (IOException io){
	            throw new RuntimeException("Image upload fail");
	            //return "That bai";
	        }
	}

	public void uploadFilesBlog(MultipartFile file, long blogID)
	{
		try
		{
			var blog = blogRepository.getById(blogID);
			Map<String, String> data = this.getCloudinary().uploader().upload(file.getBytes(), Map.of());
			blog.getImage().add(data.get("url") + "-" + data.get("public_id"));
			blogRepository.save(blog);
		}        
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
