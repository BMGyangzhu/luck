package org.luckyjourney.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.luckyjourney.authority.AuthorityUtils;
import org.luckyjourney.entity.user.*;
import org.luckyjourney.mapper.user.PermissionMapper;
import org.luckyjourney.service.user.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.luckyjourney.service.user.RolePermissionService;
import org.luckyjourney.service.user.UserRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.awt.peer.ListPeer;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private RolePermissionService rolePermissionService;



    // Permission比较器
    private  class PermissionComparator implements Comparator<Permission> {

        @Override
        public int compare(Permission o1, Permission o2) {
            return -o1.getSort()-o2.getSort();
        }
    }
    @Override
    public List<Permission> initMenu(Long uId) {

        List<Permission> parentPermission = new ArrayList<>();
        // 封装权限集合
        Set<String> set = new HashSet<>();
        
        // 根据用户id查询对应的角色id
        List<Long> rIds = userRoleService.list(
                                                 new LambdaQueryWrapper<UserRole>()
                                                         .eq(UserRole::getUserId, uId)
                                                         .select(UserRole::getRoleId)
                                         ).stream()
                                         .map(UserRole::getRoleId)
                                         .collect(Collectors.toList());

        if (ObjectUtils.isEmpty(rIds)) {
            return Collections.emptyList();
        }

        // 根据角色查询对应的权限id
        List<Integer> pIds = rolePermissionService.list(
                                                          new LambdaQueryWrapper<RolePermission>()
                                                                  .in(RolePermission::getRoleId, rIds)
                                                                  .select(RolePermission::getPermissionId)
                                                  ).stream()
                                                  .map(RolePermission::getPermissionId)
                                                  .collect(Collectors.toList());

        if (ObjectUtils.isEmpty(pIds)) {
            return Collections.emptyList();
        }

        // 查询权限列表
        List<Permission> permissions = list(
                new LambdaQueryWrapper<Permission>()
                        .in(Permission::getId, pIds)
        );

        for (Permission permission : permissions) {
            if (permission.getPId().compareTo(0L) == 0 && permission.getIsMenu() != 1) {
                permission.setChildren(new ArrayList<Permission>());
                parentPermission.add(permission);
            }
        }
        
        for (Permission permission : parentPermission) {
            permission.getChildren().add(findChild(permission,permissions, set));
        }

        AuthorityUtils.setAuthority(uId, set);

        return parentPermission;
    }

    @Override
    public List<Permission> treeSelect() {

        // 创建返回结果
        List<Permission> data = new ArrayList<>();

        // 查出所有权限 非按钮
        List<Permission> permissions = list(new LambdaQueryWrapper<Permission>().ne(Permission::getIsMenu, 1));

        // 找到根节点
        for (Permission permission : permissions) {
            if (permission.getPId().compareTo(0L) == 0) {
                permission.setChildren(new ArrayList<Permission>());
                data.add(permission);
            }
        }

        // 根据根节点找到子节点
        for (Permission datum : data) {
            datum.getChildren().add(findTreeSelectChildren(datum,permissions));
        }

        return data;
    }
    @Override
    public List<Permission> permissionTree() {
        List <Permission> result = new ArrayList<>();

        List<Permission> permissions = list(null);

        // 找到根节点
        for (Permission permission : permissions) {
            if (permission.getPId() == 0) {
                permission.setChildren(new ArrayList<Permission>());
                result.add(permission);
            }
        }

        // 根节点找到节点
        for (Permission permission : result) {
            permission.getChildren().add(findTreeSelectChildren(permission,permissions));
        }

        return result;
    }

  
    @Override
    @Transactional
    public void removeMenu(Long id) {
        // 封装id集合
        List<Long> ids = new ArrayList<>();
        findPermissionId(id,ids);
        ids.add(id);
        removeByIds(ids);
        rolePermissionService.remove(new LambdaQueryWrapper<RolePermission>().in(RolePermission::getPermissionId,ids));

    }

    private void findPermissionId(Long id, List<Long> ids) {

        list(new LambdaQueryWrapper<Permission>().eq(Permission::getPId,id).select(Permission::getId))
                .stream()
                .forEach(permission -> {
                    ids.add(permission.getId());
                    findPermissionId(permission.getId(),ids);
                });
    }


    private Permission findTreeSelectChildren(Permission datum, List<Permission> permissions) {

        datum.setChildren(new ArrayList<Permission>());
        for (Permission permission : permissions) {
            if (datum.getId().compareTo(permission.getPId()) == 0) {
                datum.getChildren().add(findTreeSelectChildren(permission,permissions));
            }
        }
        return datum;
    }
    
    
    private Permission findChild(Permission permission, List<Permission> permissions, Set<String> set) {
        permission.setChildren(new ArrayList<Permission>());
        for (Permission p : permissions) {
            if (!ObjectUtils.isEmpty(p.getPath())) {
                set.add(p.getPath());
            }
            if (p.getIsMenu() != 1) {
                if (permission.getId().compareTo(p.getPId()) == 0) {
                    permission.getChildren().add(findChild(p,permissions,set));
                }
            }
        }
        return permission;
    }


//    private Menu findChild(Menu menu, List<Menu> menus, Set<String> set) {
//        menu.setChild(new ArrayList<Menu>());
//        for (Menu m : menus) {
//            if (!ObjectUtils.isEmpty(m.getPath())){
//                set.add(m.getPath());
//            }
//            if (m.getIsMenu()!=1){
//                if (menu.getId().compareTo(m.getPId()) ==0 ) {
//                    // 递归调用该方法
//                    menu.getChild().add(findChild(m,menus,set));
//                }
//            }
//        }
//        return menu;
//    }
}
