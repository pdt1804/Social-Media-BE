package com.example.demo.services;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.demo.DTO.NotifycationDTO;
import com.example.demo.entities.Notifycation;
import com.example.demo.entities.NotifycationStatus;
import com.example.demo.entities.NotifycationType;
import com.example.demo.entities.Reply;
import com.example.demo.repositories.GroupStudyingRepository;
import com.example.demo.repositories.NotifycationRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.serviceInterfaces.NotificationManagement;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotifycationService implements NotificationManagement{

	@Autowired
	private NotifycationRepository notifycationRepository;
	
	@Autowired
	private GroupStudyingRepository groupStudyingRepository;
	
	@Autowired
	private Cloudinary cloudinary;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void insertImage(int notificationID, MultipartFile image)
	{
		try {
			var notification = notifycationRepository.getById(notificationID);
			
			if (notification != null)
			{
		        Map<String, String> data = this.cloudinary.uploader().upload(image.getBytes(), Map.of());
		        notification.getImages().add(data.get("url"));
		        notification.setPublicID(data.get("public_id"));
		        notifycationRepository.save(notification);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public int createNotifycation(int groupID, String userName, Notifycation notifycation, List<MultipartFile> files)
	{
		try
		{
			var user = userRepository.getById(userName);
			var group = groupStudyingRepository.getById(groupID);
			notifycation.setGroupStudying(group);
			notifycation.setNotifycationType(NotifycationType.user);
			group.getNotifycations().add(notifycation);
			group.setLastTimeEdited(new Date());
			for (var p: group.getUsers())
			{
				notifycation.getUsers().add(p);
				notifycation.getUserSeenNotifycation().add(p.getUserName());
				p.getNotifycations().add(notifycation);
			}
			notifycation.setDateSent(new Date());
			notifycationRepository.save(notifycation);
			
			UploadImageToFirebaseForNotification(notifycation.getNotifycationID(), files);
			userRepository.save(user);
			groupStudyingRepository.save(group);
			
			return notifycation.getNotifycationID();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			return -1;
		}
	}
	
	private void UploadImageToFirebaseForNotification(int id, List<MultipartFile> files) throws java.io.IOException
	{
		Notifycation obj = notifycationRepository.getById(id);;
		
		if (obj != null)
		{
			for (var file : files)
			{
				Random rd = new Random();
				String nameOnCloud = file.getName() + "-" + "-" + rd.nextInt(1, 9999999) + "-" + UUID.randomUUID();
				Bucket bucket = StorageClient.getInstance().bucket();
				var blob = bucket.create(nameOnCloud, file.getBytes(), file.getContentType());
				
				obj.getImages().add(nameOnCloud);
			}
			
			notifycationRepository.save(obj);
		}
	}

	@Override
	public List<Notifycation> getAllNotifycationByUserName(String userName)
	{
		try
		{
			return userRepository.getById(userName).getNotifycations()
					.stream().sorted((n1,n2) -> n2.getDateSent().compareTo(n1.getDateSent())).collect(Collectors.toList());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Notifycation> getAllNotifycationByGroupID(int groupID)
	{
		try
		{
			return groupStudyingRepository.getById(groupID).getNotifycations()
					.stream().filter(p -> p.getNotifycationType() == NotifycationType.user).sorted((n1,n2) -> n2.getDateSent().compareTo(n1.getDateSent())).collect(Collectors.toList());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public NotifycationDTO loadNotifycation(String userName, int notifycationID)
	{
		var user = userRepository.getById(userName);
		var notifycation = notifycationRepository.getById(notifycationID);
		
		notifycation.getUserSeenNotifycation().remove(userName);
		notifycationRepository.save(notifycation);
		
		return new NotifycationDTO(notifycation);
	}

	@Override
	public boolean checkNewNotifycation(String userName, int notifycationID)
	{
		System.out.println(notifycationID);
		System.out.println(userName);
		System.out.println(notifycationRepository.getById(notifycationID).getUserSeenNotifycation().size());
		
		return notifycationRepository.getById(notifycationID).getUserSeenNotifycation()
				.stream().anyMatch(p -> p.equals(userName));
	}

	@Override
	public List<Notifycation> findNotifycation(String userName, String inputContentbyUser)
	{
		return userRepository.getById(userName).getNotifycations().stream()
				.filter(p -> p.getContent().contains(inputContentbyUser) || p.getHeader().contains(inputContentbyUser))
				.sorted((n1,n2) -> n1.getDateSent().compareTo(n2.getDateSent())).collect(Collectors.toList());
	}

	@Override
	public String deleteNotifycationByLeaderGroupForAll(String userName, int notifycationID, int groupID)
	{
		try
		{
			var user = userRepository.getById(userName);
			var notifycation = notifycationRepository.getById(notifycationID);
			var group = groupStudyingRepository.getById(groupID);
			
			if (notifycation.getGroupStudying().getGroupID() == group.getGroupID() && group.getLeaderOfGroup().getUserName().equals(user.getUserName()) && group.getLeaderOfGroup().getUserName().equals(user.getUserName()))
			{
				group.getNotifycations().remove(notifycation);
				notifycation.setGroupStudying(null);
				for (var p: notifycation.getUsers())
				{
					p.getNotifycations().remove(notifycation);
				}
				notifycation.setUsers(null);
				notifycation.setUserSeenNotifycation(null);
				
				groupStudyingRepository.save(group);
				userRepository.save(user);
				
				if (notifycation.getImages().size() > 0)
				{
					for (var p : notifycation.getImages())
					{
						StorageClient.getInstance().bucket().get(p).delete();											
					}
				}
								
				notifycationRepository.delete(notifycation);
				
				return "Successful";
			}
			else
			{
				return "Failed";
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "failed";
		}
	}

	@Override
	public void deleteNotifycationForMyAccount(String userName, int notifycationID, int groupID)
	{
		var user = userRepository.getById(userName);
		var notifycation = notifycationRepository.getById(notifycationID);
		var group = groupStudyingRepository.getById(groupID);
		
		if (notifycation.getGroupStudying().getGroupID() == group.getGroupID())
		{
			notifycation.getUsers().remove(user);
			user.getNotifycations().remove(notifycation);
			
			if (notifycation.getUsers().size() >= 1)
			{
				userRepository.save(user);
				notifycationRepository.save(notifycation);
			}
			else
			{
				notifycation.setUsers(null);
				notifycation.setUserSeenNotifycation(null);
				group.getNotifycations().remove(notifycation);
				notifycation.setGroupStudying(null);
				
				groupStudyingRepository.save(group);
				userRepository.save(user);
				notifycationRepository.delete(notifycation);
			}
		}
		else
		{
			System.out.println("Xoá không thành công");
		}
	}
}
