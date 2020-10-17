package com.xinou.lawfrim.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.common.util.Config;
import com.xinou.lawfrim.common.util.TimeChange;
import com.xinou.lawfrim.sso.entity.SYSUser;
import com.xinou.lawfrim.web.dto.BusCustomDto;
import com.xinou.lawfrim.web.dto.BusLawyerDto;
import com.xinou.lawfrim.web.entity.BusAgreement;
import com.xinou.lawfrim.web.entity.BusAgreementAudit;
import com.xinou.lawfrim.web.entity.BusCustom;
import com.xinou.lawfrim.web.entity.BusLawyer;
import com.xinou.lawfrim.web.mapper.BusCustomMapper;
import com.xinou.lawfrim.web.service.IBusAgreementService;
import com.xinou.lawfrim.web.service.IBusCustomService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinou.lawfrim.web.vo.CustomVo;
import com.xinou.lawfrim.web.vo.LawyerVo;
import io.swagger.models.auth.In;
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
            String time = TimeChange.timeChangeStringyMDHM(busCustom.getGmtCreate());

            customVo.setAgreeNum(count);
            customVo.setPassword(busCustom.getPassword());
            customVo.setId(busCustom.getId());
            customVo.setName(busCustom.getName());
            customVo.setAccount(busCustom.getAccount());
            customVo.setCreateTime(time);
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
        List<BusCustom> customList = list(
                new QueryWrapper<BusCustom>().eq("is_delete", 0)
                                             .eq("account", custom.getAccount()));
        if (customList.size() != 0) {
            return new APIResponse(Config.RE_CODE_USER_EXIST,Config.RE_MSG_USER_EXIST);
        }
        BusCustom busCustom = new BusCustom();
        busCustom.setAccount(custom.getAccount());
        busCustom.setName(custom.getName());
        busCustom.setPassword(custom.getPassword());
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
        customVo.setCreateTime(TimeChange.timeChangeStringyMDHM(busCustom.getGmtCreate()));
        customVo.setName(busCustom.getName());
        customVo.setPassword(busCustom.getPassword());
        Integer count = agreementService.count(new QueryWrapper<BusAgreement>()
                               .eq("is_delete",0)
                               .eq("custom_id",busCustom.getId()));
        customVo.setAgreeNum(count);
        return new APIResponse<>(customVo);
    }

    @Override
    public APIResponse updateCustom(BusCustomDto custom) {
        BusCustom busCustom = getById(custom.getId());
        busCustom.setPassword(custom.getPassword());
        busCustom.setName(custom.getName());
        busCustom.setGmtModified(null);

        // 数据插入
        boolean res = updateById(busCustom);
        if (!res) {
            throw new RuntimeException("修改客户信息失败");
        }
        return new APIResponse();
    }
}
