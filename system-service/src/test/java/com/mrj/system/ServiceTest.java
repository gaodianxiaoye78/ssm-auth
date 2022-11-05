package com.mrj.system;

import com.mrj.model.system.SysRole;
import com.mrj.system.service.SysRoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ServiceTest {

    @Autowired
    private SysRoleService sysRoleService;

    @Test
    void test01() {
        List<SysRole> sysRoles = sysRoleService.list();
        sysRoles.forEach(System.out::println);
    }

    @Test
    void test02() {

        SysRole sysRole = new SysRole();
        sysRole.setRoleName("测试角色3");
        sysRole.setRoleCode("testManager3");
        sysRole.setDescription("测试角色3");
        boolean b = sysRoleService.save(sysRole);
        System.out.println(b);

    }



}
