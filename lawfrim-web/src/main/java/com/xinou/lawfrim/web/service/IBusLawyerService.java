package com.xinou.lawfrim.web.service;

import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.web.dto.BusLawyerDto;
import com.xinou.lawfrim.web.entity.BusLawyer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinou.lawfrim.web.vo.LawyerVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Wangxin
 * @since 2020-10-16
 */
public interface IBusLawyerService extends IService<BusLawyer> {

    /**
     * 列表
     * @param busLawyer
     * @return
     */
    APIResponse listLawyer(BusLawyerDto busLawyer);

    /**
     * 添加律师
     * @param lawyer 律师信息
     * @return
     */
    APIResponse addBusLawyer(BusLawyerDto lawyer);

    /**
     * 获取律师信息
     * @param lawyer 律师id
     * @return
     */
    APIResponse<LawyerVo>  getBusLawyer(BusLawyerDto lawyer);

    /**
     * 修改律师信息
     * @param lawyer 律师信息
     * @return
     */
    APIResponse updateBusLawyer(BusLawyerDto lawyer);
}
