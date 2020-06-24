package com.portfolio.inhelp.bootstrap;

import com.portfolio.inhelp.model.Accident;
import com.portfolio.inhelp.model.Image;
import com.portfolio.inhelp.model.Role;
import com.portfolio.inhelp.model.User;
import com.portfolio.inhelp.repository.AccidentRepository;
import com.portfolio.inhelp.repository.ImageRepository;
import com.portfolio.inhelp.repository.RoleRepository;
import com.portfolio.inhelp.repository.UserRepository;
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

    public DataLoader(UserRepository userRepository, AccidentRepository accidentRepository, RoleRepository roleRepository, ImageRepository imageRepository) {
        this.userRepository = userRepository;
        this.accidentRepository = accidentRepository;
        this.roleRepository = roleRepository;
        this.imageRepository = imageRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Role role = new Role(1L,"ROLE_USER");
        roleRepository.save(role);
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
                .roles(new HashSet<>()).build();
        image.setUser(user1);
        user1.setAvatar(image);
        user1.getRoles().add(role);
        userRepository.save(user1);
        User user2 = User.builder().id(2L).accidents(new HashSet<>()).build();
        userRepository.save(user2);
        Accident accident1 = Accident.builder().id(1L).build();
        accidentRepository.save(accident1);
        Accident accident2 = Accident.builder().id(2L).build();
        accidentRepository.save(accident2);
        user1.addAccident(accident1);
        userRepository.save(user1);
        user2.addAccident(accident2);
        userRepository.save(user2);
    }
}
