package com.xinou.lawfrim.web.service.impl;

import com.xinou.lawfrim.web.entity.BusAgreementAudit;
import com.xinou.lawfrim.web.mapper.BusAgreementAuditMapper;
import com.xinou.lawfrim.web.service.IBusAgreementAuditService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
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
public class BusAgreementAuditServiceImpl extends ServiceImpl<BusAgreementAuditMapper, BusAgreementAudit> implements IBusAgreementAuditService {

}
