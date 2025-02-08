package cn.cncc.caos.uaa.model.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BasePermissionReq {
  @NotBlank(message = "标识不能为空")
  @Schema(description = "权限标志如：file_upload，最大255", type = "String", required = true, example = "file_upload")
  private String name;
  @NotBlank(message = "名称不能为空")
  @Schema(description = "权限名称，最大255", type = "String", required = true, example = "文件上传")
  private String title;
  @NotBlank(message = "路径不能为空")
  @Schema(description = "路径，最大255", type = "String", required = true, example = "/oss/file/upload")
  private String path;
  @NotBlank(message = "父name不能为空")
  @Schema(description = "上层name", type = "String", required = true, example = "1")
  private String parentName;
  @Schema(description = "展示顺序", type = "Integer", example = "1")
  private Integer seq;
  @NotBlank(message = "环境标识为空")
  @Schema(description = "环境标识：office, npc, all", type = "String", required = true, example = "all")
  private String env;
//  @NotBlank(message = "邮件短信标识不能为空")
  @Schema(description = "邮件短信标识：1-涉及, 0-不涉及", type = "Byte", required = true, example = "1")
  private Byte notice;
}
