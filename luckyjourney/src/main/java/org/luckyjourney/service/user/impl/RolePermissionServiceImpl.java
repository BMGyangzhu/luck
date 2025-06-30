package org.luckyjourney.service.user.impl;

import org.luckyjourney.entity.user.RolePermission;
import org.luckyjourney.mapper.user.RolePermissionMapper;
import org.luckyjourney.service.user.RolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {

}
