package com.stylefeng.guns.modular.cancelOrder;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.Tip;
import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.modular.common.ConstCheck;
import com.stylefeng.guns.modular.common.FlowUtil;
import com.stylefeng.guns.modular.ecErpOrder.service.IEcErpOrderService;
import com.stylefeng.guns.modular.erpSaleOrder.service.impl.ErpSaleOrderSeriverImpWrap;
import com.stylefeng.guns.modular.exOrder.service.IExOrderService;
import com.stylefeng.guns.modular.exOrderExam.service.IExOrderExamService;
import com.stylefeng.guns.modular.orderItem.service.IOrderItemService;
import com.stylefeng.guns.modular.ro.OrderItemRo;
import com.stylefeng.guns.modular.ro.PayModeBean;
import com.stylefeng.guns.modular.system.dao.OrderItemMapper;
import com.stylefeng.guns.modular.system.model.EcErpOrder;
import com.stylefeng.guns.modular.system.model.ErpSaleOrder;
import com.stylefeng.guns.modular.system.model.ExOrder;
import com.stylefeng.guns.modular.system.model.ExOrderItem;
import com.stylefeng.guns.modular.system.service.IDeptService;
import com.stylefeng.guns.modular.system.service.IExOrderItemService;
import com.stylefeng.guns.modular.vo.EcErpOrderVo;
import com.stylefeng.guns.modular.vo.OrderItemSumVo;
import com.stylefeng.guns.modular.vo.OrderItemVo;
import com.stylefeng.guns.modular.vo.OrderNumberVo;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Controller
@RequestMapping("/cancelOrder/caiwu")
public class CaiwuController extends BaseController {

    private String PREFIX = "/cancel/order/";

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

    @Autowired
    private ErpSaleOrderSeriverImpWrap erpSaleOrderSeriverImpWrap;

    @Autowired
    private IExOrderItemService exOrderItemService;

    /**
     * 跳转到财务订单管理首页
     */
    @RequestMapping("")
    public String caiwu() {
        return PREFIX + "caiwu_orderList.html";
    }

    /**
     * 商品销售统计
     *
     * @param page
     * @return
     */
    @RequestMapping("/saleCollect")
    @ResponseBody
    public OrderItemSumVo salellectList(Page page, Date startTime, Date endTime) {
        Wrapper wrapper = new EntityWrapper<ExOrder>().between("check_time", startTime, endTime).eq("if_check", true);
        List<EcErpOrder> ecErpOrderList = ecErpOrderService.selectList(wrapper);
        //得到订单id集合
        List<Long> ecErpOrderidList = new ArrayList<>();
        for (EcErpOrder ecErpOrder : ecErpOrderList) {
            ecErpOrderidList.add(ecErpOrder.getId());
        }
        OrderItemSumVo orderItemSumVo = new OrderItemSumVo();

        if (ecErpOrderidList.size() == 0) {
            return orderItemSumVo;
        }
        //得到商品表单
        List<OrderItemVo> orderItemVoList = orderItemMapper.selectReportFormList(ecErpOrderidList);
        orderItemSumVo = orderItemMapper.selectReportForm(ecErpOrderidList);
        orderItemSumVo.setOrderItemVoList(orderItemVoList);
        //得到退单率
        wrapper.eq("return_goods_flag", true);
        List<EcErpOrder> exOrderList = ecErpOrderService.selectList(wrapper);

        System.out.println(exOrderList.size());
        System.out.println(ecErpOrderList.size());
        BigDecimal returnBillRate = new BigDecimal(exOrderList.size()).divide(new BigDecimal(ecErpOrderList.size()), 3, BigDecimal.ROUND_HALF_DOWN);
        orderItemSumVo.setReturnBillRate(returnBillRate);
        //得到核销金额
        orderItemSumVo.setCheckMoneyTotal(orderItemMapper.selectCheckMoney(ecErpOrderidList));
        return orderItemSumVo;
    }


    /**
     * 商品销售统计(导出功能)
     *
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping("/reportImport")
    public String reportImport(Date startTime, Date endTime, HttpServletResponse response) throws IOException {

        HSSFWorkbook workbook = new HSSFWorkbook();//Workbook工作簿，HSSF - 提供读写Microsoft Excel格式档案的功能。
        HSSFSheet sheet = workbook.createSheet("信息表");//sheet表格,servlet服务器小程序
        String fileName = "商品销售统计" + ".xls";//设置要导出的文件的名字
        // 生成一个表格样式(表头样式)
        HSSFCellStyle style0 = workbook.createCellStyle();
        // 设置单元格背景样式颜色
        style0.setFillPattern(FillPatternType.SOLID_FOREGROUND);// 普通颜色填充
        style0.setFillForegroundColor(HSSFColor.HSSFColorPredefined.ORANGE.getIndex());// 蓝色背景
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
        font0.setFontName("黑体"); // 字体
        font0.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        font0.setBold(true);// 加粗
        style0.setFont(font0);// 把字体应用到当前样式
        // 生成一个表格样式(表头样式)
        HSSFCellStyle style1 = workbook.createCellStyle();
        // 设置单元格背景样式颜色
        style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);// 普通颜色填充
        style1.setFillForegroundColor(HSSFColor.HSSFColorPredefined.YELLOW.getIndex());// 蓝色背景
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
        style1.setTopBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());// 顶部边框线颜色
        style1.setWrapText(true);// 自动换行
        // 设置字体
        HSSFFont font1 = workbook.createFont();// 生成一个字体样式
        font1.setFontHeightInPoints((short) 20); // 字体高度(大小)
        font1.setFontName("宋体"); // 字体
        font1.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        font1.setBold(true);// 加粗
        style1.setFont(font1);// 把字体应用到当前样式

        // 生成一个表格样式(列表样式---非表头)
        HSSFCellStyle style2 = workbook.createCellStyle();
        // 设置单元格背景样式颜色
        style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);// 普通颜色填充
        style2.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_GREEN.getIndex());// 蓝色背景
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
        font2.setFontHeightInPoints((short) 14); // 字体高度(大小)
        font2.setFontName("楷体"); // 字体
        font2.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        font2.setBold(true);// 加粗
        style2.setFont(font2);// 把字体应用到当前样式
        sheet.addMergedRegion(new CellRangeAddress(0, 2, 0, 9));
        String[] head = {"商品销售统计"};
        HSSFRow row4 = sheet.createRow(0);
        for (int q = 0; q < head.length; q++) {
            HSSFCell cell3 = row4.createCell(q); //Cell表格最小单位
            HSSFRichTextString text = new HSSFRichTextString(head[q]);
            cell3.setCellValue(text);
            cell3.setCellStyle(style0);
        }

        String[] headers = {"序号", "商品名称", "条形码", "单位", "品牌", "销售数量", "平均销售单价", "销售金额", "退货数量", "退货金额"};
        //headers表示excel表中第一行的表头
        HSSFRow row0 = sheet.createRow(3);
        //在excel表中添加表头
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row0.createCell(i); //Cell表格最小单位
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
            cell.setCellStyle(style1);
        }

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
        int num = 4;
        //在excel表中添加订单项
        for (int i = 0; i < orderItemVoList.size(); i++) {
            OrderItemVo orderItemVo = orderItemVoList.get(i);
            HSSFRow row = sheet.createRow(i + num);
            HSSFCell cell = null;
            row.createCell(0).setCellValue(i + 1);//序号
            row.createCell(1).setCellValue(orderItemVo.getProductName());//商品名称
            row.createCell(2).setCellValue(orderItemVo.getSn());//条形码
            row.createCell(3).setCellValue(orderItemVo.getUnit());//单位
            row.createCell(4).setCellValue(orderItemVo.getBrand());//品牌
            row.createCell(5).setCellValue(orderItemVo.getAmountTotal());//销售数量
            row.createCell(6).setCellValue(orderItemVo.getAvgSalePrice().toString());//平均销售单价
            row.createCell(7).setCellValue(orderItemVo.getMoneyTotal().toString());//销售金额
            row.createCell(8).setCellValue(orderItemVo.getReturnNumTotal() == null ? 0 : orderItemVo.getReturnNumTotal().doubleValue());//退货数量
            row.createCell(9).setCellValue(1);//退货金额
            for (int q = 0; q < 10; q++) {
                cell = row.getCell(q);
                cell.setCellStyle(style2);
            }
            System.out.println();
        }
        sheet.autoSizeColumn((short) 0);
        sheet.autoSizeColumn((short) 1);
        sheet.autoSizeColumn((short) 2);
        sheet.autoSizeColumn((short) 3);
        sheet.autoSizeColumn((short) 4);
        sheet.autoSizeColumn((short) 5);
        sheet.autoSizeColumn((short) 6);
        sheet.autoSizeColumn((short) 7);
        sheet.autoSizeColumn((short) 8);
        sheet.autoSizeColumn((short) 9);
        response.setCharacterEncoding("utf-8");
        OutputStream output = response.getOutputStream();
        response.reset();
        response.setHeader("Content-disposition", "attachment; filename=" + java.net.URLDecoder.decode(fileName));
        response.setContentType("application/msexcel");
        workbook.write(output);
        output.close();

        return null;
    }


    /**
     * 财务查询列表，查询所有已出库订单
     *
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object caiwuList(Integer pageNumber, Integer pageSize, Date startTime, Date endTime, String content) {
        Page page = new Page(pageNumber, pageSize);
        Wrapper wrapper = new EntityWrapper();
        wrapper.orderBy("createdate", false);
        if (startTime != null && endTime != null) {
            wrapper.between("createdate", startTime, endTime);
        }

        if (StringUtils.isNotBlank(content)) {
            wrapper.andNew().like("sale_no", content).or().like("customer_name", content);
        }

        Page<EcErpOrder> ecErpOrderPage = ecErpOrderService.selectPage(page, wrapper);
        List<EcErpOrderVo> ecErpOrderVos = new ArrayList<>();
        for (EcErpOrder ecErpOrder : ecErpOrderPage.getRecords()) {
            //关联异常状态
            Map<String, Object> map = new HashMap<>();
            map.put("ec_erp_order_id", ecErpOrder.getId());
            List<ExOrder> exOrderList = exOrderService.selectByMap(map);
            String flowInfo = "";
            Boolean returnAllSign = false;
            for (ExOrder exOrder : exOrderList) {
                if (exOrder.getExamDepId() == 0) {
                    continue;
                }
                //得到异常类型
                String flowName = FlowUtil.getFlowName(exOrder.getFlowId());
                flowInfo += "待" + iDeptService.selectById(exOrder.getExamDepId()).getSimplename() + "审核" + "(" + flowName + ")" + "<br/>";

                if (exOrder.getExType() == 3 || exOrder.getExType() == 4) {
                    returnAllSign = true;
                }
            }
            EcErpOrderVo ecErpOrderVo = EcErpOrderVo.adapt(ecErpOrder);
            //todo
            //重要数据库存数据的时候这些数据不能为空
            if (ecErpOrder.getIfOutStore() != null && ecErpOrder.getIfOutStore().equals(true)) {
                ecErpOrderVo.setReturnAllButton(true);
                ecErpOrderVo.setReturnSingleButton(true);
                ecErpOrderVo.setRedPacketButton(true);
                ecErpOrderVo.setCheckButton(true); //显示核销按钮，异常状态不显示核销按钮
                ecErpOrderVo.setOrderStateInfo("已出库");
            } else {
                ecErpOrderVo.setOrderStateInfo("订单已关闭");

            }
            //假如是异常订单就显示异常信息
            if (!flowInfo.equals("")) {
                ecErpOrderVo.setOrderStateInfo(flowInfo);
                ecErpOrderVo.setCheckButton(false);
            }
            //订单已核销或者退单隐藏所有按钮
            if ((ecErpOrderVo.getIfCheck() != null && ecErpOrderVo.getIfCheck() == true) || (returnAllSign == true)) {
                ecErpOrderVo.setReturnSingleButton(false);
                ecErpOrderVo.setRedPacketButton(false);
                ecErpOrderVo.setCheckButton(false);
                ecErpOrderVo.setExamButton(false);
                if (returnAllSign == false) {
                    ecErpOrderVo.setReturnAllButton(false);
                } else {
                    ecErpOrderVo.setReturnAllButton(true);
                }
                if (ecErpOrderVo.getIfCheck() != null && ecErpOrderVo.getIfCheck() == true) {
                    ecErpOrderVo.setOrderStateInfo("此订单已核销");
                }
        }

            if(ecErpOrderVo.getIfClose()==true){
                ecErpOrderVo.setOrderStateInfo("此订单已关闭");
                ecErpOrderVo.setReturnSingleButton(false);
                ecErpOrderVo.setReturnAllButton(false);
                ecErpOrderVo.setRedPacketButton(false);
                ecErpOrderVo.setCheckButton(false);
                ecErpOrderVo.setExamButton(false);
            }
            ecErpOrderVos.add(ecErpOrderVo);
        }
        //bootstrap-table要求服务器返回的json须包含：total，rows
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", ecErpOrderPage.getTotal());
        map.put("rows", ecErpOrderVos);
        return map;
    }


    /**
     * 全部订单(导出)
     */
    @RequestMapping("/allExport")
    @ResponseBody
    public String allExport(Date startTime, Date endTime, String content, HttpServletResponse response) throws IOException {
//        LocalDate date1 = LocalDate.of(2018, 12, 12);
//        LocalDate date2 = LocalDate.of(2018, 12, 28);
//        ZoneId zoneId = ZoneId.systemDefault();
//        ZonedDateTime zdt1 = date1.atStartOfDay(zoneId);
//        startTime = Date.from(zdt1.toInstant());
//        ZonedDateTime zdt2 = date2.atStartOfDay(zoneId);
//        endTime=Date.from(zdt2.toInstant());

        HSSFWorkbook workbook = new HSSFWorkbook();//Workbook工作簿，HSSF - 提供读写Microsoft Excel格式档案的功能。
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
        sheet.addMergedRegion(new CellRangeAddress(0, 2, 0, 25));
        //表格名称
        String[] head = {"全部订单"};
        HSSFRow row0 = sheet.createRow(0);
        for (int q = 0; q < head.length; q++) {
            HSSFCell cell0 = row0.createCell(q); //Cell表格最小单位
            HSSFRichTextString text = new HSSFRichTextString(head[q]);
            cell0.setCellValue(text);
            cell0.setCellStyle(style0);
        }
        // 生成一个表格样式(表头样式)
        HSSFCellStyle style1 = workbook.createCellStyle();
        // 设置单元格背景样式颜色
        style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);// 普通颜色填充
        style1.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_ORANGE.getIndex());
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

        String[] headers = {"序号", "销售单号", "销售日期", "客户", "红包", "金额", "支付方式", "备注", "核销金额", "现金",
                            "支付宝","公司微信","张新微信","小程序", "合并支付","余额支付","不收钱","审核退货金额","退货退款方式",
                            "退货退款金额", "退红包方式", "退红包金额", "异常金额", "交款人", "核销时间","是否核销"};
        //headers表示excel表中第一行的表头
        HSSFRow row1 = sheet.createRow(3);
        //在excel表中添加表头
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row1.createCell(i); //Cell表格最小单位
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
            cell.setCellStyle(style1);
        }
        // 生成一个表格样式(表头样式)
        HSSFCellStyle style2 = workbook.createCellStyle();
        // 设置单元格背景样式颜色
        style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);// 普通颜色填充
        style2.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_GREEN.getIndex());
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

        //得到全部订单

        Wrapper wrapper = new EntityWrapper();
        wrapper.orderBy("createdate", true);
        if (startTime != null && endTime != null) {
            wrapper.between("createdate", startTime, endTime).eq("if_close", false);
        }

        if (StringUtils.isNotBlank(content)) {
            wrapper.andNew().like("sale_no", content).or().like("customer_name", content);
        }
        List<EcErpOrder> ecErpOrderVoList = ecErpOrderService.selectList(wrapper);

        //在excel表中添加订单项
        for (int i = 0; i < ecErpOrderVoList.size(); i++) {
            EcErpOrder ecErpOrder = ecErpOrderVoList.get(i);
            HSSFRow row = sheet.createRow(i + 4);
            HSSFCell cell = null;
            row.createCell(0).setCellValue(i + 1);//序号
            row.createCell(1).setCellValue(ecErpOrder.getSaleNo());//销售单号
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = formatter.format(ecErpOrder.getCreatedate());
            row.createCell(2).setCellValue(dateString);//销售日期
            row.createCell(3).setCellValue(ecErpOrder.getCustomerName());//客户名称
            row.createCell(4).setCellValue(ecErpOrder.getDiscountAmount() != null ? ecErpOrder.getDiscountAmount().toString() : "");//红包金额
            row.createCell(5).setCellValue(ecErpOrder.getActualAmount().toString());//订单金额
            row.createCell(6).setCellValue(ecErpOrder.getPayType());//付款方式
            row.createCell(7).setCellValue(ecErpOrder.getCheckNode());//备注
            row.createCell(8).setCellValue("");//核销金额
            row.createCell(9).setCellValue("");//现金
            row.createCell(10).setCellValue("");//支付宝
            row.createCell(11).setCellValue("");//公司微信
            row.createCell(12).setCellValue("");//张新微信
            row.createCell(13).setCellValue("");//小程序
            row.createCell(14).setCellValue("");//合并支付
            row.createCell(15).setCellValue("");//余额支付
            row.createCell(16).setCellValue("");//不收钱
            row.createCell(17).setCellValue("");//审核退货金额
            if (ecErpOrder.getIfCheck() == true) {
                row.createCell(8).setCellValue(ecErpOrder.getCheckMoney().toString());//核销金额
                List<PayModeBean> payModeList = (List<PayModeBean>) JSONArray.toList(JSONArray.fromObject(ecErpOrder.getPayMode()), PayModeBean.class);
                for (PayModeBean payModeBean : payModeList) {
                    if (payModeBean.getPayType().equals("现金")) {
                        row.createCell(9).setCellValue(payModeBean.getAmoun().toString());//现金
                    }
                    if (payModeBean.getPayType().equals("支付宝")) {
                        row.createCell(10).setCellValue(payModeBean.getAmoun().toString());//支付宝
                    }
                    if (payModeBean.getPayType().equals("公司微信")) {
                        row.createCell(11).setCellValue(payModeBean.getAmoun().toString());//公司微信
                    }

                    if (payModeBean.getPayType().equals("张新微信")) {
                        row.createCell(12).setCellValue(payModeBean.getAmoun().toString());//张新微信
                    }
                    if (payModeBean.getPayType().equals("小程序")) {
                        row.createCell(13).setCellValue(payModeBean.getAmoun().toString());//小程序
                    }
                    if (payModeBean.getPayType().equals("合并支付")) {
                        row.createCell(14).setCellValue(payModeBean.getAmoun().toString());//合并支付
                    }
                    if (payModeBean.getPayType().equals("余额支付")) {
                        row.createCell(15).setCellValue(payModeBean.getAmoun().toString());//余额支付
                    }
                    if (payModeBean.getPayType().equals("不收钱")) {
                        row.createCell(16).setCellValue(payModeBean.getAmoun().toString());//不收钱
                    }
                    if (payModeBean.getPayType().equals("审核退货金额")) {
                        row.createCell(17).setCellValue(payModeBean.getAmoun().toString());//审核退货金额
                    }
                }
                row.createCell(18).setCellValue(ecErpOrder.getReturnSingleType());//退货退款方式
                row.createCell(19).setCellValue(ecErpOrder.getRefundMoney() != null ? ecErpOrder.getRefundMoney().toString() : "");//退货退款金额
                row.createCell(20).setCellValue(ecErpOrder.getReturnRedType());//退红包方式
                row.createCell(21).setCellValue(ecErpOrder.getReturnRedMoney() != null ? ecErpOrder.getReturnRedMoney().toString() : "");//退红包金额
                row.createCell(22).setCellValue(ecErpOrder.getExMoney() != null ? ecErpOrder.getExMoney().toString() : "");//异常金额
                row.createCell(23).setCellValue(ecErpOrder.getPayer());//交款人
                row.createCell(24).setCellValue(formatter.format(ecErpOrder.getCheckTime()));//核销时间
                row.createCell(25).setCellValue("已核销");
            }else{
                row.createCell(18).setCellValue("");//退货退款方式
                row.createCell(19).setCellValue("");//退货退款金额
                row.createCell(20).setCellValue("");//退红包方式
                row.createCell(21).setCellValue("");//退红包金额
                row.createCell(22).setCellValue("");//异常金额
                row.createCell(23).setCellValue("");//交款人
                row.createCell(24).setCellValue("");//核销时间
                row.createCell(25).setCellValue("");

            }
//            row.getPhysicalNumberOfCells()
            for (int j = 0; j <row.getPhysicalNumberOfCells() ; j++) {
                cell = row.getCell(j);
                cell.setCellStyle(style2);
            }
        }
        //设置自适应宽度
        for (int i = 0; i < 26; i++) {
            sheet.autoSizeColumn(i);
        }
        //根据浏览器不同将文本进行编码
        String fileName = "全部订单" + ".xls";//设置要导出的文件的名字
        fileName = URLEncoder.encode(fileName, "utf-8");
        fileName = fileName.replace("+", " ");

        response.setCharacterEncoding("utf-8");
        OutputStream output = response.getOutputStream();
        response.reset();
        response.setHeader("Content-disposition", "attachment; filename=" + fileName);
        response.setContentType("application/msexcel");
        workbook.write(output);
        output.close();
        return null;
    }

    /**
     * 主页各类型订单数量
     */
    @RequestMapping("/orderNumber")
    @ResponseBody
    public OrderNumberVo orderNumber() {
        OrderNumberVo orderNumberVo = new OrderNumberVo();
        ShiroUser shiroUser = (ShiroUser) this.getSession().getAttribute("shiroUser");
        Integer depId = ((ShiroUser) this.getSession().getAttribute("shiroUser")).getDeptId();

        //全部订单
        long startTimeallSize = System.currentTimeMillis();    //获取开始时间

        int allSize = erpSaleOrderSeriverImpWrap.selectCount(null);
        orderNumberVo.setAllAllNumber(allSize);

        long endTimeallSize = System.currentTimeMillis();    //获取结束时间




        //查询总数
        long startTimeall = System.currentTimeMillis();    //获取开始时间

//        List<EcErpOrder> all = ecErpOrderService.selectList(null);
//        orderNumberVo.setAllNumber(all.size());

        Integer all = ecErpOrderService.selectCount(null);
        orderNumberVo.setAllNumber(all);

        long endTimeall = System.currentTimeMillis();    //获取结束时间



        //已核销
        long startTimeyhxList = System.currentTimeMillis();    //获取开始时间

        Wrapper yhxWrapper = new EntityWrapper();
        yhxWrapper.eq("if_check", true);
//        List<EcErpOrder> yhxList = ecErpOrderService.selectList(yhxWrapper);
//        orderNumberVo.setCheckNumber(yhxList.size());
        Integer yhxList = ecErpOrderService.selectCount(yhxWrapper);
        orderNumberVo.setCheckNumber(yhxList);


        long endTimeyhxList = System.currentTimeMillis();    //获取结束时间

        //待核销
        long startTimedhxList = System.currentTimeMillis();    //获取开始时间

        Wrapper dhxWrapper = new EntityWrapper<ExOrder>().eq("if_check", false)
                .eq("ex_order_sign", false).eq("if_out_store", true);
//        List<EcErpOrder> dhxList = ecErpOrderService.selectList(dhxWrapper);
//        orderNumberVo.setNotCheckNumber(dhxList.size());

         Integer dhxList = ecErpOrderService.selectCount(dhxWrapper);
        orderNumberVo.setNotCheckNumber(dhxList);

        long endTimedhxList = System.currentTimeMillis();    //获取结束时间

        //已审核
        long startTimeexOrderExamList = System.currentTimeMillis();    //获取开始时间

        Wrapper yshWrapper = new EntityWrapper();
        yshWrapper.eq("sys_dept_id", depId).eq("if_launch", false);
//        List<ExOrderExam> exOrderExamList = iExOrderExamService.selectList(yshWrapper);
//        orderNumberVo.setExamNumber(exOrderExamList.size());

        Integer exOrderExamList = iExOrderExamService.selectCount(yshWrapper);
        orderNumberVo.setExamNumber(exOrderExamList);

        long endTimeexOrderExamList = System.currentTimeMillis();    //获取结束时间

        //未审核
        long startTimeexOrderList = System.currentTimeMillis();    //获取开始时间

        Wrapper dshWrapper = new EntityWrapper<ExOrder>().eq("exam_dep_id", depId);
//        List<ExOrder> exOrderList = exOrderService.selectList(dshWrapper);
//        orderNumberVo.setNotExamNumber(exOrderList.size());

        Integer exOrderList = exOrderService.selectCount(dshWrapper);
        orderNumberVo.setNotExamNumber(exOrderList);

        long endTimeexOrderList = System.currentTimeMillis();    //获取结束时间

        //已发起审核(未结束)

        long startTimeintiList = System.currentTimeMillis();    //获取开始时间

        Wrapper initWrapper = new EntityWrapper<ExOrder>().eq("ex_state", true);
//        List<ExOrder> intiList = exOrderService.selectList(initWrapper);
//        orderNumberVo.setInitExamNumber(intiList.size());

        Integer intiList = exOrderService.selectCount(initWrapper);
        orderNumberVo.setInitExamNumber(intiList);

        long endTimeintiList = System.currentTimeMillis();    //获取结束时间



        System.out.println("全部订单allSize程序运行时间：" + (endTimeallSize - startTimeallSize) + "ms");    //输出程序运行时间
        System.out.println("查询总数all程序运行时间：" + (endTimeall - startTimeall) + "ms");    //输出程序运行时间
        System.out.println("已核销yhxList程序运行时间：" + (endTimeyhxList - startTimeyhxList) + "ms");    //输出程序运行时间
        System.out.println("已审核exOrderExamList程序运行时间：" + (endTimeexOrderExamList - startTimeexOrderExamList) + "ms");    //输出程序运行时间
        System.out.println("待核销dhxList程序运行时间：" + (endTimedhxList - startTimedhxList) + "ms");    //输出程序运行时间
        System.out.println("未审核exOrderList程序运行时间：" + (endTimeexOrderList - startTimeexOrderList) + "ms");    //输出程序运行时间
        System.out.println("已发起审核(未结束)intiList程序运行时间：" + (endTimeintiList - startTimeintiList) + "ms");    //输出程序运行时间



        return orderNumberVo;
    }


    /**
     * 已核销(导出)
     */
    @RequestMapping("/yhxExport")
    @ResponseBody
    public String yhxExport(Date startTime, Date endTime, String content, HttpServletResponse response, @RequestParam(defaultValue = "1") Integer tFlag) throws IOException {
//        LocalDate date1 = LocalDate.of(2018, 12, 12);
//        LocalDate date2 = LocalDate.of(2018, 12, 28);
//        ZoneId zoneId = ZoneId.systemDefault();
//        ZonedDateTime zdt1 = date1.atStartOfDay(zoneId);
//        startTime = Date.from(zdt1.toInstant());
//        ZonedDateTime zdt2 = date2.atStartOfDay(zoneId);
//        endTime=Date.from(zdt2.toInstant());
        Map map = (Map) yhx(1, Integer.MAX_VALUE, startTime, endTime, content, tFlag);
        List<EcErpOrderVo> ecErpOrderVoList = (List<EcErpOrderVo>) map.get("rows");
        HSSFWorkbook workbook = new HSSFWorkbook();//Workbook工作簿，HSSF - 提供读写Microsoft Excel格式档案的功能。
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

        sheet.addMergedRegion(new CellRangeAddress(0, 2, 0, 24));
        //表格名称
        String[] head = {"已核销订单"};
        HSSFRow row0 = sheet.createRow(0);
        for (int q = 0; q < head.length; q++) {
            HSSFCell cell0 = row0.createCell(q); //Cell表格最小单位
            HSSFRichTextString text = new HSSFRichTextString(head[q]);
            cell0.setCellValue(text);
            cell0.setCellStyle(style0);
        }
        // 生成一个表格样式(表头样式)
        HSSFCellStyle style1 = workbook.createCellStyle();
        // 设置单元格背景样式颜色
        style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);// 普通颜色填充
        style1.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_ORANGE.getIndex());
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

        String[] headers = {"序号", "销售单号", "销售日期", "客户", "红包", "金额", "支付方式", "备注", "核销金额", "现金",
                "支付宝","公司微信","张新微信","小程序", "合并支付","余额支付","不收钱","审核退货金额","退货退款方式",
                "退货退款金额", "退红包方式", "退红包金额", "异常金额", "交款人", "核销时间"};
        //headers表示excel表中第一行的表头
        HSSFRow row1 = sheet.createRow(3);
        //在excel表中添加表头
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row1.createCell(i); //Cell表格最小单位
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
            cell.setCellStyle(style1);
        }
        // 生成一个表格样式(表头样式)
        HSSFCellStyle style2 = workbook.createCellStyle();
        // 设置单元格背景样式颜色
        style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);// 普通颜色填充
        style2.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_GREEN.getIndex());
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
        //在excel表中添加订单项
        for (int i = 0; i < ecErpOrderVoList.size(); i++) {
            EcErpOrder ecErpOrder = ecErpOrderVoList.get(i);
            HSSFRow row = sheet.createRow(i + 4);
            HSSFCell cell = null;
            row.createCell(0).setCellValue(i + 1);//序号
            row.createCell(1).setCellValue(ecErpOrder.getSaleNo());//销售单号
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = formatter.format(ecErpOrder.getCreatedate());
            row.createCell(2).setCellValue(dateString);//销售日期
            row.createCell(3).setCellValue(ecErpOrder.getCustomerName());//客户名称
            row.createCell(4).setCellValue(ecErpOrder.getDiscountAmount() != null ? ecErpOrder.getDiscountAmount().toString() : "");//红包金额
            row.createCell(5).setCellValue(ecErpOrder.getActualAmount().toString());//订单金额
            row.createCell(6).setCellValue(ecErpOrder.getPayType());//付款方式
            row.createCell(7).setCellValue(ecErpOrder.getCheckNode());//备注
            row.createCell(8).setCellValue(ecErpOrder.getCheckMoney().toString());//核销金额
            row.createCell(9).setCellValue("");//现金
            row.createCell(10).setCellValue("");//支付宝
            row.createCell(11).setCellValue("");//公司微信
            row.createCell(12).setCellValue("");//张新微信
            row.createCell(13).setCellValue("");//小程序
            row.createCell(14).setCellValue("");//合并支付
            row.createCell(15).setCellValue("");//余额支付
            row.createCell(16).setCellValue("");//不收钱
            row.createCell(17).setCellValue("");//审核退货金额

            List<PayModeBean> payModeList = (List<PayModeBean>) JSONArray.toList(JSONArray.fromObject(ecErpOrder.getPayMode()), PayModeBean.class);
            for (PayModeBean payModeBean : payModeList) {
                if (payModeBean.getPayType().equals("现金")) {
                    row.getCell(9).setCellValue(payModeBean.getAmoun().toString());//现金
                }
                if (payModeBean.getPayType().equals("支付宝")) {
                    row.getCell(10).setCellValue(payModeBean.getAmoun().toString());//张新微信
                }
                if (payModeBean.getPayType().equals("公司微信")) {
                    row.getCell(11).setCellValue(payModeBean.getAmoun().toString());//支付宝

                }
                if (payModeBean.getPayType().equals("张新微信")) {
                    row.getCell(12).setCellValue(payModeBean.getAmoun().toString());//微信
                }
                if (payModeBean.getPayType().equals("小程序")) {
                    row.getCell(13).setCellValue(payModeBean.getAmoun().toString());//微信
                }
                if (payModeBean.getPayType().equals("合并支付")) {
                    row.getCell(14).setCellValue(payModeBean.getAmoun().toString());//微信
                }
                if (payModeBean.getPayType().equals("余额支付")) {
                    row.getCell(15).setCellValue(payModeBean.getAmoun().toString());//微信
                }
                if (payModeBean.getPayType().equals("不收钱")) {
                    row.getCell(16).setCellValue(payModeBean.getAmoun().toString());//微信
                }
                if (payModeBean.getPayType().equals("审核退货金额")) {
                    row.getCell(17).setCellValue(payModeBean.getAmoun().toString());//微信
                }
//                row.getCell(13).setCellValue(payModeBean.getAmoun().toString());//其他支付方式金额
            }
            row.createCell(18).setCellValue(ecErpOrder.getReturnSingleType());//退货退款方式
            row.createCell(19).setCellValue(ecErpOrder.getRefundMoney() != null ? ecErpOrder.getRefundMoney().toString() : "");//退货退款金额
            row.createCell(20).setCellValue(ecErpOrder.getReturnRedType());//退红包方式
            row.createCell(21).setCellValue(ecErpOrder.getReturnRedMoney() != null ? ecErpOrder.getReturnRedMoney().toString() : "");//退红包金额
            row.createCell(22).setCellValue(ecErpOrder.getExMoney() != null ? ecErpOrder.getExMoney().toString() : "");//异常金额
            row.createCell(23).setCellValue(ecErpOrder.getPayer());//交款人
            row.createCell(24).setCellValue(formatter.format(ecErpOrder.getCheckTime()));//核销时间
            for (int j = 0; j < 25; j++) {
                cell = row.getCell(j);
                cell.setCellStyle(style2);
            }
        }

        //设置自适应宽度
        for (int i = 0; i < 25; i++) {
            sheet.autoSizeColumn(i);
        }

        //通过获取请求头中浏览器信息

        String agent = getHttpServletRequest().getHeader("User-Agent");

        //根据浏览器不同将文本进行编码
        String fileName = "已核销订单" + ".xls";//设置要导出的文件的名字
        fileName = URLEncoder.encode(fileName, "utf-8");
        fileName = fileName.replace("+", " ");

        response.setCharacterEncoding("utf-8");
        OutputStream output = response.getOutputStream();
        response.reset();
        response.setHeader("Content-disposition", "attachment; filename=" + fileName);
        response.setContentType("application/msexcel");
        workbook.write(output);
        output.close();
        return null;
    }


    /**
     * 待核销(导出)
     */
    @RequestMapping("/dhxExport")
    @ResponseBody
    public String dhxExport(Date startTime, Date endTime, String content, HttpServletResponse response) throws IOException {
        LocalDate date1 = LocalDate.of(2018, 12, 12);
        LocalDate date2 = LocalDate.of(2018, 12, 28);
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt1 = date1.atStartOfDay(zoneId);
        startTime = Date.from(zdt1.toInstant());
        ZonedDateTime zdt2 = date2.atStartOfDay(zoneId);
        endTime = Date.from(zdt2.toInstant());
        Map map = (Map) dhx(1, Integer.MAX_VALUE, startTime, endTime, content);
        List<EcErpOrderVo> ecErpOrderVoList = (List<EcErpOrderVo>) map.get("rows");
        HSSFWorkbook workbook = new HSSFWorkbook();//Workbook工作簿，HSSF - 提供读写Microsoft Excel格式档案的功能。
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

        sheet.addMergedRegion(new CellRangeAddress(0, 2, 0, 5));
        //表格名称
        String[] head = {"未核销订单"};
        HSSFRow row0 = sheet.createRow(0);
        for (int q = 0; q < head.length; q++) {
            HSSFCell cell0 = row0.createCell(q); //Cell表格最小单位
            HSSFRichTextString text = new HSSFRichTextString(head[q]);
            cell0.setCellValue(text);
            cell0.setCellStyle(style0);
        }
        // 生成一个表格样式(表头样式)
        HSSFCellStyle style1 = workbook.createCellStyle();
        // 设置单元格背景样式颜色
        style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);// 普通颜色填充
        style1.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_ORANGE.getIndex());
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

        String[] headers = {"序号", "销售单号", "销售日期", "客户", "金额", "支付方式"};
        //headers表示excel表中第一行的表头
        HSSFRow row1 = sheet.createRow(3);
        //在excel表中添加表头
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row1.createCell(i); //Cell表格最小单位
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
            cell.setCellStyle(style1);
        }
        // 生成一个表格样式(表头样式)
        HSSFCellStyle style2 = workbook.createCellStyle();
        // 设置单元格背景样式颜色
        style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);// 普通颜色填充
        style2.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_GREEN.getIndex());
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
        //在excel表中添加订单项
        for (int i = 0; i < ecErpOrderVoList.size(); i++) {
            EcErpOrder ecErpOrder = ecErpOrderVoList.get(i);
            HSSFRow row = sheet.createRow(i + 4);
            HSSFCell cell = null;
            row.createCell(0).setCellValue(i + 1);//序号
            row.createCell(1).setCellValue(ecErpOrder.getSaleNo());//销售单号
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = formatter.format(ecErpOrder.getCreatedate());
            row.createCell(2).setCellValue(dateString);//销售日期
            row.createCell(3).setCellValue(ecErpOrder.getCustomerName());//客户名称
            row.createCell(4).setCellValue(ecErpOrder.getActualAmount().toString());//订单金额
            row.createCell(5).setCellValue(ecErpOrder.getPayType());//付款方式
//            row.createCell(6).setCellValue(ecErpOrder.getCheckMoney().toString());//核销金额
//            row.createCell(7).setCellValue("");//现金
//            row.createCell(8).setCellValue("");//张新微信
//            row.createCell(9).setCellValue("");//支付宝
//            row.createCell(10).setCellValue("");//微信
//
//            List<PayModeBean> payModeList = (List<PayModeBean>) JSONArray.toList(JSONArray.fromObject(ecErpOrder.getPayMode()), PayModeBean.class);
//            for (PayModeBean payModeBean : payModeList) {
//                if(payModeBean.getPayType().equals("现金")){
//                    row.createCell(7).setCellValue(payModeBean.getAmoun().toString());//现金
//                }
//                if(payModeBean.getPayType().equals("张新微信")){
//                    row.createCell(8).setCellValue(payModeBean.getAmoun().toString());//张新微信
//                }
//                if(payModeBean.getPayType().equals("支付宝")){
//                    row.createCell(9).setCellValue(payModeBean.getAmoun().toString());//支付宝
//                }
//                if(payModeBean.getPayType().equals("微信")){
//                    row.createCell(10).setCellValue(payModeBean.getAmoun().toString());//微信
//                }
//            }
//
//            row.createCell(11).setCellValue("");//退货金额
//            row.createCell(12).setCellValue("");//余额
//            row.createCell(13).setCellValue(ecErpOrder.getPayer());//交款人
            for (int j = 0; j < 6; j++) {
                cell = row.getCell(j);
                cell.setCellStyle(style2);
            }
        }
        //设置自适应宽度
        for (int i = 0; i < 6; i++) {
            sheet.autoSizeColumn(i);
        }
        //通过获取请求头中浏览器信息
        String agent = getHttpServletRequest().getHeader("User-Agent");

        //根据浏览器不同将文本进行编码
        String fileName = "待核销订单" + ".xls";//设置要导出的文件的名字
        fileName = URLEncoder.encode(fileName, "utf-8");
        fileName = fileName.replace("+", " ");

        response.setCharacterEncoding("utf-8");
        OutputStream output = response.getOutputStream();
        response.reset();
        response.setHeader("Content-disposition", "attachment; filename=" + fileName);
        response.setContentType("application/msexcel");
        workbook.write(output);
        output.close();
        return null;
    }

    /**
     * 已核销
     */
    @RequestMapping("/yhx")
    @ResponseBody
    public Object yhx(Integer pageNumber, Integer pageSize, Date startTime, Date endTime, String content, @RequestParam(defaultValue = "1") Integer tFlag) {

        Page page = new Page(pageNumber, pageSize);
        Wrapper wrapper = new EntityWrapper();
        wrapper.eq("if_check", ConstCheck.ecErpOrderIfcheck.YHX)
                .orderBy("createdate", false);
        if (startTime != null && endTime != null) {
            if (tFlag == 2) {
                wrapper.between("check_time", startTime, endTime);
            } else {
                wrapper.between("createdate", startTime, endTime);
            }
        }

        if (StringUtils.isNotBlank(content)) {
            wrapper.andNew().like("sale_no", content).or().like("customer_name", content);
        }
        wrapper.orderBy("check_time", false);
        Page<EcErpOrder> ecErpOrderPage = ecErpOrderService.selectPage(page, wrapper);
        List<EcErpOrderVo> ecErpOrderVos = new ArrayList<>();
        for (EcErpOrder ecErpOrder : ecErpOrderPage.getRecords()) {
            Map<String, Object> map = new HashMap<>();
            map.put("ec_erp_order_id", ecErpOrder.getId());
            List<ExOrder> exOrderList = exOrderService.selectByMap(map);
            String flowInfo = "";
            Boolean returnAllSign = false;
            for (ExOrder exOrder : exOrderList) {
                if (exOrder.getExamDepId() == 0) {
                    continue;
                }
                //得到异常类型
                String flowName = FlowUtil.getFlowName(exOrder.getFlowId());
                flowInfo += "待" + iDeptService.selectById(exOrder.getExamDepId()).getSimplename() + "审核" + "(" + flowName + ")" + "<br/>";

                if (exOrder.getExType() == 3 || exOrder.getExType() == 4) {
                    returnAllSign = true;
                }
            }
            EcErpOrderVo ecErpOrderVo = EcErpOrderVo.adapt(ecErpOrder);
            ecErpOrderVo.setReturnSingleButton(false);
            ecErpOrderVo.setRedPacketButton(false);
            ecErpOrderVo.setCheckButton(false);
            ecErpOrderVo.setExamButton(false);
            ecErpOrderVo.setRepealCheckButton(true);
            if (returnAllSign == false) {
                ecErpOrderVo.setReturnAllButton(false);
            } else {
                ecErpOrderVo.setReturnAllButton(true);
            }
            ecErpOrderVos.add(ecErpOrderVo);
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", ecErpOrderPage.getTotal());
        map.put("rows", ecErpOrderVos);
        return map;
    }

    /**
     * 待核销
     */
    @RequestMapping("/dhx")
    @ResponseBody
    public Object dhx(Integer pageNumber, Integer pageSize, Date startTime, Date endTime, String content) {
        Page page = new Page(pageNumber, pageSize);
        Wrapper wrapper = new EntityWrapper<ExOrder>()
                .eq("if_check", false)
                .eq("ex_order_sign", false)
                .eq("if_out_store", true)
                .eq("if_close", false)
                .orderBy("createdate", false);
        if (startTime != null && endTime != null) {
            wrapper.between("createdate", startTime, endTime);
        }
        if (StringUtils.isNotBlank(content)) {
            wrapper.andNew().like("sale_no", content).or().like("customer_name", content);
        }
        Page<EcErpOrder> ecErpOrderPage = ecErpOrderService.selectPage(page, wrapper);
        List<EcErpOrderVo> ecErpOrderVos = new ArrayList<>();
        for (EcErpOrder ecErpOrder : ecErpOrderPage.getRecords()) {
            EcErpOrderVo ecErpOrderVo = EcErpOrderVo.adapt(ecErpOrder);
            ecErpOrderVo.setCheckButton(true); //显示核销按钮
            ecErpOrderVo.setReturnAllButton(true);
            ecErpOrderVo.setRedPacketButton(true);
            ecErpOrderVo.setReturnSingleButton(true);
            ecErpOrderVos.add(ecErpOrderVo);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", ecErpOrderPage.getTotal());
        map.put("rows", ecErpOrderVos);
        return map;
    }

    /**
     * 核销订单
     */
    @RequestMapping(value = "/checkOrder")
    @ResponseBody
    public Object checkOrder(EcErpOrder ecErpOrder) {
        List<PayModeBean> payModeList = (List<PayModeBean>) JSONArray.toList(JSONArray.fromObject(ecErpOrder.getPayMode()), PayModeBean.class);
        BigDecimal checkMoney = new BigDecimal(0);
        for (PayModeBean payModeBean : payModeList) {
            checkMoney = checkMoney.add(payModeBean.getAmoun());
            System.out.println(payModeBean.getAmoun());
            System.out.println("===");
        }
        ecErpOrder.setCheckTime(new Date());
        ecErpOrder.setCheckMoney(checkMoney);
        ecErpOrderService.updateById(ecErpOrder);
        return SUCCESS_TIP;
    }

    /**
     * 撤销核销
     */
    @RequestMapping(value = "/repealCheck")
    @ResponseBody
    public Object repealCheck(Long ecErpOrderId) {
        EcErpOrder ecErpOrder = ecErpOrderService.selectById(ecErpOrderId);
        //重置核销信息
        ecErpOrder.setPayMode(null);
        ecErpOrder.setPayer(null);//交款人
        ecErpOrder.setPayee(null);//收款人
        ecErpOrder.setIfCheck(false);
        ecErpOrderService.updateAllColumnById(ecErpOrder);
        return SUCCESS_TIP;
    }

    /**
     * 测试erp和核销系统数据
     * （改造这个方法，使用函数式编程和流处理）
     */
    @RequestMapping(value = "/test")
    @ResponseBody
    public Object test() {
        //1、根据时间得到erp系统中数据
        Wrapper wrapper = new EntityWrapper();
        String start = "2019-01-01";
        String end = "2019-02-26";
        wrapper.between("sale_date", start, end);
        List<ErpSaleOrder> erpSaleOrderList = erpSaleOrderSeriverImpWrap.selectList(wrapper);
        BigDecimal bigDecimal = new BigDecimal(0);

        //2、把erp数据存入一个map集合中
        Map<String, ErpSaleOrder> map = new HashMap();
        for (ErpSaleOrder erpSaleOrder : erpSaleOrderList) {
            map.put(erpSaleOrder.getSn(), erpSaleOrder);
            bigDecimal = bigDecimal.add(erpSaleOrder.getTPrice());
        }
        //3、根据时间得到电商系统中数据

        wrapper = new EntityWrapper();
        wrapper.between("createdate", start, end);
        wrapper.eq("if_close", false);
        List<EcErpOrder> ecErpOrderList = ecErpOrderService.selectList(wrapper);

        //4、根据时间得到电商系统中数据
        int index = 0;
        BigDecimal bigDecimal2 = new BigDecimal(0);//核销系统金额
        BigDecimal bigDecimal3 = new BigDecimal(0);
        BigDecimal bigDecimal5 = new BigDecimal(0);//系统中不存在的(薏米小店)
        //相同的销售单号
        Set<String> saleSnSet = new HashSet();
        List<String> saleSnList = new ArrayList<>();  //重复的销售单号
        //相同的电商单号
        Map<String, String> ecSnMap = new HashMap<>();
        List<String> ecSnList = new ArrayList<>();
        //循环核销集合数据
        for (EcErpOrder ecErpOrder : ecErpOrderList) {
            //1）得到重复数据(前端问题解决后这个问题已经规避，多线程时候可能会出现问题)
            if (!saleSnSet.contains(ecErpOrder.getSaleNo())) {
                saleSnSet.add(ecErpOrder.getSaleNo());
            } else {
                System.out.println("重复销售单号：" + ecErpOrder.getSaleNo() + "   销售金额：" + ecErpOrder.getTotalAmount());
                saleSnList.add(ecErpOrder.getSaleNo());
            }
            //2）得到相同订单号，但是金额不同的订单
            if (map.get(ecErpOrder.getSaleNo()) != null) {
                ErpSaleOrder erpSaleOrder = map.get(ecErpOrder.getSaleNo());
                if (!(erpSaleOrder.getTPrice().compareTo(ecErpOrder.getActualAmount().add(ecErpOrder.getDiscountAmount())) == 0)) {
                    System.out.println(erpSaleOrder.getSn());
                    System.out.printf("erp系统:%s,核销系统金额:%s",erpSaleOrder.getTPrice(),ecErpOrder.getActualAmount().add(ecErpOrder.getDiscountAmount()));
                    System.out.println();
                    bigDecimal3 = bigDecimal3.add(erpSaleOrder.getTPrice().subtract(ecErpOrder.getActualAmount().add(ecErpOrder.getDiscountAmount())));
                }
                index++;
            //3）得到系统中不存在的订单
            } else {
                saleSnList.add(ecErpOrder.getSaleNo());
                System.out.println("系统中不存在订单号：" + ecErpOrder.getSaleNo());
            }
            bigDecimal2 = bigDecimal2.add(ecErpOrder.getActualAmount().add(ecErpOrder.getDiscountAmount()));
        }
        //多余订单
        for (String s : saleSnList) {
            Wrapper wrapper1 = new EntityWrapper();
            wrapper1.eq("sale_no", s);
            EcErpOrder ecErpOrder = ecErpOrderService.selectOne(wrapper1);
            bigDecimal5 = bigDecimal5.add(ecErpOrder.getDiscountAmount().add(ecErpOrder.getActualAmount()));
        }
        System.out.println(index);
        System.out.println("核销系统总金额：" + bigDecimal2);
        System.out.println("erp系统订单金额：" + bigDecimal);
        System.out.println("多余和重复订单相加金额：" + bigDecimal5);
        System.out.println("得到相同订单号，但是金额不同的订单的差额（erp减去核销系统）：" + bigDecimal3);
        return "核对完成";
    }

     /**
     * 关闭订单
     */
    @RequestMapping("/closeOrder")
    @ResponseBody
    public Tip closeOrder(Long ecErpOrderId,String closeReason) throws ParseException {
        EcErpOrder ecErpOrder=ecErpOrderService.selectById(ecErpOrderId);
        ecErpOrder.setIfClose(true);
        if (ecErpOrder.getCheckNode() != null) {
            ecErpOrder.setCheckNode(ecErpOrder.getCheckNode()+",订单关闭原因："+closeReason);
        }else {
            ecErpOrder.setCheckNode("订单关闭原因："+closeReason);
        }
        ecErpOrderService.updateById(ecErpOrder);
        return SUCCESS_TIP;
    }




    /**
     * 生成异常订单——订单项退货中间表
     */
    @RequestMapping("/exItem")
    @ResponseBody
    public Tip exItem(){
        //查询所有异常订单
        Wrapper wrapper = new EntityWrapper();
        wrapper.in("ex_type", Arrays.asList(2, 3, 4));
        List<ExOrder> exOrderList =exOrderService.selectList(wrapper);
        ArrayList<ExOrderItem> arrayList = new ArrayList();
        for (ExOrder exOrder : exOrderList) {
            if (exOrder.getExType() == 2) {
                List<OrderItemRo> orderItemVoList = (List<OrderItemRo>) JSONArray.toList(JSONArray.fromObject(exOrder.getDeleteOrderItems()), OrderItemRo.class);
                ExOrderItem exOrderItem = new ExOrderItem();
                for (OrderItemRo orderItemRo:orderItemVoList){
                    exOrderItem.setExOrderId(exOrder.getId());
                    exOrderItem.setReturnNumber(orderItemRo.getNum());
                    exOrderItem.setOrderItemId(orderItemRo.getId());
                    arrayList.add(exOrderItem);
                }
            }
        }
        exOrderItemService.insertBatch(arrayList);
        return SUCCESS_TIP;
    }






    public static void main(String[] args) {
        BigDecimal returnBillRate = new BigDecimal(1).divide(new BigDecimal(3), 3, BigDecimal.ROUND_HALF_DOWN);
        System.out.println(returnBillRate);
    }


}
