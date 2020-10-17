package com.xinou.lawfrim.web.service;

import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.web.dto.BusCustomDto;
import com.xinou.lawfrim.web.entity.BusCustom;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Wangxin
 * @since 2020-10-16
 */
public interface IBusCustomService extends IService<BusCustom> {
    /**
     * 列表
     * @param custom
     * @return
     */
    APIResponse listCustom(BusCustomDto custom);

    /**
     * 添加律师
     * @param custom 客户信息
     * @return
     */
    APIResponse addCustom(BusCustomDto custom);

    /**
     * 获取律师信息
     * @param custom 客户id
     * @return
     */
    APIResponse getCustom(BusCustomDto custom);

    /**
     * 修改律师信息
     * @param custom 客户信息
     * @return
     */
    APIResponse updateCustom(BusCustomDto custom);

}
