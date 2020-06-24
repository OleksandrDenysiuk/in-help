package com.portfolio.inhelp.bootstrap;

import com.portfolio.inhelp.model.*;
import com.portfolio.inhelp.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
@Order(1)
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final AccidentRepository accidentRepository;
    private final RoleRepository roleRepository;
    private final ImageRepository imageRepository;
    private final CommentRepository commentRepository;
    private final NewsRepository newsRepository;

    public DataLoader(UserRepository userRepository, AccidentRepository accidentRepository, RoleRepository roleRepository, ImageRepository imageRepository, CommentRepository commentRepository, NewsRepository newsRepository) {
        this.userRepository = userRepository;
        this.accidentRepository = accidentRepository;
        this.roleRepository = roleRepository;
        this.imageRepository = imageRepository;
        this.commentRepository = commentRepository;
        this.newsRepository = newsRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Role roleUser = new Role(1L,"ROLE_USER");
        roleRepository.save(roleUser);
        Role roleAdmin = new Role(1L,"ROLE_ADMIN");
        roleRepository.save(roleAdmin);

        Image image = Image.builder().id(1L).imageBytes(new byte[] { (byte)0xe0, 0x4f, (byte)0xd0}).build();
        User user1 = User.builder()
                .id(1L)
                .username("u")
                .password(new BCryptPasswordEncoder().encode("1"))
                .firstName("Alex")
                .lastName("Denysiuk")
                .email("alex@alex.com")
                .phoneNumber("+48796363016")
                .accidents(new HashSet<>())
                .roles(new HashSet<>())
                .comments(new HashSet<>()).build();
        image.setUser(user1);
        user1.setAvatar(image);
        user1.getRoles().add(roleAdmin);
        userRepository.save(user1);

        Accident accident1 = Accident.builder()
                .id(1L)
                .title("title")
                .content("content")
                .comments(new HashSet<>())
                .images(new HashSet<>()).build();
        user1.addAccident(accident1);
        accidentRepository.save(accident1);

        Comment comment = Comment.builder()
                .id(1L)
                .content("content")
                .build();
        accident1.addComment(comment);
        user1.addComment(comment);
        commentRepository.save(comment);

        News news = News.builder()
                .id(1L)
                .title("title")
                .content("content")
                .accident(accident1)
                .comments(new HashSet<>())
                .images(new HashSet<>())
                .build();

        newsRepository.save(news);

        Comment comment2 = Comment.builder()
                .id(2L)
                .content("content2")
                .build();
        news.addComment(comment2);
        user1.addComment(comment2);
        commentRepository.save(comment2);


        Image image2 = Image.builder().id(2L).imageBytes(new byte[] { (byte)0xe0, 0x4f, (byte)0xd0}).build();
        Image image3 = Image.builder().id(3L).imageBytes(new byte[] { (byte)0xe0, 0x4f, (byte)0xd0}).build();
        accident1.addImage(image2);
        imageRepository.save(image2);
        accident1.addImage(image3);
        imageRepository.save(image3);

        Image image4 = Image.builder().id(4L).imageBytes(new byte[] { (byte)0xe0, 0x4f, (byte)0xd0}).build();
        Image image5 = Image.builder().id(5L).imageBytes(new byte[] { (byte)0xe0, 0x4f, (byte)0xd0}).build();
        news.addImage(image4);
        news.addImage(image5);
        imageRepository.save(image4);
        imageRepository.save(image5);
    }
}
