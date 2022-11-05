package com.mrj.system;

import com.mrj.model.system.SysRole;
import com.mrj.system.mapper.SysRoleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
@SpringBootTest
public class MapperTest {

    @Autowired
    SysRoleMapper sysRoleMapper;

    @Test
    public void test01() {
        List<SysRole> roles = sysRoleMapper.selectList(null);
        roles.forEach(System.out::println);
    }

    @Test
    void test02() {
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("测试角色2");
        sysRole.setRoleCode("testManager2");
        sysRole.setDescription("测试角色2");
        int result = sysRoleMapper.insert(sysRole);
        System.out.println(result);
    }


}
