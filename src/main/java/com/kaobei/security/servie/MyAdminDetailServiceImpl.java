package com.kaobei.security.servie;

import com.kaobei.entity.AdminEntity;
import com.kaobei.entity.AdminRoleEntity;
import com.kaobei.security.entity.MyUserDetails;
import com.kaobei.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyAdminDetailServiceImpl implements UserDetailsService {


    @Autowired
    private AdminService adminService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminEntity adminEntity = adminService.findAdminByUsername(username);
        if(adminEntity ==null){
            throw new RuntimeException(username+"账号不存在");
        }
        List<AdminRoleEntity> adminRoleEntities = adminService.findAdminRolesByUsername(username);
        List<GrantedAuthority> authoritys = new ArrayList<>();
        for (AdminRoleEntity adminRoleEntity : adminRoleEntities){
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(adminRoleEntity.getRole());
            authoritys.add(simpleGrantedAuthority);
        }

        return new MyUserDetails(adminEntity.getUsername(), adminEntity.getPassword(),authoritys);
    }
}
