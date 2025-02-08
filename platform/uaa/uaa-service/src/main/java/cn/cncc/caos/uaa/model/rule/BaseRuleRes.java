package cn.cncc.caos.uaa.model.rule;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class BaseRuleRes implements Serializable{
  private String id;
  private String name;
  private String title;
  private String formula;
  private String parentId;
  private Integer seq;
  private List<BaseRuleRes> children;
}
