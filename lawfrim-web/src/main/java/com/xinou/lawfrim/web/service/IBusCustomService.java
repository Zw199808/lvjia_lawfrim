package com.xinou.lawfrim.web.service;

import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.web.dto.BusAgreementDto;
import com.xinou.lawfrim.web.dto.BusCustomDto;
import com.xinou.lawfrim.web.entity.BusCustom;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinou.lawfrim.web.vo.custom.CustomNumVo;
import com.xinou.lawfrim.web.vo.custom.CustomVo;

import java.util.HashMap;
import java.util.Map;

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
    APIResponse<CustomVo> listCustom(BusCustomDto custom);

    /**
     * 添加客户
     * @param custom 客户信息
     * @return
     */
    APIResponse addCustom(BusCustomDto custom);

    /**
     * 获取客户信息
     * @param custom 客户id
     * @return
     */
    APIResponse<CustomVo> getCustom(BusCustomDto custom);

    /**
     * 修改客户信息
     * @param custom 客户信息
     * @return
     */
    APIResponse updateCustom(BusCustomDto custom);


    /**
     * 登录
     * @param custom 客户信息
     * @return
     */
    APIResponse customLogin(BusCustomDto custom);

    /**
     * 获取用户信息
     * @param custom 客户信息
     * @return
     */
    APIResponse<CustomVo> getCustomInfo(BusCustomDto custom);


    /**
     * 上传合同
     * @param agreement 客户信息
     * @return
     */
    APIResponse addAgreement(BusAgreementDto agreement);

}
