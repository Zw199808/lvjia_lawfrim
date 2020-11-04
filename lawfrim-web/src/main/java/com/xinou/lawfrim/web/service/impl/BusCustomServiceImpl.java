package com.xinou.lawfrim.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinou.lawfrim.common.enumeration.TokenTypeEnum;
import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.common.util.Config;
import com.xinou.lawfrim.common.util.MD5Util;
import com.xinou.lawfrim.common.util.TimeChange;
import com.xinou.lawfrim.web.base.JwtModel;
import com.xinou.lawfrim.web.dto.BusAgreementDto;
import com.xinou.lawfrim.web.dto.BusCustomDto;
import com.xinou.lawfrim.web.entity.BusAgreement;
import com.xinou.lawfrim.web.entity.BusCustom;
import com.xinou.lawfrim.web.mapper.BusCustomMapper;
import com.xinou.lawfrim.web.service.IBusAgreementService;
import com.xinou.lawfrim.web.service.IBusCustomService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinou.lawfrim.web.util.ExcelUtil2;
import com.xinou.lawfrim.web.util.JwtUtil;
import com.xinou.lawfrim.web.vo.custom.CustomExcel;
import com.xinou.lawfrim.web.vo.custom.CustomVo;
import com.xinou.lawfrim.web.vo.custom.ExcelCustomVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Wangxin
 * @since 2020-10-16
 */
@Primary
@Service
public class BusCustomServiceImpl extends ServiceImpl<BusCustomMapper, BusCustom> implements IBusCustomService {

    @Autowired
    private IBusAgreementService agreementService;

    @Autowired
    private BusCustomMapper busCustomMapper;

    @Override
    public APIResponse<CustomVo> listCustom(BusCustomDto custom) {
        Page<BusCustomDto> page = new Page<>(custom.getPageNumber(), custom.getPageSize());
        List<BusCustom> list1 = busCustomMapper.getList(page, custom);
        Integer total = busCustomMapper.getTotal(custom);
        List<CustomVo> list = new ArrayList<>();
        for (BusCustom busCustom :list1){
            CustomVo customVo = new CustomVo();
            Integer count = agreementService.count(new QueryWrapper<BusAgreement>()
                    .eq("is_delete",0)
                    .eq("custom_id",busCustom.getId()));

            customVo.setAgreeNum(count);
            customVo.setPassword("******");
            customVo.setId(busCustom.getId());
            customVo.setName(busCustom.getName());
            customVo.setAccount(busCustom.getAccount());
            customVo.setCreateTime(busCustom.getGmtCreate());
            list.add(customVo);
        }
        Map<String, Object> map = new HashMap<>(2);
        if (list.size() == 0) {
            map.put("dataList", new ArrayList<>());
            map.put("total", 0);
            return new APIResponse(map);
        }
        map.put("dataList", list);
        map.put("total", total);
        return new APIResponse(map);
    }

    @Override
    public APIResponse addCustom(BusCustomDto custom) {
        //判断账户、姓名是否为空
        if (custom.getName() == null || ("").equals(custom.getName()) || custom.getAccount() == null || ("").equals(custom.getAccount())) {
            return new APIResponse(Config.RE_CODE_PARAM_ERROR,Config.RE_MSG_PARAM_ERROR);
        }

        List<BusCustom> customList = list(
                new QueryWrapper<BusCustom>().eq("is_delete", 0)
                                             .eq("account", custom.getAccount()));
        if (customList.size() != 0) {
            return new APIResponse(Config.RE_CODE_USER_EXIST,Config.RE_MSG_USER_EXIST);
        }
        BusCustom busCustom = new BusCustom();
        busCustom.setAccount(custom.getAccount());
        busCustom.setName(custom.getName());
        busCustom.setPassword(MD5Util.MD5("123456").toLowerCase());
        boolean res = save(busCustom);
        if (!res){
            throw new RuntimeException("新增客户失败");
        }
        return new APIResponse();
    }

    @Override
    public APIResponse<CustomVo> getCustom(BusCustomDto custom) {
        BusCustom busCustom = getById(custom);
        CustomVo customVo = new CustomVo();
        customVo.setId(busCustom.getId());
        customVo.setAccount(busCustom.getAccount());
        customVo.setCreateTime(busCustom.getGmtCreate());
        customVo.setName(busCustom.getName());
        customVo.setPassword("******");
        Integer count = agreementService.count(new QueryWrapper<BusAgreement>()
                               .eq("is_delete",0)
                               .eq("custom_id",busCustom.getId()));
        customVo.setAgreeNum(count);
        return new APIResponse<>(customVo);
    }

    @Override
    public APIResponse AdminUpdateCustom(BusCustomDto custom) {
        BusCustom busCustom = getById(custom.getId());
        if (custom.getPassword() != null && !("").equals(custom.getPassword())){
            busCustom.setPassword(custom.getPassword());
        }
        if (custom.getName() != null && !("").equals(custom.getName())){
            busCustom.setName(custom.getName());
        }
        busCustom.setGmtModified(null);

        // 数据插入
        boolean res = updateById(busCustom);
        if (!res) {
            throw new RuntimeException("修改客户信息失败");
        }
        return new APIResponse();
    }

    @Override
    public APIResponse updateCustom(BusCustomDto custom) {
        BusCustom busCustom = getById(custom.getId());
        if (!busCustom.getPassword().equals(custom.getOldPassword())){
            return new APIResponse(Config.RE_OLD_PASSWORD_ERROR_CODE,Config.RE_OLD_PASSWORD_ERROR_MSG);
        }
        busCustom.setPassword(custom.getPassword());
        busCustom.setGmtModified(null);

        // 数据插入
        boolean res = updateById(busCustom);
        if (!res) {
            throw new RuntimeException("修改客户密码失败");
        }
        return new APIResponse();
    }

    @Override
    public APIResponse customLogin(BusCustomDto custom) {
        BusCustom busCustom = busCustomMapper.selectOne(new QueryWrapper<BusCustom>().eq("account",custom.getAccount()));
        if(busCustom == null){
            return new APIResponse(Config.RE_ACCOUNT_NOT_EXIST_CODE,Config.RE_ACCOUNT_NOT_EXIST_MSG);
        }
        if (!busCustom.getPassword().equals(custom.getPassword())){
            return new APIResponse(Config.RE_PASSWORD_ERROR_CODE,Config.RE_PASSWORD_ERROR_MSG);
        }
        Map<String,Object> data = new HashMap<>(1);
        return  new APIResponse(data).setToken(
                JwtUtil.getJwtString(
                        JwtModel.builder()
                                .userId(Long.parseLong(busCustom.getId().toString()))
                                .account(busCustom.getAccount())
                                .userName(busCustom.getName())
                                .type(TokenTypeEnum.TYPE_WEB.getType())
                                .build(),
                        "custom",
                        "30d"
                )
        );
    }

    @Override
    public APIResponse<CustomVo> getCustomInfo(BusCustomDto custom) {
        BusCustom busCustom = busCustomMapper.selectById(custom.getId());
        CustomVo customVo = new CustomVo();
        customVo.setAccount(busCustom.getAccount());
        customVo.setName(busCustom.getName());
        customVo.setId(busCustom.getId());
        customVo.setPassword("******");
        customVo.setCreateTime(busCustom.getGmtCreate());
        return new APIResponse<>(customVo);
    }


    @Override
    public APIResponse addAgreement(BusAgreementDto agreement) {
//        System.out.println("时间："+System.currentTimeMillis());
        BusAgreement busAgreement = new BusAgreement();
        busAgreement.setAudit(agreement.getAudit());
        busAgreement.setCustomId(agreement.getCustomId());
//        System.out.println("结束时间："+TimeChange.stringChangeTime(agreement.getEndTime()));
        busAgreement.setEndTime(agreement.getEndTime());
        busAgreement.setName(agreement.getName());
        busAgreement.setRemark(agreement.getRemark());
        busAgreement.setState(1);
        boolean res = agreementService.save(busAgreement);
        if (!res){
            throw new RuntimeException("上传合同失败");
        }
        return new APIResponse();
    }

    @Override
    public String AdminExcelCustom(BusCustomDto custom) {
        List<BusCustom> list1 = busCustomMapper.getExcelList(custom);
        Integer total = busCustomMapper.getTotal(custom);
        List<ExcelCustomVo> list = new ArrayList<>();
        int i=1;
        for (BusCustom busCustom :list1){
            ExcelCustomVo excelCustomVo = new ExcelCustomVo();
            Integer count = agreementService.count(new QueryWrapper<BusAgreement>()
                    .eq("is_delete",0)
                    .eq("custom_id",busCustom.getId()));
            excelCustomVo.setAgreeNum(count);
            excelCustomVo.setIndex(i++);
            excelCustomVo.setName(busCustom.getName());
            excelCustomVo.setAccount(busCustom.getAccount());
            excelCustomVo.setCreateTime(TimeChange.timeChangeString(busCustom.getGmtCreate()));
            list.add(excelCustomVo);
        }
        return ExcelUtil2.simplyExcel(CustomExcel.class,list,"Custom");
    }
}
