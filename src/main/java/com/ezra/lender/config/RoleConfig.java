package com.ezra.lender.config;

import com.ezra.lender.model.Role;
import com.ezra.lender.model.User;
import com.ezra.lender.repository.RoleRepository;
import com.ezra.lender.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Locale;


@Component
@Slf4j
public class RoleConfig implements CommandLineRunner {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {
        log.info("reached RoleConfig.......................");
       String Admin="ADMIN";
       String Member="MEMBER";
       if(!roleRepository.findByName(Admin).isPresent()){
           Role role=new Role();
           role.setName(Admin);
           roleRepository.save(role);
       }if(!roleRepository.findByName(Member).isPresent()){
            Role role=new Role();
            role.setName(Member);
            roleRepository.save(role);
        }
//        if(!userRepository.existsByUsername(Member.toLowerCase(Locale.ROOT))){
//            User user=new User();
//            user.setEmail("member@gmail.com");
//            user.setName("member");
//            user.setUsername("member");
//            user.setPassword(passwordEncoder.encode("member"));
//            user.setCreditStatus("GOOD");
//            user.setMsisdn("0700000000");
//            user.setRoles(Collections.singleton(roleRepository.findByName("MEMBER").get()));
//            userRepository.save(user);
//        }
//        if(!userRepository.existsByUsername(Admin.toLowerCase(Locale.ROOT))){
//            User user=new User();
//            user.setEmail("admin@gmail.com");
//            user.setName("admin");
//            user.setUsername("admin");
//            user.setPassword(passwordEncoder.encode("admin"));
//            user.setCreditStatus("GOOD");
//            user.setMsisdn("0700000001");
//            user.setRoles(Collections.singleton(roleRepository.findByName("ADMIN").get()));
//            userRepository.save(user);
//        }

    }
}