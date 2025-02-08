package cn.cncc.caos.uaa.service;

import cn.cncc.caos.common.core.enums.DataStatus;
import cn.cncc.caos.common.core.utils.TimeUtil;
import cn.cncc.caos.platform.uaa.client.api.pojo.BaseUser;
import cn.cncc.caos.uaa.config.ServerConfigHelper;
import cn.cncc.caos.uaa.db.dao.BasePostMapper;
import cn.cncc.caos.uaa.db.daoex.BasePostMapperEx;
import cn.cncc.caos.uaa.model.base.post.BasePostAddReq;
import cn.cncc.caos.uaa.model.base.post.BasePostRes;
import cn.cncc.caos.uaa.model.base.post.BasePostUpdateReq;
import cn.cncc.caos.uaa.db.pojo.BasePost;
import cn.cncc.caos.uaa.utils.IDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 岗位信息Service业务层处理
 *
 * @date 2023-10-08
 */
@Slf4j
@Service
public class BasePostService {

  @Resource
  private ServerConfigHelper serverConfigHelper;

  @Resource
  private BasePostMapper basePostMapper;

  @Resource
  private BasePostMapperEx basePostMapperEx;


  /**
   * 查询岗位信息
   *
   * @param postId 岗位信息主键
   * @return 岗位信息
   */
  public BasePost selectBasePostByPostId(String postId) {
    return basePostMapper.selectByPrimaryKey(postId);
  }

  /**
   * 查询岗位信息列表
   *
   * @param basePost 岗位信息
   * @return 岗位信息
   */
  public List<BasePost> selectBasePostList(BasePost basePost) {
    return basePostMapperEx.selectBasePostList(basePost);
  }

  /**
   * 新增岗位信息
   *
   * @param req 岗位信息
   * @return 结果
   */
  public BasePostRes insertBasePost(BasePostAddReq req, BaseUser baseUser) {
    BasePost basePost = new BasePost();
    BeanUtils.copyProperties(req, basePost);
    Date currentTime = TimeUtil.getCurrentTime();
    basePost.setCreateTime(currentTime);
    basePost.setUpdateTime(currentTime);
    basePost.setCreateUser(baseUser.getRealName());
    basePost.setUpdateUser(baseUser.getRealName());
    basePost.setId(IDUtil.getStringNextId(serverConfigHelper.getValue("sync.id.prefix")));
    basePostMapper.insertSelective(basePost);
    log.info("insert basePost end postCode={},postName={}", req.getPostCode(), req.getPostName());

    BasePostRes basePostRes = new BasePostRes();
    BeanUtils.copyProperties(basePost, basePostRes);
    return basePostRes;
  }

  /**
   * 修改岗位信息
   *
   * @param basePostUpdateReq 岗位信息
   * @return 结果
   */
  public BasePostRes updateBasePost(BasePostUpdateReq basePostUpdateReq, BaseUser baseUser) {
    BasePost basePost = new BasePost();
    BeanUtils.copyProperties(basePostUpdateReq, basePost);
    basePost.setUpdateUser(baseUser.getRealName());
    basePost.setUpdateTime(TimeUtil.getCurrentTime());

    basePostMapper.updateByPrimaryKeySelective(basePost);
    log.info("update basePost end postCode={},postName={}", basePostUpdateReq.getPostCode(), basePostUpdateReq.getPostName());

    BasePostRes basePostRes = new BasePostRes();
    BeanUtils.copyProperties(basePost, basePostRes);
    return basePostRes;
  }

  /**
   * 删除岗位信息
   *
   * @param postId 需要删除的岗位信息主键
   */
  public void deleteBasePostByPostIds(String postId, BaseUser baseUser) {
    BasePost basePost = new BasePost();
    basePost.setId(postId);
    basePost.setUpdateTime(TimeUtil.getCurrentTime());
    basePost.setUpdateUser(baseUser.getRealName());
    basePost.setStatus(DataStatus.DELETE.index);
    basePostMapper.updateByPrimaryKeySelective(basePost);
    log.info("delete basePost by postId end id:" + postId);

  }

  public Long selectCount(BasePost basePostSearch) {
    return basePostMapperEx.selectBasePostCount(basePostSearch);
  }
}