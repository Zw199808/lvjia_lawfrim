package com.xinou.lawfrim.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.common.util.Config;
import com.xinou.lawfrim.common.util.TimeChange;
import com.xinou.lawfrim.web.dto.BusAgreementDto;
import com.xinou.lawfrim.web.dto.DownloadAgreementDto;
import com.xinou.lawfrim.web.entity.*;
import com.xinou.lawfrim.web.mapper.*;
import com.xinou.lawfrim.web.service.IBusAgreementService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinou.lawfrim.web.util.ExcelUtil2;
import com.xinou.lawfrim.web.util.upLoadFile;
import com.xinou.lawfrim.web.vo.agreement.*;
import com.xinou.lawfrim.web.vo.custom.CustomNumVo;
import com.xinou.lawfrim.web.vo.lawyer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    @Autowired
    private BusAgreementAuditMapper agreementAuditMapper;

    @Autowired
    private BusCustomMapper customMapper;

    @Autowired
    private BusChangeRecordMapper changeRecordMapper;

    @Autowired
    private BusLawyerMapper lawyerMapper;

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
                .in("state",1,2,3,5));
        CustomNumVo customNumVo = new CustomNumVo();
        customNumVo.setAgreeNum(agreementCount);
        customNumVo.setAuditAgreement(auditCount);
        customNumVo.setNotAuditAgreement(notAuditCount);

        return new APIResponse<>(customNumVo);
    }

    @Override
    public APIResponse<AgreementTypeVo> getAgreementTypeStatistic(BusAgreementDto agreement) {
        List<AgreementTypeVo> list = agreementMapper.getAgreementTypeStatistic(agreement);
        Map<String, Object> map = new HashMap<>(2);
        if (list.size() == 0) {
            map.put("dataList", new ArrayList<>());
            return new APIResponse(map);
        }
        map.put("dataList", list);
        return new APIResponse(map);
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
                .in("state",1,2,3,5));
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
    public APIResponse<AgreementListVo> finishAgreement(BusAgreementDto agreementDto) {
        Page<BusAgreementDto> page = new Page<>(agreementDto.getPageNumber(), agreementDto.getPageSize());
        List<AgreementListVo> list = agreementMapper.getList(page, agreementDto);
        Integer total = agreementMapper.getTotal(agreementDto);
        for (AgreementListVo agreementListVo :list){
              BusAgreementAudit agreementAudit = agreementAuditMapper.selectOne(new QueryWrapper<BusAgreementAudit>()
                                                                            .eq("agreement_id",agreementListVo.getAgreeId())
                                                                            .eq("is_delete",0));
              if (agreementAudit == null){
                  return new APIResponse<>(Config.RE_DATA_NOT_EXIST_ERROR_CODE,Config.RE_DATA_NOT_EXIST_ERROR_MSG);
              }
              agreementListVo.setFirstAgreeName(agreementAudit.getFirstAgreementName());
              agreementListVo.setEndAgreeName(agreementAudit.getFirstAgreementName());
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
    public APIResponse<AgreementInfoVo> getAgreementInfo(BusAgreementDto agreementDto) {
        BusAgreement agreement = getById(agreementDto.getId());
        if (agreement == null){
            return new APIResponse<>(Config.RE_DATA_NOT_EXIST_ERROR_CODE,Config.RE_DATA_NOT_EXIST_ERROR_MSG);
        }
        //查询合同上传人姓名
        BusCustom busCustom = customMapper.selectById(agreement.getCustomId());
        if (agreement == null){
            return new APIResponse<>(Config.RE_DATA_NOT_EXIST_ERROR_CODE,Config.RE_DATA_NOT_EXIST_ERROR_MSG);
        }

        AgreementInfoVo agreementInfoVo = new AgreementInfoVo();
        agreementInfoVo.setAgreeId(agreement.getId());
        agreementInfoVo.setAgreeName(agreement.getName());
        agreementInfoVo.setCustomName(busCustom.getName());
        agreementInfoVo.setAgreeAudit(agreement.getAudit());
        agreementInfoVo.setApplyTime(agreement.getGmtCreate());//上传时间
        agreementInfoVo.setEndTime(agreement.getEndTime());//截止时间
        agreementInfoVo.setState(agreement.getState());//合同状态
        if (agreement.getState() != 1){
            //查询合同初审律师   初审接受时间
            BusAgreementAudit agreementAudit = agreementAuditMapper.selectOne(new QueryWrapper<BusAgreementAudit>()
                                                                             .eq("is_delete",0)
                                                                              .eq("agreement_id",agreement.getId()));
            if (agreementAudit == null){
                return new APIResponse<>(Config.RE_DATA_NOT_EXIST_ERROR_CODE,Config.RE_DATA_NOT_EXIST_ERROR_MSG);
            }
            //所有转移记录
            List<BusChangeRecord> changeRecordList = changeRecordMapper.selectList(new QueryWrapper<BusChangeRecord>()
                                                                       .eq("is_delete",0)
                                                                       .eq("agreement_audit_id",agreementAudit.getId())
                                                                       .in("type",2,3));
            //获取初审律师姓名
            if (agreement.getState() != 2){
                BusLawyer lawyer = lawyerMapper.selectById(agreementAudit.getLawyerId());
                agreementInfoVo.setFirstLawyerName(lawyer.getName());//初审律师姓名
            }
            //审批表id
            agreementInfoVo.setAgreementAuditId(agreementAudit.getId());
            agreementInfoVo.setAgreeType(agreementAudit.getType());//合同类型---回复后才会有
            //初审接受时间
            agreementInfoVo.setGmtCreate(agreementAudit.getGmtCreate());
            //初审回复时间
            agreementInfoVo.setFirstAuditTime(agreementAudit.getFirstAuditTime());
            //等待复审 或完成
            if (agreement.getState() == 3 || agreement.getState() == 4 ){
                BusLawyer lawyer1 = lawyerMapper.selectById(agreementAudit.getEndLawyerId());
                agreementInfoVo.setEndLawyerName(lawyer1.getName());//复审律师姓名
            }
            //复审回复时间
            if (agreement.getState() == 4){
                //复审分数
                agreementInfoVo.setScore(String.format("%.2f",agreementAudit.getScore()));
                agreementInfoVo.setGmtModified(agreementAudit.getGmtModified());//审核完成--修改时间为复审时间
            }else{
                agreementInfoVo.setGmtModified(null);//未审核完成复审时间为空
            }
//            if (changeRecord.getType() == 2){//转移，查询转移律师
//                BusLawyer lawyer2 = lawyerMapper.selectById(changeRecord.getOldOrAssignLawyerId());
//                agreementInfoVo.setChangeLawyerName(lawyer2.getName());
//                agreementInfoVo.setChangeTime(TimeChange.timeChangeString(changeRecord.getGmtModified()));
//            }
            List<LawyerChangeVo> list = new ArrayList<>();
            for (BusChangeRecord changeRecord : changeRecordList){
                BusLawyer lawyer2 = lawyerMapper.selectById(changeRecord.getOldOrAssignLawyerId());
                LawyerChangeVo changeVo = new LawyerChangeVo();
                changeVo.setChangeLawyerName(lawyer2.getName());
                changeVo.setChangeTime(changeRecord.getGmtCreate());
                changeVo.setType(changeRecord.getType());
                list.add(changeVo);
            }
            agreementInfoVo.setLawyerChangeVoList(list);
        }
        return new APIResponse<>(agreementInfoVo);
    }

    @Override
    public APIResponse<DownloadAgreementVo> downloadTwoAgreement(DownloadAgreementDto agreement) {
        String firstURL = upLoadFile.resourcesCode(agreement.getFirstAgreeName());
        String secondURL = upLoadFile.resourcesCode(agreement.getEndAgreeName());
        DownloadAgreementVo downloadAgreementVo = new DownloadAgreementVo();
        downloadAgreementVo.setFirstURL(firstURL);
        downloadAgreementVo.setSecondURL(secondURL);
        return new APIResponse<>(downloadAgreementVo);
    }

    @Override
    public APIResponse<LawyerAgreementListVo> AllAgreementList(BusAgreementDto agreement) {

        Page<BusAgreementDto> page = new Page<>(agreement.getPageNumber(), agreement.getPageSize());
        List<LawyerAgreementListVo> list = changeRecordMapper.getList(page, agreement);
        Integer total = changeRecordMapper.getTotal(agreement);
        for (LawyerAgreementListVo lawyerAgreementListVo : list){
            BusLawyer lawyer = lawyerMapper.selectById(lawyerAgreementListVo.getFirstLawyerId());
            if (lawyer == null){
                return new APIResponse<>(Config.RE_DATA_NOT_EXIST_ERROR_CODE,Config.RE_DATA_NOT_EXIST_ERROR_MSG);
            }
            lawyerAgreementListVo.setFirstLawyerName(lawyer.getName());
            BusLawyer lawyer1 = lawyerMapper.selectById(lawyerAgreementListVo.getFirstLawyerId());
            if (lawyer1 == null){
                return new APIResponse<>(Config.RE_DATA_NOT_EXIST_ERROR_CODE,Config.RE_DATA_NOT_EXIST_ERROR_MSG);
            }
            lawyerAgreementListVo.setEndLawyerName(lawyer1.getName());
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
    public APIResponse<LawyerAgreementListVo> getAllAgreementList(BusAgreementDto agreement) {
        Page<BusAgreementDto> page = new Page<>(agreement.getPageNumber(), agreement.getPageSize());
        List<LawyerAgreementListVo> list = changeRecordMapper.getAllList(page, agreement);
        Integer total = changeRecordMapper.getAllTotal(agreement);
        //领取过得合同要查询审批信息
        for (LawyerAgreementListVo lawyerAgreementListVo :list){
            if (lawyerAgreementListVo.getAgreeState() != 1){
                //去查询审批表信息
                BusAgreementAudit agreementAudit = agreementAuditMapper.selectOne(new QueryWrapper<BusAgreementAudit>()
                                                                       .eq("is_delete",0)
                                                                       .eq("agreement_id",lawyerAgreementListVo.getAgreeId()));
            lawyerAgreementListVo.setGetState(agreementAudit.getState());
            lawyerAgreementListVo.setAgreementAuditId(agreementAudit.getId());
            lawyerAgreementListVo.setEndLawyerId(agreementAudit.getEndLawyerId());
            lawyerAgreementListVo.setFirstLawyerId(agreementAudit.getLawyerId());
            }
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
    public APIResponse<AgreementNumVo> getAgreementNumber() {
        //合同数量统计
        List<AgreementNumVo> list = agreementMapper.getAgreementNumber();
        //遍历查询每个月加每月之前总数
        for (AgreementNumVo agreementNumVo1 : list){
            BusAgreementDto agreementDto = new BusAgreementDto();
            agreementDto.setEndTime(agreementNumVo1.getGroupYear());
            Integer num = agreementMapper.getAgreementNumByTime(agreementDto);
            agreementNumVo1.setTotalNumber(num);
        }
        Map<String, Object> map = new HashMap<>(2);
        if (list.size() == 0) {
            map.put("dataList", new ArrayList<>());
            return new APIResponse(map);
        }
        map.put("dataList", list);
        return new APIResponse(map);
    }


    @Override
    public String LawyerExcelAgreement(BusAgreementDto agreement) {
        if(agreement.getTag() != 1){
            //获取当前时间
            getTime(agreement);
        }
        List<LawyerAgreementListVo> list1 = changeRecordMapper.getLawyerExcelList(agreement);
        List<LawyerAgreementVo> list = new ArrayList();
        int i = 1;
        for (LawyerAgreementListVo lawyerAgreementListVo : list1){
            //
            LawyerAgreementVo lawyerAgreementVo = new LawyerAgreementVo();
            lawyerAgreementVo.setIndex(i++);
            lawyerAgreementVo.setAgreeName(lawyerAgreementListVo.getAgreeName());
            lawyerAgreementVo.setCustomName(lawyerAgreementListVo.getCustomName());
            lawyerAgreementVo.setEndTime(lawyerAgreementListVo.getEndTime().substring(0,11));
            lawyerAgreementVo.setEndTime2(lawyerAgreementListVo.getEndTime().substring(11,19));
            lawyerAgreementVo.setRemark("");
            lawyerAgreementVo.setFirstLawyerName(lawyerAgreementListVo.getFirstLawyerName());
            lawyerAgreementVo.setEndLawyerName(lawyerAgreementListVo.getEndLawyerName());
//            if (lawyer != ""){
//                lawyerAgreementVo.setFirstLawyerName(lawyer.getName());
//            }else {
//                lawyerAgreementVo.setFirstLawyerName("");
//            }
//            if (lawyer1 != null){
//                lawyerAgreementVo.setEndLawyerName(lawyer1.getName());
//            }else {
//                lawyerAgreementVo.setEndLawyerName("");
//            }
            //合同初审复审完成情况
            if (lawyerAgreementListVo.getAgreeState()==1){
                lawyerAgreementVo.setFirst("");
                lawyerAgreementVo.setSecond("");
            }else if (lawyerAgreementListVo.getAgreeState()==2){
                lawyerAgreementVo.setFirst("");
                lawyerAgreementVo.setSecond("");
            }else if (lawyerAgreementListVo.getAgreeState()==3){
                lawyerAgreementVo.setFirst("√");
                lawyerAgreementVo.setSecond("");
            }else if (lawyerAgreementListVo.getAgreeState()==4){
                lawyerAgreementVo.setFirst("√");
                lawyerAgreementVo.setSecond("√");
            }
            list.add(lawyerAgreementVo);
        }
        return ExcelUtil2.simplyExcel(LawyerAgreementExcel.class,list,"Agreement");
    }

    @Override
    public String AdminExcelAgreement(BusAgreementDto agreement) {
        getTime(agreement);
        List<LawyerAgreementListVo> list1 = changeRecordMapper.getAdminExcelList(agreement);
        List<LawyerAgreementExcelv2> list = new ArrayList();
        List<LawyerAgreementExcelv1> historyList = new ArrayList();
        int i = 1;
        int m = 1;
        for (LawyerAgreementListVo lawyerAgreementListVo : list1){
            LawyerAgreementExcelv2 lawyerAgreementVo = new LawyerAgreementExcelv2();
            lawyerAgreementVo.setAgreeName(lawyerAgreementListVo.getAgreeName());
            lawyerAgreementVo.setCustomName(lawyerAgreementListVo.getCustomName());
            lawyerAgreementVo.setEndTime(lawyerAgreementListVo.getEndTime().substring(0,11));
            lawyerAgreementVo.setEndTime2(lawyerAgreementListVo.getEndTime().substring(11,19));
            lawyerAgreementVo.setRemark("");
            if (lawyerAgreementListVo.getAgreeState()==1){
                lawyerAgreementVo.setFirst("");
                lawyerAgreementVo.setSecond("");
            }
            if (lawyerAgreementListVo.getAgreeState() != 1){
                lawyerAgreementVo.setFirstLawyerName(lawyerAgreementListVo.getFirstLawyerName());
                lawyerAgreementVo.setEndLawyerName(lawyerAgreementListVo.getEndLawyerName());
                //合同初审复审完成情况
                if (lawyerAgreementListVo.getAgreeState()==2){
                    lawyerAgreementVo.setFirst("");
                    lawyerAgreementVo.setSecond("");
                }else if (lawyerAgreementListVo.getAgreeState()==3){
                    lawyerAgreementVo.setFirst("√");
                    lawyerAgreementVo.setSecond("");
                }else if (lawyerAgreementListVo.getAgreeState()==4){
                    lawyerAgreementVo.setFirst("√");
                    lawyerAgreementVo.setSecond("√");
                }
            }
            LawyerAgreementExcelv1 lawyerAgreementVo1 = new LawyerAgreementExcelv1(lawyerAgreementVo);
            if (agreement.getGmtTime().equals(lawyerAgreementListVo.getUpTime())){
                lawyerAgreementVo.setIndex(i++);
                list.add(lawyerAgreementVo);//今日合同列表
            }else{
                lawyerAgreementVo1.setIndex(m++);
                historyList.add(lawyerAgreementVo1);//历史合同列表
            }

        }
        return ExcelUtil2.twoSecondExcel(LawyerAgreementExcelv1.class,LawyerAgreementExcelv2.class,historyList,list,"Agreement");
    }

    private void getTime(BusAgreementDto agreement) {
        LocalDateTime now = LocalDateTime.now();
        String nowString = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        agreement.setGmtTime(nowString);
//        agreement.setGmtTime(TimeChange.stringChangeTime(nowString));
    }

}
