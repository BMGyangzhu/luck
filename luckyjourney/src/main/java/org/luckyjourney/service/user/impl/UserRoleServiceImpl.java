package org.luckyjourney.service.user.impl;

import org.luckyjourney.entity.user.UserRole;
import org.luckyjourney.mapper.user.UserRoleMapper;
import org.luckyjourney.service.user.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
