package cn.cncc.caos.uaa.model.rule;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BaseRuleReq {
  @NotBlank(message = "标识不能为空")
  @Schema(description = "权限标志如：file_upload，最大255", type = "String", required = true, example = "file_upload")
  private String name;
  @NotBlank(message = "名称不能为空")
  @Schema(description = "权限名称，最大255", type = "String", required = true, example = "文件上传")
  private String title;
  @NotBlank(message = "规则不能为空")
  @Schema(description = "规则表达式，最大255", type = "String", required = true, example = "(_this)=>{return _this.formItem.processStatusNameList==='创建人提出变更'}")
  private String formula;
  @NotBlank(message = "父id不能为空")
  @Schema(description = "上层id", type = "String", required = true, example = "1")
  private String parentId;
  @Schema(description = "展示顺序", type = "Integer", required = false, example = "1")
  private Integer seq;
}
