package cn.cncc.caos.platform.uaa.client.api.pojo;

import lombok.Data;

import java.util.Set;

@Data
public class AssigneeIdAndGroupDTO {
  private Set<Integer> assigneeIds;
  private Set<String> assigneeGroups;
}
