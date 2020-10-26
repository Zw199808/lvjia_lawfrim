package com.xinou.lawfrim.web.util;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.xinou.lawfrim.common.util.Config;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @author xiao_XX
 * @date 2020/08/13.
 * Description:
 */
public class ExcelUtil2 {

    public static String excelUse(HttpServletResponse response, Class<? extends BaseRowModel> tClass, List data, String fileName){

        ExcelWriter writer = null;
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            //添加响应头信息
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + System.currentTimeMillis() + ".xls");
            response.setContentType("application/msexcel;charset=UTF-8");//设置类型
            response.setHeader("Pragma", "No-cache");//设置头
            response.setHeader("Cache-Control", "no-cache");//设置头
            response.setDateHeader("Expires", 0);//设置日期头

            //实例化 ExcelWriter
            writer = new ExcelWriter(outputStream, ExcelTypeEnum.XLS, true);

            //实例化表单
            Sheet sheet = new Sheet(1, 0, tClass);
            sheet.setSheetName(fileName);

            //获取数据（这个根据你实际情况获取到的数据）
            //输出
            writer.write(data, sheet);
            writer.finish();
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.getOutputStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }
    public static String simplyExcel(Class tClass,List data,String fileName){
        String excelPath = Config.EXCEL_PATH;
         fileName =  fileName + "-" + System.nanoTime() + ".xlsx";

        OutputStream out = null;
        ExcelWriter excelWriter = null;
        try {
            out = new FileOutputStream(excelPath+fileName);
            excelWriter = new ExcelWriter(out, ExcelTypeEnum.XLSX,true);
            Sheet sheet1 = new Sheet(1,0,tClass);
            sheet1.setSheetName("sheet1");
            excelWriter.write0(data,sheet1);
            excelWriter.finish();
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }



//        ExcelWriter excelWriter = EasyExcel.write(fileName, tClass).file(out).build();
//        WriteSheet writeSheet = EasyExcel.writerSheet(fileName).build();
//        excelWriter.write(data, writeSheet);
//        excelWriter.finish();
//        try {
//            out.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        return fileName;
    }

    public static void main(String[] args) {
//        Long userId = 9L;
//        String s =JwtUtil.getJwtString(
//                JwtModel.builder()
//                        .userId(userId)
//                        .account(userId.toString())
//                        .userName("王鹏贺")
//                        .type(TokenTypeEnum.TYPE_WEB.getType())
//                        .build(),
//                "monopoly",
//                "30d"
//        );
//
//        System.out.println(s);

    }

}
