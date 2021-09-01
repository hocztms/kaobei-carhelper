package com.kaobei;

import com.kaobei.entity.AdminEntity;
import com.kaobei.entity.UserEntity;
import com.kaobei.service.AdminService;
import com.kaobei.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class KaobeiKaobeiCarhelperApplicationTests {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;


    @Test
    void contextLoads() {
        userService.insertUser(new UserEntity("123456",null,0));

    }

}
