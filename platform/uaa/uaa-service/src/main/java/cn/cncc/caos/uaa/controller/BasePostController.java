package cn.cncc.caos.uaa.controller;

import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser;
import cn.cncc.caos.uaa.model.UserHolder;
import com.github.pagehelper.PageHelper;
import cn.cncc.caos.common.core.utils.JacksonUtil;
import cn.cncc.caos.common.core.response.jw.JwResponseCode;
import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.uaa.model.base.post.BasePostAddReq;
import cn.cncc.caos.uaa.model.base.post.BasePostListInfoWithTotal;
import cn.cncc.caos.uaa.model.base.post.BasePostRes;
import cn.cncc.caos.uaa.model.base.post.BasePostUpdateReq;
import cn.cncc.caos.uaa.db.pojo.BasePost;
import cn.cncc.caos.uaa.service.BasePostService;
import cn.cncc.caos.uaa.service.OperationHistoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@Tag(name = "岗位相关接口")
public class BasePostController {
  String operTypeAdd = "basePostAdd";
  String operTypeUpdate = "basePostUpdate";
  String operTypeDelete = "basePostDelete";

  @Resource
  private BasePostService basePostService;

  @Resource
  private OperationHistoryService operationHistoryService;

  /**
   * 查询岗位信息列表
   */
  @PreAuthorize("hasAuthority('position-manage')")
  @Operation(summary = "查询岗位信息列表接口")
  @RequestMapping(value = "/open_api/base/post/list", method = RequestMethod.GET)
  public JwResponseData<BasePostListInfoWithTotal> list(
          @RequestParam(value = "postCode", required = false) String postCode,
          @RequestParam(value = "postName", required = false) String postName,
          @RequestParam(value = "status", required = false) Byte status,
          @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
          @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
    BasePost basePostSearch = new BasePost();
    basePostSearch.setPostCode(postCode);
    basePostSearch.setPostName(postName);
    basePostSearch.setStatus(status);

    Long total = basePostService.selectCount(basePostSearch);

    PageHelper.startPage(pageNum, pageSize);

    List<BasePost> list = basePostService.selectBasePostList(basePostSearch);
    List<BasePostRes> basePostResList = new ArrayList<>();
    for (BasePost basePost : list) {
      BasePostRes basePostRes = new BasePostRes();
      BeanUtils.copyProperties(basePost, basePostRes);
      basePostResList.add(basePostRes);
    }

    BasePostListInfoWithTotal res = new BasePostListInfoWithTotal();
    res.setTotal(total);
    res.setList(basePostResList);
    return JwResponseData.success("获取成功", res);
  }

  /**
   * 获取岗位信息详细信息
   */
//  @PreAuthorize("hasAuthority('position-manage')")
  @PreAuthorize("hasAuthority('position-manage')")
  @Operation(summary = "查询岗位详细信息接口")
  @RequestMapping(value = "/open_api/base/post/{postId}", method = RequestMethod.GET)
  public JwResponseData<BasePostRes> getInfo(@PathVariable("postId") String postId) {
    BasePost basePost = basePostService.selectBasePostByPostId(postId);
    BasePostRes basePostRes = new BasePostRes();
    BeanUtils.copyProperties(basePost, basePostRes);
    return JwResponseData.success("获取成功", basePostRes);
  }

  /**
   * 新增岗位信息
   */
  @PreAuthorize("hasAuthority('position-manage')")
  @Operation(summary = "新增岗位信息接口")
  @RequestMapping(value = "/open_api/base/post", method = RequestMethod.POST)
  public JwResponseData<BasePostRes> add(@RequestBody @Validated BasePostAddReq req) {
    try {
      BaseUser user = UserHolder.getUser();
      BasePostRes basePostRes = basePostService.insertBasePost(req, user);

      operationHistoryService.insertHistoryOper(user, operTypeAdd, JacksonUtil.objToJson(req));
      return JwResponseData.success("新增成功", basePostRes);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  /**
   * 修改岗位信息
   */
  @PreAuthorize("hasAuthority('position-manage')")
  @Operation(summary = "更新岗位信息接口")
  @RequestMapping(value = "/open_api/base/post", method = RequestMethod.PUT)
  public JwResponseData<BasePostRes> edit(@RequestBody @Validated BasePostUpdateReq req) {
    try {
      BaseUser user = UserHolder.getUser();
      BasePostRes basePostRes = basePostService.updateBasePost(req, user);

      operationHistoryService.insertHistoryOper(user, operTypeUpdate, JacksonUtil.objToJson(req));
      return JwResponseData.success("更新成功", basePostRes);
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }

  /**
   * 删除岗位信息
   */
  @PreAuthorize("hasAuthority('position-manage')")
  @Operation(summary = "删除岗位信息接口")
  @RequestMapping(value = "/open_api/base/post/{postId}", method = RequestMethod.DELETE)
  public JwResponseData<Object> remove(@PathVariable String postId) {
    try {
      BaseUser user = UserHolder.getUser();
      basePostService.deleteBasePostByPostIds(postId, user);

      operationHistoryService.insertHistoryOper(user, operTypeDelete, JacksonUtil.objToJson(postId));
      return JwResponseData.success("删除成功");
    } catch (Exception e) {
      log.error("", e);
      return JwResponseData.error(JwResponseCode.SERVER_ERROR);
    }
  }
}
