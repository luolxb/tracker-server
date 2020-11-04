package com.nts.iot.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.model.vo.UserOperationalVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zzm
 * @since 2019-04-16
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

    User findByUsername(@Param("username") String username);

    User findByEmail(@Param("email") String email);

    void updatePass(@Param("username") String username, @Param("pass") String pass, @Param("lastPasswordResetTime") Date lastPasswordResetTime);

    void updateAvatar(@Param("username") String username, @Param("url") String url);

    void updateEmail(@Param("username") String username, @Param("email") String email);

    User findByPhone(@Param("phone") String phone);

    List<User> queryListByjurisdictions(@Param("jurisdictions") List<String> jurisdictions);

    /**
     * 运营统计
     *
     * @param userId
     * @return
     */
    UserOperationalVo operationalStatistics(Long userId);
}
