package com.nts.iot.modules.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.exception.BadRequestException;
import com.nts.iot.exception.EntityExistException;
import com.nts.iot.exception.EntityNotFoundException;
import com.nts.iot.modules.system.dao.UserMapper;
import com.nts.iot.modules.system.dto.UserDTO;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.model.UserDevice;
import com.nts.iot.modules.system.model.UserRoleRelation;
import com.nts.iot.modules.system.model.vo.UserOperationalVo;
import com.nts.iot.modules.system.model.vo.UserRq;
import com.nts.iot.modules.system.model.vo.UserVo;
import com.nts.iot.modules.system.service.*;
import com.nts.iot.util.ExcelUtil;
import com.nts.iot.util.RestResponse;
import com.nts.iot.utils.EncryptUtils;
import com.nts.iot.utils.PageUtil;
import com.nts.iot.utils.ValidationUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author jie
 * @date 2018-11-23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private static final Integer DEL_FLAG = 2;

    private static final Integer USER_ENABLE = 0;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleRelaService userRoleRelaService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private UserDeviceService userDeviceService;


    @Override
    public Map queryAll(UserDTO userDTO, Set<Long> result, Pageable pageable) {
        Page<User> page = new Page(pageable.getPageNumber() + 1, pageable.getPageSize());
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (userDTO.getId() != null) {
            wrapper.like("id", userDTO.getId());
        }
        if (StrUtil.isNotEmpty(userDTO.getUsername())) {
            wrapper.like("username", userDTO.getUsername());
        }
        if (StrUtil.isNotEmpty(userDTO.getEmail())) {
            wrapper.like("email", userDTO.getEmail());
        }
        if (userDTO.getEnabled() != null) {
            wrapper.eq("enabled", userDTO.getEnabled());
        }

        if (result != null && result.size() > 0) {
            wrapper.in("dept_id", result);
        }
        wrapper.isNotNull("dept_id");
        wrapper.isNotNull("job_id");
        wrapper.isNotNull("enabled");
        IPage<User> pageResult = baseMapper.selectPage(page, wrapper);
//        pageResult.getRecords().forEach(user -> {
//            Dept dept = deptService.getById(user.getDeptId());
//            Job job = jobService.getById(user.getJobId());
//            user.setRoles(new HashSet<>(roleService.findByUserId(user.getId())));
//            user.setDept(dept);
//            user.setJob(job);
//        });
        return PageUtil.toPage(pageResult);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getUsers(UserRq userRq, Pageable pageable) {
        Page<User> page = new Page(pageable.getPageNumber(), pageable.getPageSize());
        QueryWrapper<User> wrapper = getUserQueryWrapper(userRq);
        wrapper.ne("enabled", USER_ENABLE);

        if (userRq.getId() != null) {
            wrapper.and(Wrapper -> Wrapper
                    .eq("p_id", userRq.getId()));
        }
        if (StrUtil.isNotEmpty(userRq.getUsername())) {
            wrapper.and(Wrapper -> Wrapper
                    .like("username", userRq.getUsername())
                    .or()
                    .like("nick_name", userRq.getUsername()));
        }

        wrapper.eq("del_flag", 1);
//        if (StrUtil.isNotEmpty(userRq.getEmail())) {
//            wrapper.like("email", userRq.getEmail());
//        }
        IPage<User> pageResult = baseMapper.selectPage(page, wrapper);
        pageResult.getRecords().forEach(result ->
                result.setDeviceSum(userDeviceService.getBaseMapper().selectCount(new QueryWrapper<UserDevice>().eq("user_id", result.getId())))
        );
        return PageUtil.toPage(pageResult);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> usersTree(UserRq userRq) {
        QueryWrapper<User> wrapper = getUserQueryWrapper(userRq);
        wrapper.ne("enabled", USER_ENABLE);
        List<User> selectList = baseMapper.selectList(wrapper);

        // 获取数据库全部正常数据
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("del_flag", 1);
        queryWrapper.ne("enabled", USER_ENABLE);
        List<User> selectListList = baseMapper.selectList(queryWrapper);
        List<User> userRootList = selectList
                .stream()
                .filter(user -> !user.getDelFlag().equals(DEL_FLAG) &&
                        !selectList.stream().map(User::getId).collect(Collectors.toList()).contains(user.getPId()))
                .collect(Collectors.toList());
        getChildUser(selectListList, userRootList);

        return userRootList;
    }

    @Override
    @Transactional(readOnly = true)
    public RestResponse export(Long userId, String userName) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.and(Wrapper -> Wrapper
                .eq("p_id", userId));

        if (StrUtil.isNotBlank(userName)) {
            wrapper.and(Wrapper -> Wrapper
                    .like("username", userName)
                    .or()
                    .like("nick_name", userName));
        }
        wrapper.eq("del_flag", 1);
        wrapper.ne("enabled", USER_ENABLE);
        List<User> userList = baseMapper.selectList(wrapper);
        List<UserVo> userVoList = new ArrayList<>();
        // 将user对象的集合转换为userVo对象的集合，也可以不转换直接导出user对象集合
        userList.forEach(user -> {
            UserVo userVo = new UserVo();
            user.setDeviceSum(userDeviceService.getBaseMapper().selectCount(new QueryWrapper<UserDevice>().eq("user_id", user.getId())));
            BeanUtils.copyProperties(user, userVo);
            userVoList.add(userVo);
        });

        ExcelUtil<UserVo> util = new ExcelUtil<>(UserVo.class);
        return util.exportExcel(userVoList, "User Info");
    }

    private void getChildUser(List<User> result, List<User> userRootList) {
        // 给上级新增子集
        userRootList.forEach(userRoot -> {
            // 获取子集
            List<User> childUser = result
                    .stream()
                    .filter(user -> (!user.getDelFlag().equals(DEL_FLAG) &&
                            user.getPId().equals(userRoot.getId())))
                    .collect(Collectors.toList());
            if (childUser.size() > 0) {
                userRoot.setChildUser(childUser);
                getChildUser(result, childUser);
            }
        });

    }

    private QueryWrapper<User> getUserQueryWrapper(UserRq userRq) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (userRq.getId() != null) {
            wrapper.and(Wrapper -> Wrapper
                    .eq("id", userRq.getId())
                    .or()
                    .eq("p_id", userRq.getId()));
        }
        if (StrUtil.isNotEmpty(userRq.getUsername())) {
            wrapper.and(Wrapper -> Wrapper
                    .like("username", userRq.getUsername())
                    .or()
                    .like("nick_name", userRq.getUsername()));
        }

        wrapper.eq("del_flag", 1);
//        if (StrUtil.isNotEmpty(userRq.getEmail())) {
//            wrapper.like("email", userRq.getEmail());
//        }
        return wrapper;
    }

    @Override
    public UserDTO findById(long id) {
        UserDTO userDTO = new UserDTO();
        User user = baseMapper.selectById(id);
        BeanUtil.copyProperties(user, userDTO);
        return userDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(UserRq resources, User user1) {

        // 校验参数
        createCheckParam(resources, user1);
        resources.setPassword(EncryptUtils.encryptPassword(resources.getPassword()));
        User user = new User();
        BeanUtils.copyProperties(resources, user);
        user.setAvatar("https://aurora-1255840532.cos.ap-chengdu.myqcloud.com/8918a306ea314404835a9196585c4b75.jpeg");
        user.setCreateTime(new Timestamp(System.currentTimeMillis()));
        // 保存用户
        int insert = baseMapper.insert(user);
        if (insert <= 0) {
            throw new BadRequestException("新增用户信息失败,Failed to add user information");
        }
        //保存用户、角色关系信息
//        UserRoleRelation userRoleRelation = new UserRoleRelation();
//        userRoleRelation.setUserId(user.getId());
//        boolean save = userRoleRelaService.save(userRoleRelation);
//        if (!save) {
//            throw new BadRequestException("新增用户角色信息失败");
//        }
    }

    /**
     * 校验参数
     *
     * @param resources
     */
    private void createCheckParam(UserRq resources, User user) {
        if (null != resources.getId()) {
            throw new BadRequestException("ID不需要,User ID is not required");
        }
        if (StringUtils.isBlank(resources.getPassword())) {
            throw new BadRequestException("密码不能为空,password can not be blank");
        }
        if (StringUtils.isBlank(resources.getConfirmPassword())) {
            throw new BadRequestException("确认密码不能为空,confirm password can not be blank");
        }
        if (!StringUtils.equals(resources.getPassword(), resources.getConfirmPassword())) {
            throw new BadRequestException("两次输入密码不一致,Two passwords are inconsistent");
        }

        if (user.getCustomTypeId().compareTo(resources.getCustomTypeId()) >= 0) {
            throw new BadRequestException("下级不允许新建上级账号,Subordinates are not allowed to create new superior accounts");
        }

        User userByUsername = baseMapper.findByUsername(resources.getUsername());
//        User userByEmail = baseMapper.findByEmail(resources.getEmail());
        User userByPhone = baseMapper.findByPhone(resources.getPhone());

        if (userByUsername != null) {
            throw new EntityExistException(User.class, "username", resources.getUsername());
        }
//        if (userByEmail != null) {
//            throw new EntityExistException(User.class, "用户邮箱", resources.getEmail());
//        }
        if (userByPhone != null) {
            throw new EntityExistException(User.class, "phone", resources.getPhone());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserRq resources) {
        // 校验参数
        updateCheckParam(resources);
        User copyUser = new User();
        BeanUtils.copyProperties(resources, copyUser);
        //保存用户信息
        this.baseMapper.updateById(copyUser);
    }

    /**
     * 校验参数
     *
     * @param resources
     */
    private void updateCheckParam(UserRq resources) {
        if (null == resources.getId() || 0 == resources.getId()) {
            throw new BadRequestException("id不能为空或者0,id cannot be empty or 0");
        }
        // 如果密码不为空就将密码使用MD5加密
        if (StringUtils.isNotBlank(resources.getPassword())) {
            String password = EncryptUtils.encryptPassword(resources.getPassword());
            resources.setPassword(password);
        }

        User user = baseMapper.selectById(resources.getId());
        if (null == user) {
            throw new BadRequestException("用户不存在,User does not exist");
        }
        User userByUsername = baseMapper.findByUsername(resources.getUsername());

//        User userByEmail = baseMapper.findByEmail(resources.getEmail());
        User userByPhone = baseMapper.findByPhone(resources.getPhone());

        if (userByUsername != null && !userByUsername.getId().equals(user.getId())) {
            throw new BadRequestException("用户名重复,Duplicate username");
        }
//        if (!user.getEmail().equals(resources.getEmail()) && userByEmail != null) {
//            //email没有改，什么都不用管，只有email修改了才做判断
//            throw new EntityExistException(User.class, "用户邮箱", resources.getEmail());
//        }
        if (userByPhone != null && !userByPhone.getId().equals(user.getId())) {
            throw new BadRequestException("用户联系方式重复,Duplicate user contact information");
        }

        // 判断用户是否已经被标记删除
        if (DEL_FLAG.equals(user.getDelFlag())) {
            throw new BadRequestException("用户信息不存在请请确认信息后再进行操作,User information does not exist, please confirm the information before proceeding");
        }
    }

    /**
     * 保存用户、角色信息
     *
     * @param user
     * @param userId
     */
    void saveUserRoleRela(User user, Long userId) {
        List<UserRoleRelation> userRoleRelations = new ArrayList<>();
        user.getRoles().forEach(it -> {
            UserRoleRelation userRoleRelation = new UserRoleRelation();
            userRoleRelation.setRoleId(it.getId());
            userRoleRelation.setUserId(userId);
            userRoleRelations.add(userRoleRelation);
        });
        userRoleRelaService.saveBatch(userRoleRelations);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        baseMapper.deleteById(id);
    }

    /**
     * 通过用户的IDS 批量删除用户信息
     *
     * @param ids
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(List<Long> ids) {
        // 修改用户信息del_flag标记 为删除 2
        ids.forEach(id -> {
            // 查询 其子集是否删除标记，禁用标记
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("p_id", id);
            userQueryWrapper.eq("del_flag", 1);
            userQueryWrapper.eq("enabled", 1);
            List<User> users = this.baseMapper.selectList(userQueryWrapper);
            if (!CollectionUtils.isEmpty(users)) {
                throw new BadRequestException("操作失败请先删除下级,If the operation fails, please delete the subordinate first");
            }
            User user = new User();
            user.setId(id);
            user.setDelFlag(DEL_FLAG);
            this.baseMapper.updateById(user);
        });
    }


    @Override
    public User findByName(String userName) {
        return baseMapper.findByUsername(userName);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePass(String username, String pass) {
        baseMapper.updatePass(username, pass, new Date());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAvatar(String username, String url) {
        baseMapper.updateAvatar(username, url);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateEmail(String username, String email) {
        baseMapper.updateEmail(username, email);
    }


    @Override
    public User findUserByName(String userName) {
        User user = null;
        if (ValidationUtil.isEmail(userName)) {
            user = baseMapper.findByEmail(userName);
        } else {
            user = baseMapper.findByUsername(userName);
        }
        if (user == null) {
            throw new EntityNotFoundException(User.class, "name", userName);
        } else {
            user.setRoles(new HashSet<>(roleService.findByUserId(user.getId())));
//            user.setDept(deptService.getById(user.getDeptId()));
            return user;
        }
    }

    @Override
    public User findUserByopenId(String maOpenId) {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.eq("ma_open_id", maOpenId);
        return baseMapper.selectOne(query);
    }

    @Override
    public List<User> queryListByjurisdictions(List<String> jurisdictions) {
        return baseMapper.queryListByjurisdictions(jurisdictions);
    }

    @Override
    public Integer findByDept(long deptId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dept_id", deptId);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public Integer findByJob(Long jodId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("job_id", jodId);
        return baseMapper.selectCount(queryWrapper);
    }

    /**
     * 运营统计
     *
     * @param userId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public UserOperationalVo operationalStatistics(Long userId) {
        return deviceService.operationalStatistics(userId);
    }

    /**
     * 重置用户密码
     *
     * @param user
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPass(User user, Long id) {
        User user1 = new User();
        // 默认密码为123456
        user1.setId(id);
        String password = EncryptUtils.encryptPassword("123456");
        user1.setPassword(password);
        int i = this.baseMapper.updateById(user1);
        if (i <= 0) {
            throw new BadRequestException("重置密码失败,Password reset failed");
        }
    }
}
