//package com.xinou.lawfrim.common.util;
//
///**
// * All rights Reserved, Designed By 信鸥科技
// * project : xinouservice
// * Created by zhangbo on 17/4/12.
// * 注意：本内容仅限于信鸥科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
// * Description:
// */
//
//import org.apache.poi.hssf.usermodel.*;
//import org.apache.poi.hssf.util.HSSFColor;
//
//import java.io.IOException;
//import java.io.OutputStream;
//import java.lang.reflect.Field;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.text.SimpleDateFormat;
//import java.util.Collection;
//import java.util.Date;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * 利用开源组件POI3.0.2动态导出EXCEL文档 转载时请保留以下信息，注明出处！
// *
// * @param <T> 应用泛型，代表任意一个符合javabean风格的类
// *            注意这里为了简单起见，boolean型的属性xxx的get器方式为getXxx(),而不是isXxx()
// *            byte[]表jpg格式的图片数据
// * @author leno
// * @version v1.0
// */
//public class ExcelUtil<T> {
//    public void exportExcel(Collection<T> dataset, OutputStream out) {
//        exportExcel("sheet1", null, dataset, out, "yyyy-MM-dd");
//    }
//
//    public void exportExcel(String[] headers, Collection<T> dataset,
//                            OutputStream out) {
//        exportExcel("sheet1", headers, dataset, out, "yyyy-MM-dd");
//    }
//
//    public void exportExcel(String[] headers, Collection<T> dataset,
//                            OutputStream out, String pattern) {
//        exportExcel("sheet1", headers, dataset, out, pattern);
//    }
//
//    public void exportExcel(String[] headers, Map<String,Object> dataMap,
//                            OutputStream out) {
//        exportMultiSheet(headers, dataMap, out,"yyyy-MM-dd");
//    }
//
//
//    /**
//     * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
//     *
//     * @param title   表格标题名
//     * @param headers 表格属性列名数组
//     * @param dataset 需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
//     *                javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
//     * @param out     与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
//     * @param pattern 如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
//     */
//    private void exportExcel(String title, String[] headers,
//                            Collection<T> dataset, OutputStream out, String pattern) {
//        // 声明一个工作薄
//        HSSFWorkbook workbook = new HSSFWorkbook();
//        // 生成一个表格
//        HSSFSheet sheet = workbook.createSheet(title);
//        // 设置表格默认列宽度为15个字节
//        sheet.setDefaultColumnWidth((short) 20);
//        // 生成一个样式
//        HSSFCellStyle style = workbook.createCellStyle();
//        // 设置这些样式
//        style.setFillForegroundColor(HSSFColor.WHITE.index);
//        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
//        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
//        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//        // 生成一个字体
//        HSSFFont font = workbook.createFont();
//        font.setFontName("黑体");
//        font.setColor(HSSFColor.BLACK.index);
//        font.setFontHeightInPoints((short) 14);
//        font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
//        // 把字体应用到当前的样式
//        style.setFont(font);
//        // 生成并设置另一个样式
//        HSSFCellStyle style2 = workbook.createCellStyle();
//        style2.setFillForegroundColor(HSSFColor.WHITE.index);
//        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
//        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
//        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//        // 自动换行
//        style2.setWrapText(true);
//        // 生成另一个字体
//        HSSFFont font2 = workbook.createFont();
//        font2.setFontName("仿宋_GB2312");
//        font2.setColor(HSSFColor.BLACK.index);
//        font2.setFontHeightInPoints((short) 12);
//        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
//        // 把字体应用到当前的样式
//        style2.setFont(font2);
//
//        // 声明一个画图的顶级管理器
//        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
//
//
//        HSSFFont font3 = workbook.createFont();
//        font3.setFontName("仿宋_GB2312");
//        font3.setColor(HSSFColor.BLACK.index);
//        font3.setFontHeightInPoints((short) 12);
//        font3.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
//
//        // 定义注释的大小和位置,详见文档
////        HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,
////                0, 0, 0, (short) 4, 2, (short) 6, 5));
//        // 设置注释内容
////        comment.setString(new HSSFRichTextString(""));
//        // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
////        comment.setAuthor("");
//
//        // 产生表格标题行
//        HSSFRow row = sheet.createRow(0);
//        for (short i = 0; i < headers.length; i++) {
//            HSSFCell cell = row.createCell(i);
//            cell.setCellStyle(style);
//            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
//            cell.setCellValue(text);
//        }
//
//        // 遍历集合数据，产生数据行
//        Iterator<T> it = dataset.iterator();
//        int index = 0;
//        while (it.hasNext()) {
//            index++;
//            row = sheet.createRow(index);
//            T t = it.next();
//            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
//            Field[] fields = t.getClass().getDeclaredFields();
//            for (short i = 0; i < fields.length; i++) {
//                HSSFCell cell = row.createCell(i);
//                cell.setCellStyle(style2);
//                Field field = fields[i];
//                String fieldName = field.getName();
//                String getMethodName = "get"
//                        + fieldName.substring(0, 1).toUpperCase()
//                        + fieldName.substring(1);
//                try {
//                    Class tCls = t.getClass();
//                    Method getMethod = tCls.getMethod(getMethodName, new Class[]{});
//                    Object value = getMethod.invoke(t, new Object[]{});
//                    // 判断值的类型后进行强制类型转换
//                    String textValue = null;
//                    // if (value instanceof Integer) {
//                    // int intValue = (Integer) value;
//                    // cell.setCellValue(intValue);
//                    // } else if (value instanceof Float) {
//                    // float fValue = (Float) value;
//                    // textValue = new HSSFRichTextString(
//                    // String.valueOf(fValue));
//                    // cell.setCellValue(textValue);
//                    // } else if (value instanceof Double) {
//                    // double dValue = (Double) value;
//                    // textValue = new HSSFRichTextString(
//                    // String.valueOf(dValue));
//                    // cell.setCellValue(textValue);
//                    // } else if (value instanceof Long) {
//                    // long longValue = (Long) value;
//                    // cell.setCellValue(longValue);
//                    // }
////                    if (value instanceof Boolean) {
////                        boolean bValue = (Boolean) value;
////                        textValue = "男";
////                        if (!bValue) {
////                            textValue = "女";
////                        }
////                    }
//                    if (value instanceof Integer) {
//                        int intValue = (Integer) value;
//                        cell.setCellValue(intValue);
//                    } else if (value instanceof Date) {
//                        Date date = (Date) value;
//                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
//                        textValue = sdf.format(date);
//                    } else if (value instanceof byte[]) {
//                        // 有图片时，设置行高为60px;
//                        row.setHeightInPoints(60);
//                        // 设置图片所在列宽度为80px,注意这里单位的一个换算
//                        sheet.setColumnWidth(i, (short) (35.7 * 80));
//                        // sheet.autoSizeColumn(i);
//                        byte[] bsValue = (byte[]) value;
//                        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
//                                1023, 255, (short) 6, index, (short) 6, index);
//                        anchor.setAnchorType(2);
//                        patriarch.createPicture(anchor, workbook.addPicture(
//                                bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
//                    } else {
//                        if(value!=null){
//                            // 其它数据类型都当作字符串简单处理
//                            textValue = value.toString();
//                        }else {
//                            textValue = "";
//                        }
//
////                        if(textValue.toCharArray().length>=30){
////                            sheet.setColumnWidth(i, (short) (33 * 900));
////                        }
//
//                    }
//                    // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
//                    if (textValue != null) {
//                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
//                        Matcher matcher = p.matcher(textValue);
//                        if (matcher.matches()) {
//                            // 是数字当作double处理
//                            cell.setCellValue(Double.parseDouble(textValue));
//                        } else {
//                            HSSFRichTextString richString = new HSSFRichTextString(
//                                    textValue);
//
//                            richString.applyFont(font3);
//                            cell.setCellValue(richString);
//                        }
//                    }
//                } catch (SecurityException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
//                    e.printStackTrace();
//                } finally {
//                    // 清理资源
//                }
//            }
//        }
//        try {
//            workbook.write(out);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//
//
//
//
//
//    private void exportMultiSheet(String[] headers,
//                             Map<String,Object> dataMap, OutputStream out, String pattern) {
//
//
//
//        // 声明一个工作薄
//        HSSFWorkbook workbook = new HSSFWorkbook();
//        // 生成一个样式
//        HSSFCellStyle style = workbook.createCellStyle();
//        // 设置这些样式
//        style.setFillForegroundColor(HSSFColor.WHITE.index);
//        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
//        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
//        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//        // 生成一个字体
//        HSSFFont font = workbook.createFont();
//        font.setFontName("黑体");
//        font.setColor(HSSFColor.BLACK.index);
//        font.setFontHeightInPoints((short) 14);
//        font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
//        // 把字体应用到当前的样式
//        style.setFont(font);
//        // 生成并设置另一个样式
//        HSSFCellStyle style2 = workbook.createCellStyle();
//        style2.setFillForegroundColor(HSSFColor.WHITE.index);
//        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
//        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
//        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//        // 自动换行
//        style2.setWrapText(true);
//        // 生成另一个字体
//        HSSFFont font2 = workbook.createFont();
//        font2.setFontName("仿宋_GB2312");
//        font2.setColor(HSSFColor.BLACK.index);
//        font2.setFontHeightInPoints((short) 12);
//        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
//        // 把字体应用到当前的样式
//        style2.setFont(font2);
//
//
//        for (String key : dataMap.keySet()){
//            // 生成一个表格
//            HSSFSheet sheet = workbook.createSheet(key);
//            // 设置表格默认列宽度为15个字节
//            sheet.setDefaultColumnWidth((short) 20);
//
//
//            // 声明一个画图的顶级管理器
//            HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
//
//            // 产生表格标题行
//            HSSFRow row = sheet.createRow(0);
//            for (short i = 0; i < headers.length; i++) {
//                HSSFCell cell = row.createCell(i);
//                cell.setCellStyle(style);
//                HSSFRichTextString text = new HSSFRichTextString(headers[i]);
//                cell.setCellValue(text);
//            }
//
//
//            // 遍历集合数据，产生数据行
//            Iterator<T> it = ((Collection<T>)dataMap.get(key)).iterator();
//            int index = 0;
//            while (it.hasNext()) {
//                index++;
//                row = sheet.createRow(index);
//                T t = it.next();
//                // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
//                Field[] fields = t.getClass().getDeclaredFields();
//                for (short i = 0; i < fields.length; i++) {
//                    HSSFCell cell = row.createCell(i);
//                    cell.setCellStyle(style2);
//                    Field field = fields[i];
//                    String fieldName = field.getName();
//                    String getMethodName = "get"
//                            + fieldName.substring(0, 1).toUpperCase()
//                            + fieldName.substring(1);
//                    try {
//                        Class tCls = t.getClass();
//                        Method getMethod = tCls.getMethod(getMethodName, new Class[]{});
//                        Object value = getMethod.invoke(t, new Object[]{});
//                        // 判断值的类型后进行强制类型转换
//                        String textValue = null;
//                        if (value instanceof Integer) {
//                            int intValue = (Integer) value;
//                            cell.setCellValue(intValue);
//                        } else if (value instanceof Date) {
//                            Date date = (Date) value;
//                            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
//                            textValue = sdf.format(date);
//                        } else if (value instanceof byte[]) {
//                            // 有图片时，设置行高为60px;
//                            row.setHeightInPoints(60);
//                            // 设置图片所在列宽度为80px,注意这里单位的一个换算
//                            sheet.setColumnWidth(i, (short) (35.7 * 80));
//                            // sheet.autoSizeColumn(i);
//                            byte[] bsValue = (byte[]) value;
//                            HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
//                                    1023, 255, (short) 6, index, (short) 6, index);
//                            anchor.setAnchorType(2);
//                            patriarch.createPicture(anchor, workbook.addPicture(
//                                    bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
//                        } else {
//                            if(value!=null){
//                                // 其它数据类型都当作字符串简单处理
//                                textValue = value.toString();
//                            }else {
//                                textValue = "";
//                            }
//
//                            if(textValue.toCharArray().length>=30){
//                                sheet.setColumnWidth(i, (short) (33 * 900));
//                            }
//
//                        }
//                        // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
//                        if (textValue != null) {
//                            Pattern p = Pattern.compile("^//d+(//.//d+)?$");
//                            Matcher matcher = p.matcher(textValue);
//                            if (matcher.matches()) {
//                                // 是数字当作double处理
//                                cell.setCellValue(Double.parseDouble(textValue));
//                            } else {
//                                HSSFRichTextString richString = new HSSFRichTextString(
//                                        textValue);
//                                HSSFFont font3 = workbook.createFont();
//                                font3.setFontName("仿宋_GB2312");
//                                font3.setColor(HSSFColor.BLACK.index);
//                                font3.setFontHeightInPoints((short) 12);
//                                font3.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
//                                richString.applyFont(font3);
//                                cell.setCellValue(richString);
//                            }
//                        }
//                    } catch (SecurityException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
//                        e.printStackTrace();
//                    } finally {
//                        // 清理资源
//                    }
//                }
//            }
//
//
//        }
//
//
//        try {
//            workbook.write(out);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//
//
//
//
////    public static void main(String[] args) {
////        // 测试学生
////        ExcelUtil<ResMsgLog> ex = new ExcelUtil<ResMsgLog>();
////        String[] headers = { "学号", "姓名", "年龄", "性别", "出生日期" };
////        List<ResMsgLog> dataset = new ArrayList<ResMsgLog>();
//////        dataset.add();
//////        dataset.add();
//////        dataset.add();
////        // 测试图书
//////        ExcelUtil<Book> ex2 = new ExcelUtil<Book>();
//////        String[] headers2 = { "图书编号", "图书名称", "图书作者", "图书价格", "图书ISBN",
//////                "图书出版社", "封面图片" };
//////        List<Book> dataset2 = new ArrayList<Book>();
////        try {
////            // BufferedInputStream bis = new BufferedInputStream(
////            // new FileInputStream("V://book.bmp"));
////            BufferedInputStream bis = new BufferedInputStream(
////                    new FileInputStream("/home/book.bmp"));
////            byte[] buf = new byte[bis.available()];
////            while ((bis.read(buf)) != -1) {
////                //
////            }
//////            dataset2.add(new Book(1, "jsp", "leno", 300.33f, "1234567",
//////                    "清华出版社", buf));
//////            dataset2.add(new Book(2, "java编程思想", "brucl", 300.33f, "1234567",
//////                    "阳光出版社", buf));
//////            dataset2.add(new Book(3, "DOM艺术", "lenotang", 300.33f, "1234567",
//////                    "清华出版社", buf));
//////            dataset2.add(new Book(4, "c++经典", "leno", 400.33f, "1234567",
//////                    "清华出版社", buf));
//////            dataset2.add(new Book(5, "c#入门", "leno", 300.33f, "1234567",
//////                    "汤春秀出版社", buf));
////            // OutputStream out = new FileOutputStream("E://export2003_a.xls");
////            // OutputStream out2 = new FileOutputStream("E://export2003_b.xls");
////            OutputStream out = new FileOutputStream("/home/export2003_a.xls");
//////            OutputStream out2 = new FileOutputStream("/home/export2003_b.xls");
////            ex.exportExcel(headers, dataset, out);
//////            ex2.exportExcel(headers2, dataset2, out2);
////            out.close();
////            JOptionPane.showMessageDialog(null, "导出成功!");
////            System.out.println("excel导出成功！");
////        } catch (FileNotFoundException e) {
////            e.printStackTrace();
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////    }
//
//
//}
