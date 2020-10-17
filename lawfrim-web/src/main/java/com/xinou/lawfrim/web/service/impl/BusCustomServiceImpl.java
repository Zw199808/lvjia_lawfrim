package com.xinou.lawfrim.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.common.util.Config;
import com.xinou.lawfrim.sso.entity.SYSUser;
import com.xinou.lawfrim.web.dto.BusCustomDto;
import com.xinou.lawfrim.web.entity.BusCustom;
import com.xinou.lawfrim.web.mapper.BusCustomMapper;
import com.xinou.lawfrim.web.service.IBusCustomService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;


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
    private IBusCustomService busCustomService;


    @Override
    public APIResponse listCustom(BusCustomDto custom) {
        return null;
    }

    @Override
    public APIResponse addCustom(BusCustomDto custom) {
        List<BusCustom> customList = busCustomService.list(
                new QueryWrapper<BusCustom>().eq("is_delete", 0)
                                             .eq("account", custom.getAccount()));
        if (customList.size() != 0) {
            return new APIResponse(Config.RE_CODE_USER_EXIST,Config.RE_MSG_USER_EXIST);
        }
        BusCustom busCustom = new BusCustom();
        busCustom.setAccount(custom.getAccount());
        busCustom.setName(custom.getName());
        busCustom.setPassword(busCustom.getPassword());
        boolean res = busCustomService.save(busCustom);
        if (!res){
            throw new RuntimeException("新增客户失败");
        }
        return new APIResponse();
    }

    @Override
    public APIResponse getCustom(BusCustomDto custom) {
        return null;
    }

    @Override
    public APIResponse updateCustom(BusCustomDto custom) {
        return null;
    }
}
