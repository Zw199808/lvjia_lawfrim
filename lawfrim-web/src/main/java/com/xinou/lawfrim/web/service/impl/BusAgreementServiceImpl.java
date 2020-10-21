package com.xinou.lawfrim.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.common.util.Config;
import com.xinou.lawfrim.web.dto.BusAgreementDto;
import com.xinou.lawfrim.web.entity.BusAgreement;
import com.xinou.lawfrim.web.mapper.BusAgreementMapper;
import com.xinou.lawfrim.web.service.IBusAgreementService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinou.lawfrim.web.util.upLoadFile;
import com.xinou.lawfrim.web.vo.agreement.AgreementInfoVo;
import com.xinou.lawfrim.web.vo.agreement.AgreementListVo;
import com.xinou.lawfrim.web.vo.agreement.AgreementVo;
import com.xinou.lawfrim.web.vo.custom.CustomNumVo;
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
public class BusAgreementServiceImpl extends ServiceImpl<BusAgreementMapper, BusAgreement> implements IBusAgreementService {

    @Autowired
    private BusAgreementMapper agreementMapper;
    @Override
    public APIResponse<CustomNumVo> getCustomAgreementCount(BusAgreementDto agreement) {
        //根据id查询该对象上传的合同总数
        Integer agreementCount = count(new QueryWrapper<BusAgreement>()
                .eq("is_delete",0)
                .eq("custom_id",agreement.getCustomId()));
        Integer auditCount = count(new QueryWrapper<BusAgreement>()
                .eq("is_delete",0)
                .eq("custom_id",agreement.getCustomId())
                .eq("state",4));
        Integer notAuditCount = count(new QueryWrapper<BusAgreement>()
                .eq("is_delete",0)
                .eq("custom_id",agreement.getCustomId())
                .in("state",2,3));
        CustomNumVo customNumVo = new CustomNumVo();
        customNumVo.setAgreeNum(agreementCount);
        customNumVo.setAuditAgreement(auditCount);
        customNumVo.setNotAuditAgreement(notAuditCount);

        return new APIResponse<>(customNumVo);
    }

    @Override
    public APIResponse<CustomNumVo> getAllAgreementCount() {
        //根据id查询该对象上传的合同总数
        Integer agreementCount = count(new QueryWrapper<BusAgreement>()
                .eq("is_delete",0));
        Integer auditCount = count(new QueryWrapper<BusAgreement>()
                .eq("is_delete",0)
                .eq("state",4));
        Integer notAuditCount = count(new QueryWrapper<BusAgreement>()
                .eq("is_delete",0)
                .in("state",2,3));
        CustomNumVo customNumVo = new CustomNumVo();
        customNumVo.setAgreeNum(agreementCount);
        customNumVo.setAuditAgreement(auditCount);
        customNumVo.setNotAuditAgreement(notAuditCount);

        return new APIResponse<>(customNumVo);
    }

    @Override
    public APIResponse<AgreementVo> downloadAgreement(BusAgreementDto agreementDto) {
        BusAgreement agreement = getById(agreementDto.getId());
        if (agreement == null){
            return new APIResponse<>(Config.RE_DATA_NOT_EXIST_ERROR_CODE,Config.RE_DATA_NOT_EXIST_ERROR_MSG);
        }
        String url = upLoadFile.resourcesCode(agreement.getName());
        AgreementVo agreementVo = new AgreementVo();
        agreementVo.setUrl(url);
        return new APIResponse<>(agreementVo);
    }

    @Override
    public APIResponse<AgreementListVo> listAgreement(BusAgreementDto agreementDto) {
        Page<BusAgreementDto> page = new Page<>(agreementDto.getPageNumber(), agreementDto.getPageSize());
        List<AgreementListVo> list = agreementMapper.getList(page, agreementDto);
        Integer total = agreementMapper.getTotal(agreementDto);
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
    public APIResponse<AgreementInfoVo> getAgreementInfo(BusAgreementDto agreementDto) {
        return null;
    }
}
