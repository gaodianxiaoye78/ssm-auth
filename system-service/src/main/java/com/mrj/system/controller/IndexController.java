package com.mrj.system.controller;

import com.mrj.common.result.Result;
import com.mrj.common.utils.JwtHelper;
import com.mrj.model.system.SysUser;
import com.mrj.model.vo.LoginVo;
import com.mrj.system.exception.OperationException;
import com.mrj.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "登录信息")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController {

    @Autowired
    private SysUserService sysUserService;


    @ApiOperation("用户登录")
    @PostMapping("login")
    public Result login(@RequestBody LoginVo loginVo) {
        String username = loginVo.getUsername();
        String password = loginVo.getPassword();
        SysUser user = sysUserService.getUserInfoByUserName(username);
        if (user == null || !user.getPassword().equals(password)) {
            throw new OperationException(20001, "用户名或密码错误");
        }
        String token = JwtHelper.createToken(user.getId(), username);
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        return Result.ok(map);
    }

    @ApiOperation("获取登录用户信息")
    @GetMapping("info")
    public Result info(HttpServletRequest request) {
        String token = request.getHeader("token");
        String userId = JwtHelper.getUserId(token);
        String username = JwtHelper.getUsername(token);
        Map<String, Object> map = sysUserService.getUserInfo(userId, username);
        return Result.ok(map);
    }


}
