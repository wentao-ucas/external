package cn.cncc.caos.uaa.service;

import cn.cncc.caos.uaa.db.daoex.BaseRoleMapperEx;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description baseRole 相关功能service.
 * @date 2024/1/23 11:17.
 */
@Service
public class BaseRoleService {
    @Autowired
    private BaseRoleMapperEx baseRoleMapperEx;

    public List<BaseRole> getExportRoleBySysName() {
        List<BaseRole> resultList = new ArrayList<>();
        String[] ignoreArray = {"SUPER_ADMIN", "32个CCPC"};
        List<String> ignoreList = Arrays.asList(ignoreArray);
        List<BaseRole> list = baseRoleMapperEx.getRoleBySysName("sso");
        for (BaseRole baseRole : list) {
            if (ignoreList.contains(baseRole.getRoleName())) {
                continue;
            }
            resultList.add(baseRole);
        }
        return resultList;
    }

    public List<String> getRoleNameByDepid(String depId){
        List<BaseRole> baseRoles = baseRoleMapperEx.getRoleNameByDepid(depId);
        if(baseRoles==null || baseRoles.isEmpty()){
            return new ArrayList<>();
        }

        List<String> list = new ArrayList<>();
        for(BaseRole baseRole: baseRoles){
            list.add(baseRole.getRoleName());
        }
        return list;
    }

    public List<BaseRole> getOperationDepRoleByUserId(Integer userId) {
        return baseRoleMapperEx.getOperationDepRoleByUserId(userId);
    }
}
