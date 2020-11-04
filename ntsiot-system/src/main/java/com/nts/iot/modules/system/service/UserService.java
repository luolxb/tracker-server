package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.dto.UserDTO;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.model.vo.UserOperationalVo;
import com.nts.iot.modules.system.model.vo.UserRq;
import com.nts.iot.util.RestResponse;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @author jie
 * @date 2018-11-23
 */
@CacheConfig(cacheNames = "user")
public interface UserService extends IService<User> {

    Map queryAll(UserDTO userDTO, Set<Long> result, Pageable pageable);

    /**
     * 获取用户信息
     *
     * @param userRq
     * @param pageable
     * @return
     */
    Map<String, Object> getUsers(UserRq userRq, Pageable pageable);

    /**
     * 获取用户信息树
     *
     * @param userRq
     * @return
     */
    List<User> usersTree(UserRq userRq);

    /**
     * 导出用户信息
     *
     * @param userName
     * @return
     */
    RestResponse export(Long userId,String userName);

    /**
     * get
     *
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    UserDTO findById(long id);

    /**
     * create
     *
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    void create(UserRq resources,User user);

    /**
     * update
     *
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(UserRq resources);

    /**
     * delete
     *
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Long id);

    /**
     * delete
     *
     * @param ids
     */
    @CacheEvict(allEntries = true)
    void deleteByIds(List<Long> ids);

    /**
     * findByName
     *
     * @param userName
     * @return
     */
//    @Cacheable(key = "'loadUserByUsername:'+#p0")
    User findByName(String userName);

    /**
     * 修改密码
     *
     * @param username
     * @param encryptPassword
     */
    @CacheEvict(allEntries = true)
    void updatePass(String username, String encryptPassword);

    /**
     * 修改头像
     *
     * @param username
     * @param url
     */
    @CacheEvict(allEntries = true)
    void updateAvatar(String username, String url);

    /**
     * 修改邮箱
     *
     * @param username
     * @param email
     */
    @CacheEvict(allEntries = true)
    void updateEmail(String username, String email);

    User findUserByName(String userName);

    /**
     * 根据小程序open id 查找用户
     *
     * @param maOpenId
     * @return
     */
    User findUserByopenId(String maOpenId);

    List<User> queryListByjurisdictions(List<String> jurisdictions);

    Integer findByDept(long deptId);

    Integer findByJob(Long jodId);

    /**
     * 运营统计
     *
     * @param userId
     * @return
     */
    UserOperationalVo operationalStatistics(Long userId);

    /**
     * 重置用户密码
     *
     * @param user
     * @param id
     */
    void resetPass(User user, Long id);
}
