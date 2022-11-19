package com.mrj.system.service.impl;

import com.mrj.model.system.SysUser;
import com.mrj.system.custom.CustomUser;
import com.mrj.system.service.SysMenuService;
import com.mrj.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = sysUserService.getUserInfoByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在!");
        }
        if (user.getStatus() == 0) {
            throw new RuntimeException("用户已被禁用，请联系管理员!");
        }
        List<String> userPermsList = sysMenuService.getUserButtonList(user.getId(), user.getUsername());
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String perm : userPermsList) {
            authorities.add(new SimpleGrantedAuthority(perm.trim()));
        }
        return new CustomUser(user, authorities);
    }
}
