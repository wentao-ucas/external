package cn.cncc.caos.common.core.enums;

public enum ModuleEnum {

  PROCESS_MANAGE("process-manage", "流程平台"),
  API_SERVICE("api-service", "接口服务"),
  DATA_AUTH("data-auth", "用户管理服务"),
  DATA_MANAGE("data-manage", "数据管理服务"),
  DOC_LIB("doc-lib", "文档管理服务"),
  MAIL_SERVICE("mail-service", "邮件服务"),
  OPERATION_ASSIST("operation-assist", "值班辅助系统"),
  OSS_SERVICE("oss-service", "文件服务"),
  PROCESS_ASSIST("process-assist", "流程小助手"),
  JOB_DISTRIBUTE("job-distribute", "作业平台"),
  NODE_MANAGE("node-manage", "分区管理"),
  SCRIPT_LIB("script-lib", "脚本库"),
  TASK_MANAGE("task-manage", "任务协作平台"),
  ACTIVITI_MANAGE("activiti-manage", "流程服务平台");

  public final String name;
  public final String desc;

  ModuleEnum(String name, String desc){
    this.name = name;
    this.desc = desc;
  }

  public static ModuleEnum typeOf(String name) {
    for (ModuleEnum anEnum : ModuleEnum.values()) {
      if (anEnum.name.equals(name)) return anEnum;
    }
    return null;
  }


}
