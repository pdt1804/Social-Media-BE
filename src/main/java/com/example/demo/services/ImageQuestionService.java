package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.ImageQuestion;
import com.example.demo.repositories.ImageQuestionRepository;

import jakarta.annotation.PostConstruct;

@Service
public class ImageQuestionService {

	@Autowired
	private ImageQuestionRepository listeningQuestionRepository;
	
	public List<ImageQuestion> getAllImagesQuestion() {
		return listeningQuestionRepository.findAll();
	}
	
	@PostConstruct
	private void generateQuestion() {
		if (listeningQuestionRepository.findAll().size() == 0) {
			listeningQuestionRepository.save(new ImageQuestion(1, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQCRrPYweNqDUEM6wZzW4Y4-la0yI7u2pNG2g&s", "https://as1.ftcdn.net/v2/jpg/02/40/28/24/1000_F_240282440_U1YSJ4SPZioVCP4iGLbPkwCGLGf51XDL.jpg", "Toothbrush"));
			listeningQuestionRepository.save(new ImageQuestion(2, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ0fCLziY34uNDxaTGcLo3VEv3s8GoQo8tTWQ&s", "https://cdn-icons-png.flaticon.com/512/3125/3125713.png", "Butterfly"));
			listeningQuestionRepository.save(new ImageQuestion(3, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ7YaAtxBgMeiYckiGBJEc3PDNlm-PlXQVkHA&s", "https://thumb.ac-illust.com/17/174a218593e40060abb0ab8219bcbecb_t.jpeg", "Football"));
			listeningQuestionRepository.save(new ImageQuestion(4, "https://cdn-icons-png.flaticon.com/512/10480/10480648.png", "https://img.oyster.com/production/Articles/10%20of%20the%20Most%20Beautiful%20Gardens%20Around%20the%20World/Content/17263_18001998256_064852f8c0_o_jpg_40ffb55cd5.jpeg", "Sunflower"));
			listeningQuestionRepository.save(new ImageQuestion(5, "https://cdn-icons-png.flaticon.com/512/2530/2530064.png", "https://i.pinimg.com/564x/20/8e/1f/208e1f4a8d572f1e41caa7ac49c10baa.jpg", "Snowman"));			listeningQuestionRepository.save(new ImageQuestion(5, "https://cdn-icons-png.flaticon.com/512/2530/2530064.png", "https://i.pinimg.com/564x/20/8e/1f/208e1f4a8d572f1e41caa7ac49c10baa.jpg", "Snowman"));
			listeningQuestionRepository.save(new ImageQuestion(6, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTqAgvmsVVW5dDYCFF43Vg8s-bN6VYbuTv-IQ&s", "https://www.shutterstock.com/image-vector/fish-logo-images-illustration-design-260nw-2282544091.jpg", "Starfish"));
			listeningQuestionRepository.save(new ImageQuestion(7, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQm-hkkCeOJ6AEkszacVqj_pWA7OOVY7wKw0g&s", "https://www.shutterstock.com/image-vector/bow-ribbon-pattern-repeating-hand-600nw-2454625337.jpg", "Rainbow"));
			listeningQuestionRepository.save(new ImageQuestion(8, "https://t4.ftcdn.net/jpg/00/97/40/63/360_F_97406348_msz8c5kzxBP93hjyc3t6Myt90QnJ9xrl.jpg","https://static.vecteezy.com/system/resources/thumbnails/023/227/926/small/shadow-overlay-on-the-greenboards-on-the-classroom-wall-illustration-of-board-with-empty-space-vector.jpg", "Keyboard"));
			listeningQuestionRepository.save(new ImageQuestion(9, "https://static.vecteezy.com/system/resources/previews/002/412/477/non_2x/coffee-cup-logo-coffee-shop-icon-design-free-vector.jpg", "https://www.shutterstock.com/image-vector/colorful-cake-logo-vector-illustration-600nw-2516005671.jpg", "Cupcake"));
			listeningQuestionRepository.save(new ImageQuestion(10, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRx5jkEOtsh_7b1kCPy2rIf5UK178kybuWj6w&s", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQewG2DtvuospME47fnuHVGfJBWht0Ws8AaKA&s", "Footprint"));
		}
	}
}
