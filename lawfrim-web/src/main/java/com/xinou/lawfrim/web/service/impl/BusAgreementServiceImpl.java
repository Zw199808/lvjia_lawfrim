package com.xinou.lawfrim.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.common.util.Config;
import com.xinou.lawfrim.web.dto.BusAgreementDto;
import com.xinou.lawfrim.web.dto.BusCustomDto;
import com.xinou.lawfrim.web.entity.BusAgreement;
import com.xinou.lawfrim.web.mapper.BusAgreementMapper;
import com.xinou.lawfrim.web.service.IBusAgreementService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinou.lawfrim.web.util.upLoadFile;
import com.xinou.lawfrim.web.vo.agreement.AgreementVo;
import com.xinou.lawfrim.web.vo.custom.CustomNumVo;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

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
}
