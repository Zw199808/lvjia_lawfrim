package com.xinou.lawfrim.web.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
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
        String prefix = fileName + "-" + System.nanoTime();
        String suffix = ".xlsx";
        fileName = prefix + suffix;
        OutputStream out = null;
        ExcelWriter excelWriter = null;
        File toFile = null ;

        try {
            toFile = Files.createTempFile(prefix, suffix).toFile();
            out = new FileOutputStream(toFile);
            excelWriter = new ExcelWriter(out, ExcelTypeEnum.XLSX,true);
            Sheet sheet1 = new Sheet(1,0,tClass);
            sheet1.setSheetName("sheet1");
            excelWriter.write0(data,sheet1);
            // 上传文件到七牛
            upLoadFile.uploadFileQNUrlFile(toFile);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }
            if (out != null) {
                try {
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (toFile != null) {
                toFile.deleteOnExit();
            }
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

    /**
     * 使用table去写入
     * <p>1. 创建excel对应的实体对象 参照
     * <p>2. 然后写入table即可
     */
    public static String twoSecondExcel(Class tClass1,Class tClass,List data2,List data,String fileName){

        String prefix = fileName + "-" + System.nanoTime();
        String suffix = ".xlsx";
        fileName = prefix + suffix;
        File toFile = null;

        // 这里直接写多个table的案例了，如果只有一个 也可以直一行代码搞定，参照其他案例
        // 这里 需要指定写用哪个class去写
        ExcelWriter excelWriter = null;
        try {
            toFile = Files.createTempFile(prefix, suffix).toFile();
            excelWriter = EasyExcel.write(toFile).build();
            // 把sheet设置为不需要头 不然会输出sheet的头 这样看起来第一个table 就有2个头了
            WriteSheet writeSheet = EasyExcel.writerSheet("sheet1").needHead(Boolean.FALSE).build();
            // 这里必须指定需要头，table 会继承sheet的配置，sheet配置了不需要，table 默认也是不需要
            WriteTable writeTable0 = EasyExcel.writerTable(0).needHead(Boolean.TRUE).head(tClass).build();
            WriteTable writeTable1 = EasyExcel.writerTable(1).needHead(Boolean.TRUE).head(tClass1).build();
//            head(tClass);
            // 第一次写入会创建头
            excelWriter.write(data2, writeSheet, writeTable0);
            // 第二次写如也会创建头，然后在第一次的后面写入数据
            excelWriter.write(data, writeSheet, writeTable1);
            // 上传文件到七牛
            upLoadFile.uploadFileQNUrlFile(toFile);

        }catch (IOException ignored) {

        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
            if(toFile != null) {
                toFile.deleteOnExit();
            }
        }
        return fileName;
    }

    private static List<List<String>> head(Class tClass) {
        List<List<String>> list = new ArrayList<List<String>>();
        List<String> head0 = new ArrayList<String>();
        head0.add("历史完成合同" + tClass);
        List<String> head1 = new ArrayList<String>();
        head1.add("今日完成合同" + tClass);
        list.add(head0);
        list.add(head1);
        return list;
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
