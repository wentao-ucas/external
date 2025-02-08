package cn.cncc.caos.uaa.model.permission;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BasePermissionRes implements Serializable{
  private String id;
  private String name;
  private String title;
  private String path;
  private String parentName;
  private Integer seq;
  private List<BasePermissionRes> children;
  private String env;
  private Byte notice;
}
