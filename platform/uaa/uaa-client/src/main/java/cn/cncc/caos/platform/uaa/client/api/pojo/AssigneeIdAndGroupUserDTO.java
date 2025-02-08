package cn.cncc.caos.platform.uaa.client.api.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class AssigneeIdAndGroupUserDTO {
  Map<String, List<BaseUser>> assigneeGroupUserMap;
  Map<Integer, BaseUser> assigneeIdUserMap;
}
