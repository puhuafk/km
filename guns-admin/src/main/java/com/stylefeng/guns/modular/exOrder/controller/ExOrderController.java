package com.stylefeng.guns.modular.exOrder.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.modular.exOrder.service.IExOrderService;
import com.stylefeng.guns.modular.system.model.ExOrder;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 异常订单管理控制器
 *
 * @author fengshuonan
 * @Date 2018-10-12 13:38:30
 */
@Controller
@RequestMapping("/exOrder")
public class ExOrderController extends BaseController {


    private String PREFIX = "/exOrder/exOrder/";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private IExOrderService exOrderService;

    /**
     *
     * 退单品（导出）
     */
    @RequestMapping("exportDeExOrderList")
    public List<Map<String, Object>> exportDeExOrderList(Date startTime,Date endTime,String content,HttpServletResponse response) throws IOException {
        List<Map<String, Object>> exOrderListList = exOrderService.exportDeExOrderList(2, 0,startTime,endTime,content);
        System.out.println(exOrderListList);
         //创建Excel工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("信息表");//sheet表格,servlet服务器小程序
        // 生成一个表格样式(表头样式)
        HSSFCellStyle style0 = workbook.createCellStyle();
        // 设置单元格背景样式颜色
        style0.setFillPattern(FillPatternType.SOLID_FOREGROUND);// 普通颜色填充
        style0.setFillForegroundColor(HSSFColor.HSSFColorPredefined.TEAL.getIndex());
        // 设置Excel中的边框(表头的边框)
        style0.setAlignment(HorizontalAlignment.CENTER);// 文字水平居中
        style0.setVerticalAlignment(VerticalAlignment.CENTER);// 文字垂直居中
        style0.setBorderBottom(BorderStyle.THIN);// 底部边框线样式(细实线)
        style0.setBottomBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());// 底部边框线颜色
        style0.setBorderLeft(BorderStyle.THIN);// 左边框线样式(细实线)
        style0.setLeftBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());// 左边框线颜色
        style0.setBorderRight(BorderStyle.THIN);// 右边框线样式(细实线)
        style0.setRightBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());// 右边框线颜色
        style0.setBorderTop(BorderStyle.THIN);// 顶部边框线样式(细实线)
        style0.setTopBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());// 顶部边框线颜色
        style0.setWrapText(true);// 自动换行
        // 设置字体
        HSSFFont font0 = workbook.createFont();// 生成一个字体样式
        font0.setFontHeightInPoints((short) 24); // 字体高度(大小)
        font0.setFontName("宋体"); // 字体
        font0.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());//字体颜色
        font0.setBold(true);// 加粗
        style0.setFont(font0);// 把字体应用到当前样式
        sheet.addMergedRegion(new CellRangeAddress(0, 2, 0, 11));
        //表格名称
        String[] head = {"退单品订单"};
        HSSFRow row0 = sheet.createRow(0);
        for (int q = 0; q < head.length; q++) {
            HSSFCell cell0 = row0.createCell(q); //Cell表格最小单位
            HSSFRichTextString text = new HSSFRichTextString(head[q]);
            cell0.setCellValue(text);
            cell0.setCellStyle(style0);
        }
//        style = workbook.createCellStyle();
//        style.setFillForegroundColor(IndexedColors.INDIGO.getIndex());
//        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
//        cell = row4.createCell((short) 5);
//        cell.setCellValue("X20");
//        cell.setCellStyle(style);
//        style = workbook.createCellStyle();
//        style.setFillForegroundColor(IndexedColors.ROYAL_BLUE.getIndex());
//        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
//        cell = row9.createCell((short) 1);
//        cell.setCellValue("X41");
//        cell.setCellStyle(style);


        // 生成一个表格样式(表头样式)
        HSSFCellStyle style1 = workbook.createCellStyle();
        // 设置单元格背景样式颜色
        style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);// 普通颜色填充
//        style1.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_ORANGE.getIndex());

        style1.setFillForegroundColor(IndexedColors.ROYAL_BLUE.getIndex());




        // 设置Excel中的边框(表头的边框)
        style1.setAlignment(HorizontalAlignment.CENTER);// 文字水平居中
        style1.setVerticalAlignment(VerticalAlignment.CENTER);// 文字垂直居中

        style1.setBorderBottom(BorderStyle.THIN);// 底部边框线样式(细实线)
        style1.setBottomBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());// 底部边框线颜色
        style1.setBorderLeft(BorderStyle.THIN);// 左边框线样式(细实线)
        style1.setLeftBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());// 左边框线颜色
        style1.setBorderRight(BorderStyle.THIN);// 右边框线样式(细实线)
        style1.setRightBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());// 右边框线颜色
        style1.setBorderTop(BorderStyle.THIN);// 顶部边框线样式(细实线)

        style1.setWrapText(true);// 自动换行
        // 设置字体
        HSSFFont font1 = workbook.createFont();// 生成一个字体样式
        font1.setFontHeightInPoints((short) 20); // 字体高度(大小)
        font1.setFontName("宋体"); // 字体
        font1.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());//字体颜色
        font1.setBold(true);// 加粗
        style1.setFont(font1);// 把字体应用到当前样式



//        String[] title = {"序号", "操作日期", "订单日期", "店名", "支付方式", "退货名称", "规格", "数量", "单位说明", "退款金额",  "退货支付方式", "退款接受人"};
        String[] title = {"序号", "操作日期", "订单日期","客户名称","条形码", "商品名称", "规格/型号", "数量", "单位说明", "退单品数量","正常价", "退款金额"};

//        String[] title = {"id", "name", "sex"};

        //headers表示excel表中第一行的表头
        HSSFRow row1 = sheet.createRow(3);
        //在excel表中添加表头
        for (int i = 0; i < title.length; i++) {
            HSSFCell cell = row1.createCell(i); //Cell表格最小单位
            HSSFRichTextString text = new HSSFRichTextString(title[i]);
            cell.setCellValue(text);
            cell.setCellStyle(style1);
        }



//        style = workbook.createCellStyle();
//        style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
//        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
//        cell = row10.createCell((short) 3);
//        cell.setCellValue("X49");
//        cell.setCellStyle(style);


        // 生成一个表格样式(表头样式)
        HSSFCellStyle style2 = workbook.createCellStyle();
        // 设置单元格背景样式颜色
        style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);// 普通颜色填充
        style2.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        // 设置Excel中的边框(表头的边框)
        style2.setAlignment(HorizontalAlignment.CENTER);// 文字水平居中
        style2.setVerticalAlignment(VerticalAlignment.CENTER);// 文字垂直居中
        style2.setBorderBottom(BorderStyle.THIN);// 底部边框线样式(细实线)
        style2.setBottomBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());// 底部边框线颜色
        style2.setBorderLeft(BorderStyle.THIN);// 左边框线样式(细实线)
        style2.setLeftBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());// 左边框线颜色
        style2.setBorderRight(BorderStyle.THIN);// 右边框线样式(细实线)
        style2.setRightBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());// 右边框线颜色
        style2.setBorderTop(BorderStyle.THIN);// 顶部边框线样式(细实线)
        style2.setTopBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());// 顶部边框线颜色

        style2.setWrapText(true);// 自动换行
        // 设置字体
        HSSFFont font2 = workbook.createFont();// 生成一个字体样式
        font2.setFontHeightInPoints((short) 16); // 字体高度(大小)
        font2.setFontName("宋体"); // 字体
        font2.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());//字体颜色
        font2.setBold(true);// 加粗
        style2.setFont(font2);// 把字体应用到当前样式





//        //创建一个工作表sheet
////        HSSFSheet sheet = workbook.createSheet();
//        //创建第一行
//        HSSFRow row = sheet.createRow(0);
//        HSSFCell cell = null;
//        //插入第一行数据 "序号", "操作日期", "订单日期", "店名",等
//        for (int i = 0; i < title.length; i++) {
//            cell = row.createCell(i);
//            cell.setCellValue(title[i]);
//        }
        //追加数据
        for( int i =0 ; i < exOrderListList.size() ; i++ ){

            Map<String, Object> rowData = exOrderListList.get(i);
            System.out.println(rowData);
            HSSFRow newRow = sheet.createRow(i + 4);
            newRow.createCell(0).setCellValue(i + 1);//序号

//            String[] title = {"序号", "createtime", "sn", "product_name", "model", "amount", "unit_info", "no_valency", "refund_money", "return_number"};



            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String dateString = formatter.format(rowData.get("createtime"));
            newRow.createCell(1).setCellValue(dateString != null ? dateString  : "");

            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
            String dateString1 = formatter1.format(rowData.get("createdate"));
            newRow.createCell(2).setCellValue(dateString1 != null ? dateString1  : "");


            newRow.createCell(3).setCellValue( rowData.get("customer_name") != null ? String.valueOf(rowData.get("customer_name"))  : "" );
            newRow.createCell(4).setCellValue( rowData.get("sn") != null ? String.valueOf(rowData.get("sn"))  : "" );
            newRow.createCell(5).setCellValue( rowData.get("product_name") != null ? String.valueOf(rowData.get("product_name"))  : "" );

            newRow.createCell(6).setCellValue( rowData.get("model") != null ? String.valueOf(rowData.get("model"))  : "" );
            newRow.createCell(7).setCellValue( rowData.get("amount") != null ? String.valueOf(rowData.get("amount"))  : "" );
            newRow.createCell(8).setCellValue( rowData.get("unit_info") != null ? String.valueOf(rowData.get("unit_info"))  : "" );

            newRow.createCell(9).setCellValue( rowData.get("return_num") != null ? String.valueOf(rowData.get("return_num"))  : "" );
            newRow.createCell(10).setCellValue( rowData.get("no_valency") != null ? String.valueOf(rowData.get("no_valency"))  : "" );
            newRow.createCell(11).setCellValue( rowData.get("refund_money") != null ? String.valueOf(rowData.get("refund_money"))  : "" );





            for (int j = 0; j < 12; j++) {
                HSSFCell cell = newRow.getCell(j);
                cell.setCellStyle(style2);
            }
        }





        //设置自适应宽度
        for (int i = 0; i < 12; i++) {
            sheet.autoSizeColumn(i);
        }
        //通过获取请求头中浏览器信息
        String agent = getHttpServletRequest().getHeader("User-Agent");
//      根据浏览器不同将文本进行编码
        String fileName = "退单品订单" + ".xls";//设置要导出的文件的名字
        fileName = URLEncoder.encode(fileName, "utf-8");
        fileName = fileName.replace("+", " ");

        response.setCharacterEncoding("utf-8");
        OutputStream output = response.getOutputStream();
        response.reset();
        response.setHeader("Content-disposition", "attachment; filename=" + fileName);
        response.setContentType("application/msexcel");
        workbook.write(output);
        output.close();



        return null ;

    }


    /**
     *
     * 退整单订单（导出）
     */
    @RequestMapping("exportAllExOrderList")
    public List<Map<String, Object>> exportAllExOrderList(Date startTime,Date endTime,String content,HttpServletResponse response) throws IOException {
        List<Map<String, Object>> exAllOrderListList = exOrderService.exportAllExOrderList(3,4,0,0,startTime,endTime,content);
        System.out.println(exAllOrderListList);
        //创建Excel工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("信息表");//sheet表格,servlet服务器小程序
        // 生成一个表格样式(表头样式)
        HSSFCellStyle style0 = workbook.createCellStyle();
        // 设置单元格背景样式颜色
        style0.setFillPattern(FillPatternType.SOLID_FOREGROUND);// 普通颜色填充
        style0.setFillForegroundColor(HSSFColor.HSSFColorPredefined.TEAL.getIndex());
        // 设置Excel中的边框(表头的边框)
        style0.setAlignment(HorizontalAlignment.CENTER);// 文字水平居中
        style0.setVerticalAlignment(VerticalAlignment.CENTER);// 文字垂直居中
        style0.setBorderBottom(BorderStyle.THIN);// 底部边框线样式(细实线)
        style0.setBottomBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());// 底部边框线颜色
        style0.setBorderLeft(BorderStyle.THIN);// 左边框线样式(细实线)
        style0.setLeftBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());// 左边框线颜色
        style0.setBorderRight(BorderStyle.THIN);// 右边框线样式(细实线)
        style0.setRightBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());// 右边框线颜色
        style0.setBorderTop(BorderStyle.THIN);// 顶部边框线样式(细实线)
        style0.setTopBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());// 顶部边框线颜色
        style0.setWrapText(true);// 自动换行
        // 设置字体
        HSSFFont font0 = workbook.createFont();// 生成一个字体样式
        font0.setFontHeightInPoints((short) 24); // 字体高度(大小)
        font0.setFontName("宋体"); // 字体
        font0.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());//字体颜色
        font0.setBold(true);// 加粗
        style0.setFont(font0);// 把字体应用到当前样式
        sheet.addMergedRegion(new CellRangeAddress(0, 2, 0, 9));
        //表格名称
        String[] head = {"退整单订单"};
        HSSFRow row0 = sheet.createRow(0);
        for (int q = 0; q < head.length; q++) {
            HSSFCell cell0 = row0.createCell(q); //Cell表格最小单位
            HSSFRichTextString text = new HSSFRichTextString(head[q]);
            cell0.setCellValue(text);
            cell0.setCellStyle(style0);
        }
//        style = workbook.createCellStyle();
//        style.setFillForegroundColor(IndexedColors.INDIGO.getIndex());
//        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
//        cell = row4.createCell((short) 5);
//        cell.setCellValue("X20");
//        cell.setCellStyle(style);
//        style = workbook.createCellStyle();
//        style.setFillForegroundColor(IndexedColors.ROYAL_BLUE.getIndex());
//        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
//        cell = row9.createCell((short) 1);
//        cell.setCellValue("X41");
//        cell.setCellStyle(style);


        // 生成一个表格样式(表头样式)
        HSSFCellStyle style1 = workbook.createCellStyle();
        // 设置单元格背景样式颜色
        style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);// 普通颜色填充
//        style1.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_ORANGE.getIndex());

        style1.setFillForegroundColor(IndexedColors.ROYAL_BLUE.getIndex());




        // 设置Excel中的边框(表头的边框)
        style1.setAlignment(HorizontalAlignment.CENTER);// 文字水平居中
        style1.setVerticalAlignment(VerticalAlignment.CENTER);// 文字垂直居中

        style1.setBorderBottom(BorderStyle.THIN);// 底部边框线样式(细实线)
        style1.setBottomBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());// 底部边框线颜色
        style1.setBorderLeft(BorderStyle.THIN);// 左边框线样式(细实线)
        style1.setLeftBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());// 左边框线颜色
        style1.setBorderRight(BorderStyle.THIN);// 右边框线样式(细实线)
        style1.setRightBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());// 右边框线颜色
        style1.setBorderTop(BorderStyle.THIN);// 顶部边框线样式(细实线)

        style1.setWrapText(true);// 自动换行
        // 设置字体
        HSSFFont font1 = workbook.createFont();// 生成一个字体样式
        font1.setFontHeightInPoints((short) 20); // 字体高度(大小)
        font1.setFontName("宋体"); // 字体
        font1.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());//字体颜色
        font1.setBold(true);// 加粗
        style1.setFont(font1);// 把字体应用到当前样式



//        String[] title = {"序号", "操作日期", "订单日期", "店名", "支付方式", "退货名称", "规格", "数量", "单位说明", "退款金额",  "退货支付方式", "退款接受人"};
        String[] title = {"序号", "操作日期", "条形码", "商品名称", "规格/型号", "数量", "单位说明", "退货数量","正常价", "退款金额"};

//        String[] title = {"id", "name", "sex"};

        //headers表示excel表中第一行的表头
        HSSFRow row1 = sheet.createRow(3);
        //在excel表中添加表头
        for (int i = 0; i < title.length; i++) {
            HSSFCell cell = row1.createCell(i); //Cell表格最小单位
            HSSFRichTextString text = new HSSFRichTextString(title[i]);
            cell.setCellValue(text);
            cell.setCellStyle(style1);
        }



//        style = workbook.createCellStyle();
//        style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
//        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
//        cell = row10.createCell((short) 3);
//        cell.setCellValue("X49");
//        cell.setCellStyle(style);


        // 生成一个表格样式(表头样式)
        HSSFCellStyle style2 = workbook.createCellStyle();
        // 设置单元格背景样式颜色
        style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);// 普通颜色填充
        style2.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        // 设置Excel中的边框(表头的边框)
        style2.setAlignment(HorizontalAlignment.CENTER);// 文字水平居中
        style2.setVerticalAlignment(VerticalAlignment.CENTER);// 文字垂直居中
        style2.setBorderBottom(BorderStyle.THIN);// 底部边框线样式(细实线)
        style2.setBottomBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());// 底部边框线颜色
        style2.setBorderLeft(BorderStyle.THIN);// 左边框线样式(细实线)
        style2.setLeftBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());// 左边框线颜色
        style2.setBorderRight(BorderStyle.THIN);// 右边框线样式(细实线)
        style2.setRightBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());// 右边框线颜色
        style2.setBorderTop(BorderStyle.THIN);// 顶部边框线样式(细实线)
        style2.setTopBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());// 顶部边框线颜色

        style2.setWrapText(true);// 自动换行
        // 设置字体
        HSSFFont font2 = workbook.createFont();// 生成一个字体样式
        font2.setFontHeightInPoints((short) 16); // 字体高度(大小)
        font2.setFontName("宋体"); // 字体
        font2.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());//字体颜色
        font2.setBold(true);// 加粗
        style2.setFont(font2);// 把字体应用到当前样式





//        //创建一个工作表sheet
////        HSSFSheet sheet = workbook.createSheet();
//        //创建第一行
//        HSSFRow row = sheet.createRow(0);
//        HSSFCell cell = null;
//        //插入第一行数据 "序号", "操作日期", "订单日期", "店名",等
//        for (int i = 0; i < title.length; i++) {
//            cell = row.createCell(i);
//            cell.setCellValue(title[i]);
//        }
        //追加数据
        for( int i =0 ; i < exAllOrderListList.size() ; i++ ){

            Map<String, Object> rowData = exAllOrderListList.get(i);
            System.out.println(rowData);
            HSSFRow newRow = sheet.createRow(i + 4);
            newRow.createCell(0).setCellValue(i + 1);//序号

//            String[] title = {"序号", "createtime", "sn", "product_name", "model", "amount", "unit_info", "no_valency", "refund_money", "return_number"};

            newRow.createCell(1).setCellValue( rowData.get("createtime") != null ? String.valueOf(rowData.get("createtime"))  : "" );
            newRow.createCell(2).setCellValue( rowData.get("sn") != null ? String.valueOf(rowData.get("sn"))  : "" );
            newRow.createCell(3).setCellValue( rowData.get("product_name") != null ? String.valueOf(rowData.get("product_name"))  : "" );
            newRow.createCell(4).setCellValue( rowData.get("model") != null ? String.valueOf(rowData.get("model"))  : "" );
            newRow.createCell(5).setCellValue( rowData.get("amount") != null ? String.valueOf(rowData.get("amount"))  : "" );
            newRow.createCell(6).setCellValue( rowData.get("unit_info") != null ? String.valueOf(rowData.get("unit_info"))  : "" );
            newRow.createCell(7).setCellValue( rowData.get("return_num") != null ? String.valueOf(rowData.get("return_num"))  : "" );
            newRow.createCell(8).setCellValue( rowData.get("no_valency") != null ? String.valueOf(rowData.get("no_valency"))  : "" );
            newRow.createCell(9).setCellValue( rowData.get("refund_money") != null ? String.valueOf(rowData.get("refund_money"))  : "" );





            for (int j = 0; j < 10; j++) {
                HSSFCell cell = newRow.getCell(j);
                cell.setCellStyle(style2);
            }
        }





        //设置自适应宽度
        for (int i = 0; i < 10;i++) {
            sheet.autoSizeColumn(i);
        }
        //通过获取请求头中浏览器信息
        String agent = getHttpServletRequest().getHeader("User-Agent");
//      根据浏览器不同将文本进行编码
        String fileName = "退整单导出" + ".xls";//设置要导出的文件的名字
        fileName = URLEncoder.encode(fileName, "utf-8");
        fileName = fileName.replace("+", " ");

        response.setCharacterEncoding("utf-8");
        OutputStream output = response.getOutputStream();
        response.reset();
        response.setHeader("Content-disposition", "attachment; filename=" + fileName);
        response.setContentType("application/msexcel");
        workbook.write(output);
        output.close();



        return null ;

    }


    /**
     *
     * 退单品，退整单订单（导出）
     */
//    @RequestMapping("exportAllExOrderList")
//    public List<Map<String, Object>> exportAllExOrderList(Date startTime,Date endTime,String content,HttpServletResponse response) throws IOException {
//        List<Map<String, Object>> exAllOrderListList = exOrderService.exportAllExOrderList(2,3,4,0,0,startTime,endTime,content);
//        System.out.println(exAllOrderListList);
//        //创建Excel工作簿
//        HSSFWorkbook workbook = new HSSFWorkbook();
//        HSSFSheet sheet = workbook.createSheet("信息表");//sheet表格,servlet服务器小程序
//        // 生成一个表格样式(表头样式)
//        HSSFCellStyle style0 = workbook.createCellStyle();
//        // 设置单元格背景样式颜色
//        style0.setFillPattern(FillPatternType.SOLID_FOREGROUND);// 普通颜色填充
//        style0.setFillForegroundColor(HSSFColor.HSSFColorPredefined.TEAL.getIndex());
//        // 设置Excel中的边框(表头的边框)
//        style0.setAlignment(HorizontalAlignment.CENTER);// 文字水平居中
//        style0.setVerticalAlignment(VerticalAlignment.CENTER);// 文字垂直居中
//        style0.setBorderBottom(BorderStyle.THIN);// 底部边框线样式(细实线)
//        style0.setBottomBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());// 底部边框线颜色
//        style0.setBorderLeft(BorderStyle.THIN);// 左边框线样式(细实线)
//        style0.setLeftBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());// 左边框线颜色
//        style0.setBorderRight(BorderStyle.THIN);// 右边框线样式(细实线)
//        style0.setRightBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());// 右边框线颜色
//        style0.setBorderTop(BorderStyle.THIN);// 顶部边框线样式(细实线)
//        style0.setTopBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());// 顶部边框线颜色
//        style0.setWrapText(true);// 自动换行
//        // 设置字体
//        HSSFFont font0 = workbook.createFont();// 生成一个字体样式
//        font0.setFontHeightInPoints((short) 24); // 字体高度(大小)
//        font0.setFontName("宋体"); // 字体
//        font0.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());//字体颜色
//        font0.setBold(true);// 加粗
//        style0.setFont(font0);// 把字体应用到当前样式
//        sheet.addMergedRegion(new CellRangeAddress(0, 2, 0, 9));
//        //表格名称
//        String[] head = {"异常订单"};
//        HSSFRow row0 = sheet.createRow(0);
//        for (int q = 0; q < head.length; q++) {
//            HSSFCell cell0 = row0.createCell(q); //Cell表格最小单位
//            HSSFRichTextString text = new HSSFRichTextString(head[q]);
//            cell0.setCellValue(text);
//            cell0.setCellStyle(style0);
//        }
////        style = workbook.createCellStyle();
////        style.setFillForegroundColor(IndexedColors.INDIGO.getIndex());
////        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
////        cell = row4.createCell((short) 5);
////        cell.setCellValue("X20");
////        cell.setCellStyle(style);
////        style = workbook.createCellStyle();
////        style.setFillForegroundColor(IndexedColors.ROYAL_BLUE.getIndex());
////        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
////        cell = row9.createCell((short) 1);
////        cell.setCellValue("X41");
////        cell.setCellStyle(style);
//
//
//        // 生成一个表格样式(表头样式)
//        HSSFCellStyle style1 = workbook.createCellStyle();
//        // 设置单元格背景样式颜色
//        style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);// 普通颜色填充
////        style1.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_ORANGE.getIndex());
//
//        style1.setFillForegroundColor(IndexedColors.ROYAL_BLUE.getIndex());
//
//
//
//
//        // 设置Excel中的边框(表头的边框)
//        style1.setAlignment(HorizontalAlignment.CENTER);// 文字水平居中
//        style1.setVerticalAlignment(VerticalAlignment.CENTER);// 文字垂直居中
//
//        style1.setBorderBottom(BorderStyle.THIN);// 底部边框线样式(细实线)
//        style1.setBottomBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());// 底部边框线颜色
//        style1.setBorderLeft(BorderStyle.THIN);// 左边框线样式(细实线)
//        style1.setLeftBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());// 左边框线颜色
//        style1.setBorderRight(BorderStyle.THIN);// 右边框线样式(细实线)
//        style1.setRightBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());// 右边框线颜色
//        style1.setBorderTop(BorderStyle.THIN);// 顶部边框线样式(细实线)
//
//        style1.setWrapText(true);// 自动换行
//        // 设置字体
//        HSSFFont font1 = workbook.createFont();// 生成一个字体样式
//        font1.setFontHeightInPoints((short) 20); // 字体高度(大小)
//        font1.setFontName("宋体"); // 字体
//        font1.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());//字体颜色
//        font1.setBold(true);// 加粗
//        style1.setFont(font1);// 把字体应用到当前样式
//
//
//
////        String[] title = {"序号", "操作日期", "订单日期", "店名", "支付方式", "退货名称", "规格", "数量", "单位说明", "退款金额",  "退货支付方式", "退款接受人"};
//        String[] title = {"序号", "操作日期", "条形码", "商品名称", "规格/型号", "数量", "单位说明", "退货数量","正常价", "退款金额"};
//
////        String[] title = {"id", "name", "sex"};
//
//        //headers表示excel表中第一行的表头
//        HSSFRow row1 = sheet.createRow(3);
//        //在excel表中添加表头
//        for (int i = 0; i < title.length; i++) {
//            HSSFCell cell = row1.createCell(i); //Cell表格最小单位
//            HSSFRichTextString text = new HSSFRichTextString(title[i]);
//            cell.setCellValue(text);
//            cell.setCellStyle(style1);
//        }
//
//
//
////        style = workbook.createCellStyle();
////        style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
////        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
////        cell = row10.createCell((short) 3);
////        cell.setCellValue("X49");
////        cell.setCellStyle(style);
//
//
//        // 生成一个表格样式(表头样式)
//        HSSFCellStyle style2 = workbook.createCellStyle();
//        // 设置单元格背景样式颜色
//        style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);// 普通颜色填充
//        style2.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
//        // 设置Excel中的边框(表头的边框)
//        style2.setAlignment(HorizontalAlignment.CENTER);// 文字水平居中
//        style2.setVerticalAlignment(VerticalAlignment.CENTER);// 文字垂直居中
//        style2.setBorderBottom(BorderStyle.THIN);// 底部边框线样式(细实线)
//        style2.setBottomBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());// 底部边框线颜色
//        style2.setBorderLeft(BorderStyle.THIN);// 左边框线样式(细实线)
//        style2.setLeftBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());// 左边框线颜色
//        style2.setBorderRight(BorderStyle.THIN);// 右边框线样式(细实线)
//        style2.setRightBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());// 右边框线颜色
//        style2.setBorderTop(BorderStyle.THIN);// 顶部边框线样式(细实线)
//        style2.setTopBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());// 顶部边框线颜色
//
//        style2.setWrapText(true);// 自动换行
//        // 设置字体
//        HSSFFont font2 = workbook.createFont();// 生成一个字体样式
//        font2.setFontHeightInPoints((short) 16); // 字体高度(大小)
//        font2.setFontName("宋体"); // 字体
//        font2.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());//字体颜色
//        font2.setBold(true);// 加粗
//        style2.setFont(font2);// 把字体应用到当前样式
//
//
//
//
//
////        //创建一个工作表sheet
//////        HSSFSheet sheet = workbook.createSheet();
////        //创建第一行
////        HSSFRow row = sheet.createRow(0);
////        HSSFCell cell = null;
////        //插入第一行数据 "序号", "操作日期", "订单日期", "店名",等
////        for (int i = 0; i < title.length; i++) {
////            cell = row.createCell(i);
////            cell.setCellValue(title[i]);
////        }
//        //追加数据
//        for( int i =0 ; i < exAllOrderListList.size() ; i++ ){
//
//            Map<String, Object> rowData = exAllOrderListList.get(i);
//            System.out.println(rowData);
//            HSSFRow newRow = sheet.createRow(i + 4);
//            newRow.createCell(0).setCellValue(i + 1);//序号
//
////            String[] title = {"序号", "createtime", "sn", "product_name", "model", "amount", "unit_info", "no_valency", "refund_money", "return_number"};
//
//            newRow.createCell(1).setCellValue( rowData.get("createtime") != null ? String.valueOf(rowData.get("createtime"))  : "" );
//            newRow.createCell(2).setCellValue( rowData.get("sn") != null ? String.valueOf(rowData.get("sn"))  : "" );
//            newRow.createCell(3).setCellValue( rowData.get("product_name") != null ? String.valueOf(rowData.get("product_name"))  : "" );
//            newRow.createCell(4).setCellValue( rowData.get("model") != null ? String.valueOf(rowData.get("model"))  : "" );
//            newRow.createCell(5).setCellValue( rowData.get("amount") != null ? String.valueOf(rowData.get("amount"))  : "" );
//            newRow.createCell(6).setCellValue( rowData.get("unit_info") != null ? String.valueOf(rowData.get("unit_info"))  : "" );
//            newRow.createCell(7).setCellValue( rowData.get("return_num") != null ? String.valueOf(rowData.get("return_num"))  : "" );
//            newRow.createCell(8).setCellValue( rowData.get("no_valency") != null ? String.valueOf(rowData.get("no_valency"))  : "" );
//            newRow.createCell(9).setCellValue( rowData.get("refund_money") != null ? String.valueOf(rowData.get("refund_money"))  : "" );
//
//
//
//
//
//            for (int j = 0; j < 10; j++) {
//                HSSFCell cell = newRow.getCell(j);
//                cell.setCellStyle(style2);
//            }
//        }
//
//
//
//
//
//        //设置自适应宽度
//        for (int i = 0; i < 10; i++) {
//            sheet.autoSizeColumn(i);
//        }
//        //通过获取请求头中浏览器信息
//        String agent = getHttpServletRequest().getHeader("User-Agent");
////      根据浏览器不同将文本进行编码
//        String fileName = "异常订单" + ".xls";//设置要导出的文件的名字
//        fileName = URLEncoder.encode(fileName, "utf-8");
//        fileName = fileName.replace("+", " ");
//
//        response.setCharacterEncoding("utf-8");
//        OutputStream output = response.getOutputStream();
//        response.reset();
//        response.setHeader("Content-disposition", "attachment; filename=" + fileName);
//        response.setContentType("application/msexcel");
//        workbook.write(output);
//        output.close();
//
//
//
//        return null ;
//
//    }

    /**
     * 跳转到异常订单管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "exOrder.html";
    }

    /**
     * 跳转到添加异常订单管理
     */
    @RequestMapping("/exOrder_add")
    public String exOrderAdd() {
        return PREFIX + "exOrder_add.html";
    }

    /**
     * 跳转到修改异常订单管理
     */
    @RequestMapping("/exOrder_update/{exOrderId}")
    public String exOrderUpdate(@PathVariable Integer exOrderId, Model model, HttpServletRequest request) {
        return PREFIX + "exOrder_edit.html";
    }

    /**
     * 获取异常订单管理列表(得到当前登录人部门id)
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        //根据当前登录人部门id查询异常订单数据
        Integer depId = ((ShiroUser) this.getSession().getAttribute("shiroUser")).getDeptId();

//        Map map = new HashMap();
//        map.put("exam_dep_id", depId);
//        List<ExOrder> list = exOrderService.selectByMap(map);
        Wrapper wrapper = new EntityWrapper<ExOrder>().eq("exam_dep_id", depId);
//        wrapper.setSqlSelect("id","order_sn");
        List<ExOrder> list = exOrderService.selectList(wrapper);
        return list;
    }

    /**
     * 新增异常订单管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ExOrder exOrder) {
        exOrder.setExState(true);
        exOrder.setOperatorId(((ShiroUser) this.getSession().getAttribute("shiroUser")).getId());
        exOrder.setOperator(((ShiroUser) this.getSession().getAttribute("shiroUser")).getName());
        exOrderService.insert(exOrder);
        return SUCCESS_TIP;
    }

    /**
     * 删除异常订单管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer exOrderId) {
        exOrderService.deleteById(exOrderId);
        return SUCCESS_TIP;
    }

    /**
     * 修改异常订单管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ExOrder exOrder) {
        exOrderService.updateById(exOrder);
        return SUCCESS_TIP;
    }

    /**
     * 异常订单管理详情
     */
    @RequestMapping(value = "/detail/{exOrderId}")
    @ResponseBody
    public Object detail(@PathVariable("exOrderId") Integer exOrderId) {
        return exOrderService.selectById(exOrderId);
    }

    public static void main(String[] args) {
        System.out.println(Class.class.getClass().getResource("/").getPath());
        System.out.println(System.getProperty("user.dir"));
    }

    private class SOLID_FOREGROUND {
    }
}
