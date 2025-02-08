package cn.cncc.caos.platform.uaa.client.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoleNameListReq {

	private String roleNameListInput;

	public RoleNameListReq(String roleNameListInput) {
		this.roleNameListInput = roleNameListInput;
	}
}
