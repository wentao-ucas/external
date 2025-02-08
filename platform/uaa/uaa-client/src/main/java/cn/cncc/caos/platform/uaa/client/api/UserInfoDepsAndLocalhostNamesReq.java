package cn.cncc.caos.platform.uaa.client.api;

import lombok.Data;

import java.util.List;

@Data
public class UserInfoDepsAndLocalhostNamesReq {

    List<Integer> depIdList;

    List<String> localhostNames;
}
