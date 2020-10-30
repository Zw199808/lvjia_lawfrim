package com.xinou.lawfrim.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.common.util.Config;
import com.xinou.lawfrim.sso.entity.ReSYSUserApp;
import com.xinou.lawfrim.sso.entity.ReSYSUserRole;
import com.xinou.lawfrim.sso.entity.Role;
import com.xinou.lawfrim.sso.entity.SYSUser;
import com.xinou.lawfrim.sso.service.ReUserAppSSOService;
import com.xinou.lawfrim.sso.service.ReUserRoleSSOService;
import com.xinou.lawfrim.sso.service.RoleSSOService;
import com.xinou.lawfrim.sso.service.UserSSOService;
import com.xinou.lawfrim.web.dto.BusLawyerDto;
import com.xinou.lawfrim.web.dto.SortRuleDto;
import com.xinou.lawfrim.web.entity.BusAgreementAudit;
import com.xinou.lawfrim.web.entity.BusCustom;
import com.xinou.lawfrim.web.entity.BusLawyer;
import com.xinou.lawfrim.web.mapper.BusLawyerMapper;
import com.xinou.lawfrim.web.service.IBusAgreementAuditService;
import com.xinou.lawfrim.web.service.IBusCustomService;
import com.xinou.lawfrim.web.service.IBusLawyerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinou.lawfrim.web.util.ExcelUtil2;
import com.xinou.lawfrim.web.vo.UserNumberVo;
import com.xinou.lawfrim.web.vo.lawyer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class BusLawyerServiceImpl extends ServiceImpl<BusLawyerMapper, BusLawyer> implements IBusLawyerService {

    @Autowired
    private  BusLawyerMapper busLawyerMapper;

    @Autowired
    private UserSSOService userSSOService;

    @Autowired
    private ReUserRoleSSOService reUserRoleSSOService;

    @Autowired
    private ReUserAppSSOService reUserAppSSOService;

    @Autowired
    private RoleSSOService roleSSOService;

    @Autowired
    private IBusAgreementAuditService agreementAuditService;

    @Autowired
    private IBusCustomService customService;

    @Override
    public APIResponse<LawyerVo> listLawyer(BusLawyerDto busLawyer) {
        Page<BusLawyerDto> page = new Page<>(busLawyer.getPageNumber(), busLawyer.getPageSize());
        List<LawyerVo> list = busLawyerMapper.getList(page, busLawyer);
        Integer total = busLawyerMapper.getTotal(busLawyer);
        for (LawyerVo lawyerVo :list){
            Integer count = agreementAuditService.count(new QueryWrapper<BusAgreementAudit>()
                                        .eq("lawyer_id",lawyerVo.getId())
                                        .eq("is_delete",0));
            lawyerVo.setAgreeNum(count);
            lawyerVo.setCreateTime(lawyerVo.getGmtCreate());
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
    public APIResponse<endLawyerVo> endListLawyer() {
        List<BusLawyer> list1 = busLawyerMapper.selectList(new QueryWrapper<BusLawyer>()
                                                         .eq("is_delete",0));
        List<endLawyerVo> list = new ArrayList<>();
        for (BusLawyer lawyer : list1){
            endLawyerVo endLawyerVo = new endLawyerVo();
            endLawyerVo.setId(lawyer.getId());
            endLawyerVo.setName(lawyer.getName());
            list.add(endLawyerVo);
        }
        Map<String, Object> map = new HashMap<>(2);
        if (list.size() == 0) {
            map.put("dataList", new ArrayList<>());
            map.put("total", 0);
            return new APIResponse(map);
        }
        map.put("dataList", list);
        return new APIResponse(map);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public APIResponse addBusLawyer(BusLawyerDto lawyer) {
        List<SYSUser> sysUserList = userSSOService.list(
                new QueryWrapper<SYSUser>().eq("is_delete", 0).eq("account", lawyer.getAccount())
        );
        if (sysUserList.size() != 0) {
            return new APIResponse(Config.RE_CODE_USER_EXIST,Config.RE_MSG_USER_EXIST);
        }

        //插入一个后台用户
        SYSUser user = new SYSUser();
        user.setAccount(lawyer.getAccount());
//        user.setPassword(MD5Util.MD5("123456").toLowerCase());
        user.setPassword(lawyer.getPassword());
        user.setRealName(lawyer.getName());
        user.setIsEnable(1);
        boolean res = userSSOService.save(user);
        if (!res) {
            throw new RuntimeException("新增后台用户失败");
        }

        //添加用户
        BusLawyer busLawyer = new BusLawyer();
        busLawyer.setName(lawyer.getName());
        busLawyer.setState(1);
        busLawyer.setSysUserId(user.getId());
        if (lawyer.getRoleId() == 1){
            busLawyer.setAdviser(1);//顾问法务标记
        }
        res = save(busLawyer);
        if (!res) {
            throw new RuntimeException("新增用户失败");
        }

        //角色与用户的关系
        ReSYSUserRole reSYSUserRole = new ReSYSUserRole();
        reSYSUserRole.setUserId(user.getId());
        reSYSUserRole.setRoleId(lawyer.getRoleId());
        res = reUserRoleSSOService.save(reSYSUserRole);
        if (!res) {
            throw new RuntimeException("添加用户角色失败");
        }

        //应用与用户的关系
        ReSYSUserApp reSYSUserApp = new ReSYSUserApp();
        reSYSUserApp.setUserId(user.getId());
        reSYSUserApp.setAppId(Config.ADMIN_APP_ID);
        res = reUserAppSSOService.save(reSYSUserApp);
        if (!res) {
            throw new RuntimeException("添加用户应用失败");
        }
        return new APIResponse();
    }

    @Override
    public APIResponse<LawyerVo>  getBusLawyer(BusLawyerDto lawyer) {
        BusLawyer busLawyer = busLawyerMapper.selectById(lawyer.getId());
        if (busLawyer == null){
            return new APIResponse<>(Config.RE_DATA_NOT_EXIST_ERROR_CODE,Config.RE_DATA_NOT_EXIST_ERROR_MSG);
        }
        SYSUser sysUser = userSSOService.getById(busLawyer.getSysUserId());
        if (busLawyer == null){
            return new APIResponse<>(Config.RE_DATA_NOT_EXIST_ERROR_CODE,Config.RE_DATA_NOT_EXIST_ERROR_MSG);
        }
        ReSYSUserRole reSYSUserRole = reUserRoleSSOService.getOne(new QueryWrapper<ReSYSUserRole>()
                                                                  .eq("user_id",sysUser.getId()));
        if (reSYSUserRole == null){
            return new APIResponse<>(Config.RE_DATA_NOT_EXIST_ERROR_CODE,Config.RE_DATA_NOT_EXIST_ERROR_MSG);
        }
        Role role = roleSSOService.getById(reSYSUserRole.getRoleId());
        if (role == null){
            return new APIResponse<>(Config.RE_DATA_NOT_EXIST_ERROR_CODE,Config.RE_DATA_NOT_EXIST_ERROR_MSG);
        }
        Integer count = agreementAuditService.count(new QueryWrapper<BusAgreementAudit>()
                .eq("lawyer_id",busLawyer.getId())
                .eq("is_delete",0));

        LawyerVo lawyerVo = new LawyerVo();
        lawyerVo.setId(busLawyer.getId());
        lawyerVo.setAccount(sysUser.getAccount());
        lawyerVo.setName(busLawyer.getName());
        lawyerVo.setPassword("******");
//        lawyerVo.setPassword(sysUser.getPassword());
        lawyerVo.setState(busLawyer.getState());
        lawyerVo.setRoleId(reSYSUserRole.getRoleId());
        lawyerVo.setRoleName(role.getName());
        lawyerVo.setCreateTime(busLawyer.getGmtCreate());
        lawyerVo.setAgreeNum(count);
        return new APIResponse<>(lawyerVo);
    }

    @Override
    public APIResponse updateBusLawyer(BusLawyerDto lawyer) {
        BusLawyer busLawyer = getById(lawyer.getId());
        busLawyer.setState(lawyer.getState());
//        busLawyer.setName(lawyer.getName());
        busLawyer.setGmtModified(null);

        // 数据插入
        boolean res = updateById(busLawyer);
        if (!res) {
            throw new RuntimeException("修改律师在线状态失败");
        }

//        //修改sys_user表中密码
//        SYSUser sysUser = userSSOService.getById(busLawyer.getSysUserId());
////        sysUser.setRealName(lawyer.getName());
//        sysUser.setPassword(lawyer.getPassword());
//        res = userSSOService.updateById(sysUser);
//        if (!res) {
//            throw new RuntimeException("修改律师账户失败");
//        }

        // 修改用户角色相关
//        ReSYSUserRole role = reUserRoleSSOService.getOne(
//                new QueryWrapper<ReSYSUserRole>().eq("user_id", busLawyer.getSysUserId())
//        );
//        role.setRoleId(lawyer.getRoleId());
//        role.setGmtModified(null);
//        res = reUserRoleSSOService.updateById(role);
//        if (!res) {
//            throw new RuntimeException("修改律师角色失败");
//        }
        return new APIResponse();

    }

    @Override
    public APIResponse updateBusLawyerPassword(BusLawyerDto lawyer) {
        //修改sys_user表中密码
        SYSUser sysUser = userSSOService.getById(lawyer.getSysUserId());
        if (!sysUser.getPassword().equals(lawyer.getOldPassword())){
            return new APIResponse<>(Config.RE_OLD_PASSWORD_ERROR_CODE,Config.RE_OLD_PASSWORD_ERROR_MSG);
        }
//        sysUser.setRealName(lawyer.getName());
        sysUser.setPassword(lawyer.getPassword());
        boolean res = userSSOService.updateById(sysUser);
        if (!res) {
            throw new RuntimeException("修改律师登录密码失败");
        }
        return new APIResponse();
    }

    @Override
    public APIResponse AdminUpdateBusLawyerPassword(BusLawyerDto lawyer) {
        BusLawyer lawyer1 = getById(lawyer);
        if (lawyer1 == null){
            return new APIResponse<>(Config.RE_DATA_NOT_EXIST_ERROR_CODE,Config.RE_DATA_NOT_EXIST_ERROR_MSG);
        }
        //修改sys_user表中密码
        SYSUser sysUser = userSSOService.getById(lawyer1.getSysUserId());
//        sysUser.setRealName(lawyer.getName());
        sysUser.setPassword(lawyer.getPassword());
        boolean res = userSSOService.updateById(sysUser);
        if (!res) {
            throw new RuntimeException("修改律师登录密码失败");
        }
        return new APIResponse();
    }

    @Override
    public APIResponse<UserNumberVo> statisticUserNumber() {
        //查询用户数量
        Integer customNumber = customService.count(new QueryWrapper<BusCustom>()
                                                  .eq("is_delete",0));
        Integer lawyerNumber = count(new QueryWrapper<BusLawyer>()
                                    .eq("is_delete",0));
        UserNumberVo userNumberVo = new UserNumberVo();
        userNumberVo.setCustomNumber(customNumber);
        userNumberVo.setLawyerNumber(lawyerNumber);
        return new APIResponse<>(userNumberVo);
    }

    @Override
    public APIResponse<AssignLawyerVo> getAssignLawyerList(SortRuleDto sortRuleDto) {
        Page<SortRuleDto> page = new Page<>(sortRuleDto.getPageNumber(), sortRuleDto.getPageSize());
        List<AssignLawyerVo> list = busLawyerMapper.getAssignLawyerList(page, sortRuleDto);
        Integer total = busLawyerMapper.getAssignLawyerTotal(sortRuleDto);
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
    public String AdminExcelLawyer(BusLawyerDto busLawyer) {
        List<LawyerVo> list1 = busLawyerMapper.getExcelList(busLawyer);
        List<ExcelLawyerVo> list = new ArrayList();
        int i = 1;
        for (LawyerVo lawyerVo : list1){
            Integer count = agreementAuditService.count(new QueryWrapper<BusAgreementAudit>()
                    .eq("lawyer_id",lawyerVo.getId())
                    .eq("is_delete",0));

            ExcelLawyerVo excelLawyerVo = new ExcelLawyerVo();
            excelLawyerVo.setAccount(lawyerVo.getAccount());
            excelLawyerVo.setAgreeNum(count);
            excelLawyerVo.setCreateTime(lawyerVo.getGmtCreate());
            excelLawyerVo.setIndex(i++);
            excelLawyerVo.setName(lawyerVo.getName());
            excelLawyerVo.setRoleName(lawyerVo.getRoleName());
            list.add(excelLawyerVo);
        }
        return ExcelUtil2.simplyExcel(LawyerExcel.class,list,"Lawyer");
    }
}
