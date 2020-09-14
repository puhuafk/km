/*
package com.stylefeng.guns.modular.orderItem.controller;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.modular.cancelOrder.OrderController;
import com.stylefeng.guns.modular.ecErpOrder.service.IEcErpOrderService;
import com.stylefeng.guns.modular.exOrder.service.IExOrderService;
import com.stylefeng.guns.modular.exOrderExam.service.IExOrderExamService;
import com.stylefeng.guns.modular.orderItem.service.IOrderItemService;
import com.stylefeng.guns.modular.system.dao.OrderItemMapper;
import com.stylefeng.guns.modular.system.model.EcErpOrder;
import com.stylefeng.guns.modular.system.model.ExOrder;
import com.stylefeng.guns.modular.system.model.OrderItem;
import com.stylefeng.guns.modular.system.service.IDeptService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.*;
@Controller
@RequestMapping("/orderItemExport")
public class ExcelExportController {
    @Autowired
    private IEcErpOrderService ecErpOrderService;

    @Autowired
    private IExOrderService exOrderService;

    @Qualifier("deptServiceImpl")
    @Autowired
    private IDeptService iDeptService;

    @Autowired
    private IExOrderExamService iExOrderExamService;

    @Autowired
    private IOrderItemService iOrderItemService;

    @Autowired
    private OrderController orderController;

    @Autowired
    private OrderItemMapper orderItemMapper;

    //导出Excel
    @RequestMapping("/export")
    @ResponseBody
    public String createExcel(HttpServletResponse response, @PathVariable Integer orderItemId) throws IOException {
        //获取查询结果的数据,reportlist为别的方法查询出来的数据，格式为List<Object[]>,其实这里不管reportlist是什么数据格式，这里只要对其进行封装就行了
        OrderItem orderItem = iOrderItemService.selectById(orderItemId);
        List<Object[]> newlist = (List<Object[]>) orderItem;
        //数据封装，这里的map之所以敢这样add是因为这里的add顺序和hql中的select字段顺序是一样的，总共就查询那么多字段
        List<Map<String, Object>> solist = new ArrayList();
        for (Object[] obj : newlist) {
            //每次循环都要重新new一个map，表示不同对象
            Map<String, Object> map = new HashMap();
            map.put("id", obj[0]);
            map.put("productName", obj[1]);
            map.put("sn", obj[2]);
            map.put("unit", obj[3]);
            map.put("unitState", obj[4]);
            map.put("count", obj[5]);
            map.put("noValency", obj[6]);
            map.put("salesPrice", obj[7]);
            map.put("disPrice", obj[8]);
            map.put("money", obj[9]);

            solist.add(map);
        }
        //创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        //建立新的sheet对象（excel的表单）
        HSSFSheet sheet = wb.createSheet("报表");
        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1 = sheet.createRow(0);
        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell = row1.createCell(0);

        // 1.生成字体对象
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setFontName("新宋体");

        // 2.生成样式对象，这里的设置居中样式和版本有关，我用的poi用HSSFCellStyle.ALIGN_CENTER会报错，所以用下面的
        HSSFCellStyle style = wb.createCellStyle();
        //style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//设置居中样式
        style.setFont(font); // 调用字体样式对象
        style.setWrapText(true);
        style.setAlignment(HorizontalAlignment.CENTER);//设置居中样式

        // 3.单元格应用样式
        cell.setCellStyle(style);

        //设置单元格内容
        cell.setCellValue("报表");
        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 27));

        //在sheet里创建第二行
        HSSFRow row2 = sheet.createRow(1);
        //创建单元格并设置单元格内容及样式
        HSSFCell cell000 = row2.createCell(0);
        cell000.setCellStyle(style);
        cell000.setCellValue("序号");

        HSSFCell cell001 = row2.createCell(1);
        cell001.setCellStyle(style);
        cell001.setCellValue("商品名称");

        HSSFCell cell002 = row2.createCell(2);
        cell002.setCellStyle(style);
        cell002.setCellValue("条形码");

        HSSFCell cell003 = row2.createCell(3);
        cell003.setCellStyle(style);
        cell003.setCellValue("单位");

        HSSFCell cell004 = row2.createCell(4);
        cell004.setCellStyle(style);
        cell004.setCellValue("品牌");

        HSSFCell cell005 = row2.createCell(5);
        cell005.setCellStyle(style);
        cell005.setCellValue("销售数量");

        HSSFCell cell006 = row2.createCell(6);
        cell006.setCellStyle(style);
        cell006.setCellValue("平均销售单价");

        HSSFCell cell007 = row2.createCell(7);
        cell007.setCellStyle(style);
        cell007.setCellValue("销售金额");

        HSSFCell cell008 = row2.createCell(8);
        cell008.setCellStyle(style);
        cell008.setCellValue("退货数量");

        HSSFCell cell009 = row2.createCell(9);
        cell009.setCellStyle(style);
        cell009.setCellValue("退货金额");
        //单元格宽度自适应
        sheet.autoSizeColumn((short) 3);
        sheet.autoSizeColumn((short) 4);
        sheet.autoSizeColumn((short) 5);
        sheet.autoSizeColumn((short) 6);
        sheet.autoSizeColumn((short) 7);
        sheet.autoSizeColumn((short) 8);
        sheet.autoSizeColumn((short) 9);

        //宽度自适应可自行选择自适应哪一行，这里写在前面的是适应第二行，写在后面的是适应第三行
        for (int i = 0; i < solist.size(); i++) {
            //单元格宽度自适应
            sheet.autoSizeColumn((short) 0);
            sheet.autoSizeColumn((short) 1);
            sheet.autoSizeColumn((short) 2);
            //从sheet第三行开始填充数据
            HSSFRow rowx = sheet.createRow(i + 2);
            Map<String, Object> map = solist.get(i);
            //这里的hospitalid,idnumber等都是前面定义的全局变量
            Integer id = (Integer) map.get("id");
            HSSFCell cell00 = rowx.createCell(0);
            cell00.setCellStyle(style);
            cell00.setCellValue(id);

            String productName = (String) map.get("productName");
            HSSFCell cell01 = rowx.createCell(1);
            cell01.setCellStyle(style);
            cell01.setCellValue(productName);

            String sn = (String) map.get("sn");
            HSSFCell cell02 = rowx.createCell(2);
            cell02.setCellStyle(style);
            cell02.setCellValue(sn);

            String unit = (String) map.get("unit");
            HSSFCell cell03 = rowx.createCell(3);
            cell03.setCellStyle(style);
            cell03.setCellValue(unit);

            String unitState = (String) map.get("unitState");
            HSSFCell cell04 = rowx.createCell(4);
            cell04.setCellStyle(style);
            cell04.setCellValue(unitState);

            Integer count = (Integer) map.get("count");
            HSSFCell cell05 = rowx.createCell(5);
            cell05.setCellStyle(style);
            cell05.setCellValue(count);

            BigDecimal noValency = (BigDecimal) map.get("noValency");
            HSSFCell cell06 = rowx.createCell(6);
            cell06.setCellStyle(style);
            cell06.setCellValue((RichTextString) noValency);

            BigDecimal salesPrice = (BigDecimal) map.get("salesPrice");
            HSSFCell cell07 = rowx.createCell(7);
            cell07.setCellStyle(style);
            cell07.setCellValue((RichTextString) salesPrice);

            BigDecimal disPrice = (BigDecimal) map.get("disPrice");
            HSSFCell cell08 = rowx.createCell(8);
            cell08.setCellStyle(style);
            cell08.setCellValue((RichTextString) disPrice);

            BigDecimal money = (BigDecimal) map.get("money");
            HSSFCell cell09 = rowx.createCell(9);
            cell09.setCellStyle(style);
            cell09.setCellValue((RichTextString) money);


        }
        //输出Excel文件
        response.setCharacterEncoding("utf-8");
        OutputStream output = response.getOutputStream();
        response.reset();
        response.setHeader("Content-disposition", "attachment; filename=订单详情.xls");//文件名这里可以改
        response.setContentType("application/msexcel");
        wb.write(output);
        output.close();
        return null;
    }
}




    //数据封装，这里的map之所以敢这样add是因为这里的add顺序和hql中的select字段顺序是一样的，总共就查询那么多字段
    //在excel表中添加数据
    Wrapper wrapper = new EntityWrapper<ExOrder>().between("check_time", startTime, endTime).eq("if_check", true);
    List<EcErpOrder> ecErpOrderList = ecErpOrderService.selectList(wrapper);
    //得到订单id集合
    List<Long> ecErpOrderidList = new ArrayList<>();
        for (EcErpOrder ecErpOrder : ecErpOrderList) {
                ecErpOrderidList.add(ecErpOrder.getId());
                }
                //得到商品表单
                List<OrderItemVo> orderItemVoList = orderItemMapper.selectReportFormList(ecErpOrderidList);
        List<OrderItemVo[]> newlist = (List<OrderItemVo[]>) orderItemVoList;
        List<Map<String, Object>> solist = new ArrayList();
        for (OrderItemVo[] obj : newlist) {
        //每次循环都要重新new一个map，表示不同对象
        Map<String, Object> map = new HashMap();
        map.put("id", obj[0]);
        map.put("productName", obj[1]);
        map.put("sn", obj[2]);
        map.put("unit", obj[3]);
        map.put("brand", obj[4]);
        map.put("amountTotal", obj[5]);
        map.put("avgSalePrice", obj[6]);
        map.put("moneyTotal", obj[7]);
        map.put("returnNumTotal", obj[8]);
        map.put("returnMoneyTotle", obj[9]);

        solist.add(map);
        }
        //创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        //建立新的sheet对象（excel的表单）
        HSSFSheet sheet = wb.createSheet("报表");
        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1 = sheet.createRow(0);
        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell = row1.createCell(0);

        // 1.生成字体对象
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setFontName("新宋体");

        // 2.生成样式对象，这里的设置居中样式和版本有关，我用的poi用HSSFCellStyle.ALIGN_CENTER会报错，所以用下面的
        HSSFCellStyle style = wb.createCellStyle();
        //style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//设置居中样式
        style.setFont(font); // 调用字体样式对象
        style.setWrapText(true);
        style.setAlignment(HorizontalAlignment.CENTER);//设置居中样式

        // 3.单元格应用样式
        cell.setCellStyle(style);

        //设置单元格内容
        cell.setCellValue("商品销售统计");
        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 27));

        //在sheet里创建第二行
        HSSFRow row2 = sheet.createRow(1);
        //创建单元格并设置单元格内容及样式
        HSSFCell cell000 = row2.createCell(0);
        cell000.setCellStyle(style);
        cell000.setCellValue("序号");

        HSSFCell cell001 = row2.createCell(1);
        cell001.setCellStyle(style);
        cell001.setCellValue("商品名称");

        HSSFCell cell002 = row2.createCell(2);
        cell002.setCellStyle(style);
        cell002.setCellValue("条形码");

        HSSFCell cell003 = row2.createCell(3);
        cell003.setCellStyle(style);
        cell003.setCellValue("单位");

        HSSFCell cell004 = row2.createCell(4);
        cell004.setCellStyle(style);
        cell004.setCellValue("品牌");

        HSSFCell cell005 = row2.createCell(5);
        cell005.setCellStyle(style);
        cell005.setCellValue("销售数量");

        HSSFCell cell006 = row2.createCell(6);
        cell006.setCellStyle(style);
        cell006.setCellValue("平均销售单价");

        HSSFCell cell007 = row2.createCell(7);
        cell007.setCellStyle(style);
        cell007.setCellValue("销售金额");

        HSSFCell cell008 = row2.createCell(8);
        cell008.setCellStyle(style);
        cell008.setCellValue("退货数量");

        HSSFCell cell009 = row2.createCell(9);
        cell009.setCellStyle(style);
        cell009.setCellValue("退货金额");
        //单元格宽度自适应
        sheet.autoSizeColumn((short) 3);
        sheet.autoSizeColumn((short) 4);
        sheet.autoSizeColumn((short) 5);
        sheet.autoSizeColumn((short) 6);
        sheet.autoSizeColumn((short) 7);
        sheet.autoSizeColumn((short) 8);
        sheet.autoSizeColumn((short) 9);

        //宽度自适应可自行选择自适应哪一行，这里写在前面的是适应第二行，写在后面的是适应第三行
        for (int i = 0; i < solist.size(); i++) {
        //单元格宽度自适应
        sheet.autoSizeColumn((short) 0);
        sheet.autoSizeColumn((short) 1);
        sheet.autoSizeColumn((short) 2);
        //从sheet第三行开始填充数据
        HSSFRow rowx = sheet.createRow(i + 2);
        Map<String, Object> map = solist.get(i);
        //这里的hospitalid,idnumber等都是前面定义的全局变量
        Integer id = (Integer) map.get("id");
        HSSFCell cell00 = rowx.createCell(0);
        cell00.setCellStyle(style);
        cell00.setCellValue(id);

        String productName = (String) map.get("productName");
        HSSFCell cell01 = rowx.createCell(1);
        cell01.setCellStyle(style);
        cell01.setCellValue(productName);

        String sn = (String) map.get("sn");
        HSSFCell cell02 = rowx.createCell(2);
        cell02.setCellStyle(style);
        cell02.setCellValue(sn);

        String unit = (String) map.get("unit");
        HSSFCell cell03 = rowx.createCell(3);
        cell03.setCellStyle(style);
        cell03.setCellValue(unit);

        String brand = (String) map.get("brand");
        HSSFCell cell04 = rowx.createCell(4);
        cell04.setCellStyle(style);
        cell04.setCellValue(brand);

        Integer amountTotal = (Integer) map.get("amountTotal");
        HSSFCell cell05 = rowx.createCell(5);
        cell05.setCellStyle(style);
        cell05.setCellValue(amountTotal);

        BigDecimal avgSalePrice = (BigDecimal) map.get("avgSalePrice");
        HSSFCell cell06 = rowx.createCell(6);
        cell06.setCellStyle(style);
        cell06.setCellValue((RichTextString) avgSalePrice);

        BigDecimal moneyTotal = (BigDecimal) map.get("moneyTotal");
        HSSFCell cell07 = rowx.createCell(7);
        cell07.setCellStyle(style);
        cell07.setCellValue((RichTextString) moneyTotal);

        Integer  returnNumTotal = (Integer ) map.get("returnNumTotal");
        HSSFCell cell08 = rowx.createCell(8);
        cell08.setCellStyle(style);
        cell08.setCellValue(returnNumTotal);

        BigDecimal returnMoneyTotle = (BigDecimal) map.get("returnMoneyTotle");
        HSSFCell cell09 = rowx.createCell(9);
        cell09.setCellStyle(style);
        cell09.setCellValue((RichTextString) returnMoneyTotle);


        }
        //输出Excel文件
        response.setCharacterEncoding("utf-8");
        OutputStream output = response.getOutputStream();
        response.reset();
        response.setHeader("Content-disposition", "attachment; filename=商品销售统计.xls");//文件名这里可以改
        response.setContentType("application/msexcel");
        wb.write(output);
        output.close();
        return null;*/
