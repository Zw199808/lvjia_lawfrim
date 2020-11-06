package com.xinou.lawfrim.web.service.impl;

import com.xinou.lawfrim.web.entity.BusAgreement;
import com.xinou.lawfrim.web.service.IBusAgreementAuditService;
import com.xinou.lawfrim.web.service.IBusAgreementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By 信鸥科技
 * Created by WangXin on 2020/11/06.
 * 注意：本内容仅限于信鸥科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 * Description:
 */
@Component
public class ScoreAutoBean extends Thread {


    @Autowired
    private IBusAgreementAuditService agreementAuditService;

    //新建全局变量,订单截止时间记录将存放在这里
    static List<BusAgreement> agreeList = new ArrayList<>();

    //服务启动自动加载
    @PostConstruct
    public void init() {


        //待评分列表
        agreeList = agreementAuditService.toBeScoreList();
        //开始线程
        this.start();
    }

    //重写run方法,this.start后将运行此方法
    @Override
    public void run(){


        for (int i = 0; i < 1; i--) {
            try {
                //每秒运行
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //修改评分和合同状态
            agreeList = agreementAuditService.toBeScoreAuto(agreeList);

        }
    }

    //list中添加数据,回复合同时调用
    public static void addToBeScoreList (BusAgreement agreement){
        agreeList.add(agreement);
    }

    //list中添加数据,复审评分合同时调用
    public static void removeToBeScoreList (BusAgreement agreement){
        int size = agreeList.size();
        for (int index = 0; index < size; index++) {
            BusAgreement agreement1 = agreeList.get(index);
                if (agreement1.getId() == agreement.getId()) {
                    agreeList.remove(index);
                    index--;
                    size--;
                }
        }
    }

}
