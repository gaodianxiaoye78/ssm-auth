package com.mrj.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mrj.model.system.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author mrj
 * @since 2022-11-12
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> findMenuListUserId(@Param("userId") String userId);
}
