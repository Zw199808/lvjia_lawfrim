package com.xinou.lawfrim.sso.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by xiao_XX on 2017/10/25.
 * 关系表  返回
 */

@Data
@Accessors(chain = true)
public class ResReSYSUserApp extends ReSYSUserApp{

    private String appName;  // 系统名

}