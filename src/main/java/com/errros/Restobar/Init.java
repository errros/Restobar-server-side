package com.errros.Restobar;

import com.errros.Restobar.entities.Sys_Admin;
import com.errros.Restobar.models.UserRole;
import com.errros.Restobar.repositories.Sys_AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Init implements CommandLineRunner {

    public static final String SYS_ADMIN_MAIL = "admin@admin.com";
    public static final String SYS_ADMIN_PASSWORD = "admin";


    @Autowired
   private Sys_AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        var admin =adminRepository.findByEmail(SYS_ADMIN_MAIL);
        if(admin.isEmpty()){
            String password = passwordEncoder.encode(SYS_ADMIN_PASSWORD);
            Sys_Admin sys_admin = new Sys_Admin(1l,"admin","admin","admin",password, UserRole.SYS_ADMIN,SYS_ADMIN_MAIL,null,null,true);
            adminRepository.save(sys_admin);
        }

    }
}
