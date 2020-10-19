package com.xinou.lawfrim.common.enumeration;

import cn.hutool.core.util.ObjectUtil;

/**
 * @author lizhongyuan
 */

public enum TokenTypeEnum {

    /**
     * token类型
     */
    TYPE_SYS(1,"sys"),
    TYPE_WEB(2,"web");

    private Integer type;
    private String desc;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    TokenTypeEnum(Integer type, String desc){
        this.type = type;
        this.desc = desc;

    }

    /**
     * 自己定义一个静态方法,通过type返回枚举常量对象
     * @param type 传入的类型
     * @return 该类型的英文描述简写
     */
    public static String getAbbrByType(Integer type){

        for (TokenTypeEnum questionTypeEnum : values()) {
            if(questionTypeEnum.getType().equals(type)){
                return questionTypeEnum.getDesc();
            }
        }
        return null;
    }

    /**
     * 通过type取枚举
     * @param type 类型
     * @return 枚举
     */
    public static TokenTypeEnum getEnumByValue(Integer type){
        if (ObjectUtil.isEmpty(type)){
            return null;
        }
        for (TokenTypeEnum questionTypeEnum : TokenTypeEnum.values()) {
            if (questionTypeEnum.getType().equals(type)) {
                return questionTypeEnum;
            }
        }
        return null;
    }
}
