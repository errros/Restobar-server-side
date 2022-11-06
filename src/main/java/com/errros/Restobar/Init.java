package com.errros.Restobar;

import com.errros.Restobar.authentication.User;
import com.errros.Restobar.authentication.UserRepository;
import com.errros.Restobar.authentication.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Init implements CommandLineRunner {

    public static final String SYS_ADMIN_MAIL = "admin@admin.com";
    public static final String SYS_ADMIN_PASSWORD = "admin";


    @Autowired
   private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        var admin =userRepository.findByEmail(SYS_ADMIN_MAIL);
        if(admin.isEmpty()){
            String password = passwordEncoder.encode(SYS_ADMIN_PASSWORD);
            User u = new User(1l,"admin","admin","admin",password, UserRole.SYS_ADMIN,SYS_ADMIN_MAIL,null,null,true);
            userRepository.save(u);
        }

    }
}
