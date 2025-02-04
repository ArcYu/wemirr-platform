package com.wemirr.platform.authority.repository;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wemirr.framework.db.configuration.dynamic.annotation.TenantDS;
import com.wemirr.framework.db.mybatis.SuperMapper;
import com.wemirr.framework.db.mybatis.auth.DataScope;
import com.wemirr.framework.db.mybatis.conditions.query.LbqWrapper;
import com.wemirr.platform.authority.domain.entity.baseinfo.User;
import com.wemirr.platform.authority.domain.vo.UserResp;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Levin
 */
@TenantDS
@Repository
public interface UserMapper extends SuperMapper<User> {


    /**
     * 分页查询用户
     *
     * @param page    page
     * @param wrapper wrapper
     * @return 查询结果
     */
    IPage<UserResp> findPage(IPage<User> page, LbqWrapper<User> wrapper);

    /**
     * 带数据权限用户列表
     *
     * @param dataScope dataScopeAspectJExpressionPointcut
     * @return 用户
     */
    List<User> list(DataScope dataScope);

    @InterceptorIgnore(tenantLine = "true")
    @Delete("delete from t_user where tenant_id = #{tenantId}")
    void deleteByTenantId(@Param("tenantId") Long tenantId);

    @InterceptorIgnore(tenantLine = "true")
    @Select("select * from t_user where tenant_id = #{tenantId}")
    List<User> selectByTenantId(@Param("tenantId") Long tenantId);
}
