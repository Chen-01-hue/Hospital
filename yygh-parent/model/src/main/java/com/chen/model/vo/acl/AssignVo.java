package com.chen.model.vo.acl;

import lombok.Data;

@Data
public class AssignVo {

    private Long roleId;

    private Long[] permissionId;
}
