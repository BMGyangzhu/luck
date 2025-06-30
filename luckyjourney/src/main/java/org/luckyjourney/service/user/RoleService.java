package org.luckyjourney.service.user;

import org.luckyjourney.entity.user.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import org.luckyjourney.entity.user.Tree;
import org.luckyjourney.entity.vo.AssignRoleVO;
import org.luckyjourney.entity.vo.AuthorityVO;
import org.luckyjourney.util.R;

import java.util.List;


public interface RoleService extends IService<Role> {

    List<Tree> tree();

    R removeRole(Long id);

    R gavePermission(AuthorityVO authorityVO);

    R gaveRole(AssignRoleVO assignRoleVO);

}
