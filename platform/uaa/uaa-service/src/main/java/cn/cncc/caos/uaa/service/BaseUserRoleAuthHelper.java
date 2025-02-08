package cn.cncc.caos.uaa.service;

import cn.cncc.caos.common.core.utils.TimeUtil;
import cn.cncc.caos.uaa.db.dao.BaseUserRoleAuthMapper;
import cn.cncc.caos.uaa.db.daoex.BaseUserRoleAuthMapperEx;
import cn.cncc.caos.uaa.model.role.user.BaseUserRoleAuthReq;
import cn.cncc.caos.uaa.db.pojo.BaseUserRoleAuth;
import cn.cncc.caos.uaa.utils.IdHelper;
import com.github.pagehelper.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class BaseUserRoleAuthHelper {

    @Resource
    private BaseUserRoleAuthMapperEx baseUserRoleAuthMapperEx;

    @Resource
    private BaseUserRoleAuthMapper baseUserRoleAuthMapper;

    @Resource
    private IdHelper idHelper;

    public void updateUserRoleAuth(@RequestBody BaseUserRoleAuthReq baseUserRoleAuthReq) {
        String roleIdInput = baseUserRoleAuthReq.getRoleId();
        if (StringUtil.isEmpty(roleIdInput))
            return;
        baseUserRoleAuthMapperEx.deleteRoleAuthPermission(new Date(), roleIdInput);
        log.info("delete baseUserRoleAuth end roleId:" + roleIdInput);

        String userIdListInput = baseUserRoleAuthReq.getUserIds();
        if (StringUtil.isNotEmpty(userIdListInput)) {
            String userIdList[] = userIdListInput.split(",");

            for (String userIdString : userIdList) {
                BaseUserRoleAuth bura = new BaseUserRoleAuth();
                bura.setId(idHelper.generateUserRoleAuthId());
                bura.setUserId(Integer.parseInt(userIdString));
                bura.setRoleId(roleIdInput);
                bura.setStatus((byte) 1);
                bura.setCreateTime(TimeUtil.getCurrentTime());
                bura.setUpdateTime(TimeUtil.getCurrentTime());
                baseUserRoleAuthMapper.insert(bura);
                log.info("insert baseUserRoleAuth end roleId:" + roleIdInput+",userId:"+userIdString);
            }
        }
    }

    public void batchInsertOnSync(List<BaseUserRoleAuth> targetBaseUserRoleAuthList) {
        baseUserRoleAuthMapperEx.batchInsert(targetBaseUserRoleAuthList);
        log.info("batch insert baseUserRoleAuth end on sync");
    }

    public void deleteByUserOnSync(Integer targetId) {
        baseUserRoleAuthMapperEx.deleteByUser(targetId, TimeUtil.getCurrentTime());
        log.info("batch delete baseUserRoleAuth end on sync, targetId:" + targetId);
    }
}
