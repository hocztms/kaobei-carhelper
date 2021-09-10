package com.kaobei;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kaobei.entity.ParkEntity;
import com.kaobei.mapper.ParkMapper;
import com.kaobei.service.AdminService;
import com.kaobei.service.ParkRecordService;
import com.kaobei.service.ParkService;
import com.kaobei.service.UserService;
import com.kaobei.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

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
    @Autowired
    private ParkRecordService parkRecordService;

    @Test
    void contextLoads() {
//        List<ParkDto> parkListByPosNear = parkService.findParkListByPosNear(119.00, 26.00, 1000.00, 2);
//
//        System.out.println(parkListByPosNear.toString());

//        List<ParkDto> dto = parkMapper.findParkDtoByKeyword("福大",new Page(1,2));
//        System.out.println(dto.toString());

//        Double areaRecordsCostSum = parkRecordService.getAreaRecordsCostSum(1L, );
//        System.out.println(areaRecordsCostSum.doubleValue());


//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date());
//        calendar.set(Calendar.HOUR_OF_DAY, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        Date zero = calendar.getTime();
//        System.out.println(zero.toString());

        Date zeroTime = DateUtils.getTomoZeroTime(new Date());
        System.out.println(zeroTime.toString());
//
//        QueryWrapper<ParkEntity> wrapper = new QueryWrapper<>();
//        wrapper.eq("area_id",1);
//        wrapper.select("limit 1");
//        ParkEntity parkEntity = parkMapper.selectOne(wrapper);
//        System.out.println(parkEntity.toString());
    }

}
