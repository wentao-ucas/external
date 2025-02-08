package cn.cncc.caos.uaa.model.base.post;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BasePostRes {

  
  private String id;

  
  private String postCode;

  
  private String postName;

  
  private Integer postSort;

  
  private Byte status;

  
  private String createUser;

  
  private Date createTime;

  
  private String updateUser;

  
  private Date updateTime;

  
  private String remark;
}
