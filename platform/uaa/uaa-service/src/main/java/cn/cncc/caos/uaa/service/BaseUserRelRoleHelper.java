package cn.cncc.caos.uaa.service;

import cn.cncc.caos.common.core.utils.TimeUtil;
import cn.cncc.caos.uaa.db.dao.BaseUserRelRoleMapper;
import cn.cncc.caos.uaa.db.daoex.BaseUserRelRoleMapperEx;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUserRelRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static cn.cncc.caos.uaa.db.dao.BaseUserRelRoleDynamicSqlSupport.roleId;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

@Slf4j
@Service
public class BaseUserRelRoleHelper {

    @Resource
    private BaseUserRelRoleMapper baseUserRelRoleMapper;

    @Resource
    private BaseUserRelRoleMapperEx baseUserRelRoleMapperEx;

    public void updateUserRelRole(Map jsonObject) {
        Integer roleIdInput = Integer.parseInt(jsonObject.get("roleId").toString());
        baseUserRelRoleMapper.deleteByExample().where(roleId, isEqualTo(roleIdInput)).build().execute();
        String userIdListInput = jsonObject.get("userIds").toString();

        if (!userIdListInput.equals("")) {
            String userIdList[] = userIdListInput.split(",");

            for (String userIdString : userIdList) {
                BaseUserRelRole burr = new BaseUserRelRole();
                burr.setUserId(Integer.parseInt(userIdString));
                burr.setRoleId(roleIdInput);
                burr.setIsValid(1);
                burr.setUpdateTime(TimeUtil.getCurrentTime());
                baseUserRelRoleMapper.insert(burr);
            }
        }
    }

    public void batchInsertOnSync(List<BaseUserRelRole> targetBaseUserRelRoleList) {
        baseUserRelRoleMapperEx.batchInsert(targetBaseUserRelRoleList);
        log.info("batch insert targetBaseUserRelRoleList end on sync");
    }

    public void deleteByUserOnSync(Integer targetId) {
        baseUserRelRoleMapperEx.deleteByUser(targetId);
        log.info("batch delete targetBaseUserRelRoleList end, targetId:" + targetId);
    }
}
