package com.kaobei;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kaobei.dto.ParkDto;
import com.kaobei.entity.AdminEntity;
import com.kaobei.entity.ParkEntity;
import com.kaobei.entity.UserEntity;
import com.kaobei.mapper.ParkMapper;
import com.kaobei.service.AdminService;
import com.kaobei.service.ParkService;
import com.kaobei.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
class KaobeiKaobeiCarhelperApplicationTests {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;
    @Autowired
    private ParkService parkService;
    @Autowired
    private ParkMapper parkMapper;

    @Test
    void contextLoads() {
//        List<ParkDto> parkListByPosNear = parkService.findParkListByPosNear(119.00, 26.00, 1000.00, 2);
//
//        System.out.println(parkListByPosNear.toString());

        List<ParkDto> dto = parkMapper.findParkDtoByKeyword("福大",new Page(1,2));
        System.out.println(dto.toString());
    }

}
