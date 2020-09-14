package com.stylefeng.guns.modular.cancelOrder;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.modular.cancelOrder.util.CategoryUtil;
import com.stylefeng.guns.modular.common.FlowUtil;
import com.stylefeng.guns.modular.ecErpOrder.service.IEcErpOrderService;
import com.stylefeng.guns.modular.exOrder.service.IExOrderService;
import com.stylefeng.guns.modular.exOrderExam.controller.ExOrderExamController;
import com.stylefeng.guns.modular.exOrderExam.service.IExOrderExamService;
import com.stylefeng.guns.modular.orderItem.service.IOrderItemService;
import com.stylefeng.guns.modular.ro.OrderItemRo;
import com.stylefeng.guns.modular.ro.PayModeBean;
import com.stylefeng.guns.modular.system.dao.EcErpOrderMapper;
import com.stylefeng.guns.modular.system.model.EcErpOrder;
import com.stylefeng.guns.modular.system.model.ExOrder;
import com.stylefeng.guns.modular.system.model.OrderItem;
import com.stylefeng.guns.modular.system.service.IDeptService;
import com.stylefeng.guns.modular.vo.EcErpOrderVo;
import com.stylefeng.guns.modular.vo.ExOrderVo;
import com.stylefeng.guns.modular.vo.OrderNumberVo;
import net.sf.json.JSONArray;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
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
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 部门查询订单管理控制器
 *
 * @author fengshuonan
 * @Date 2018-10-22 11:58:02
 */
@Controller
@RequestMapping("/cancelOrder")
public class OrderController extends BaseController {

    private String PREFIX = "/cancel/order/";

    @Autowired
    private IEcErpOrderService ecErpOrderService;

    @Autowired
    private IExOrderService exOrderService;

    @Qualifier("deptServiceImpl")
    @Autowired
    private IDeptService iDeptService;

    @Autowired
    private EcErpOrderMapper ecErpOrderMapper;

    @Autowired
    private IExOrderExamService iExOrderExamService;

    @Autowired
    private IOrderItemService iOrderItemService;

    @Autowired
    private CaiwuController caiwuController;

    @Autowired
    private ExOrderExamController exOrderExamController;


    /**
     * @param ecErpOrderId
     * @return
     */
    @RequestMapping("/selectExOrderById")
    @ResponseBody
    public List<ExOrder> selectExOrderById(Long ecErpOrderId) {
        Wrapper wrapper = new EntityWrapper();
        wrapper.eq("ec_erp_order_id", ecErpOrderId);
        return exOrderService.selectList(wrapper);
    }

    /**
     * 主页各类型订单数量
     */
    @RequestMapping("/orderNumber")
    @ResponseBody
    public OrderNumberVo orderNumber() {
//        if (1 == 1) {
//            throw new NullPointerException();
//        }
//        Integer depId = ShiroKit.getUser().getDeptId();
        int depId = ((ShiroUser) this.getSession().getAttribute("shiroUser")).getDeptId();
        OrderNumberVo orderNumberVo = new OrderNumberVo();
        //全部订单
        orderNumberVo.setAllNumber(ecErpOrderService.selectCount(null));
        //已审核
        Wrapper yshWrapper = new EntityWrapper();
        yshWrapper.eq("sys_dept_id", depId).eq("if_launch", false);
        orderNumberVo.setExamNumber(iExOrderExamService.selectCount(yshWrapper));
        //未审核
        Wrapper dshWrapper = new EntityWrapper<ExOrder>().eq("exam_dep_id", depId);
        orderNumberVo.setNotExamNumber(exOrderService.selectCount(dshWrapper));
        return orderNumberVo;
    }

    @RequestMapping("/list")
    @ResponseBody
    public Object list(Integer pageNumber, Integer pageSize) {
        Page<EcErpOrder> page = new Page(pageNumber, pageSize);
        Page<EcErpOrder> ecErpOrderPage = ecErpOrderService.selectPage(page, null);
        List<EcErpOrderVo> ecErpOrderVos = new ArrayList<>();
        for (EcErpOrder ecErpOrder : ecErpOrderPage.getRecords()) {
            //关联异常状态
            Map<String, Object> map = new HashMap<>();
            map.put("ec_erp_order_id", ecErpOrder.getId());
            List<ExOrder> exOrderList = exOrderService.selectByMap(map);
            String flowInfo = "";
            for (ExOrder exOrder : exOrderList) {
                if (exOrder.getExamDepId() == 0) {
                    continue;
                }
                //得到异常类型
                String flowName = FlowUtil.getFlowName(exOrder.getFlowId());
                flowInfo += "待" + iDeptService.selectById(exOrder.getExamDepId()).getSimplename() + "审核" + "(" + flowName + ")" + "<br/>";
            }
            EcErpOrderVo ecErpOrderVo = EcErpOrderVo.adapt(ecErpOrder);
            ecErpOrderVo.setOrderStateInfo(flowInfo);
            //已出库显示核销按钮和其他三个异常操作
            if (ecErpOrder.getIfOutStore() != null && ecErpOrder.getIfOutStore()) {
                if (ecErpOrderVo.getOrderStateInfo() == "") {
                    ecErpOrderVo.setOrderStateInfo("已出库");
                }
            } else {
                if (ecErpOrderVo.getOrderStateInfo() == "") {
                    ecErpOrderVo.setOrderStateInfo("待仓配出库");
                }
            }

            //订单已核销，去掉所有按钮操作
            if (ecErpOrderVo.getIfCheck() != null && ecErpOrderVo.getIfCheck() == true) {
                ecErpOrderVo.setReturnSingleButton(false);
                ecErpOrderVo.setReturnAllButton(false);
                ecErpOrderVo.setRedPacketButton(false);
                ecErpOrderVo.setCheckButton(false);
                ecErpOrderVo.setExamButton(false);
                ecErpOrderVo.setOrderStateInfo("此订单已核销");
            }


            if(ecErpOrderVo.getIfClose()==true){
                ecErpOrderVo.setOrderStateInfo("此订单已关闭");
            }

            ecErpOrderVos.add(ecErpOrderVo);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", ecErpOrderPage.getTotal());
        map.put("rows", ecErpOrderVos);
        return map;
    }

    /**
     * 异常订单已经发起,未结束(这个方法只有财务能用)
     */
    @RequestMapping("/init")
    @ResponseBody
    public Object init(Integer pageNumber, Integer pageSize) {
        Page page = new Page(pageNumber, pageSize);
        Integer depId = ((ShiroUser) this.getSession().getAttribute("shiroUser")).getDeptId();
        //查询处于异常审核中的订单信息
        Wrapper initWrapper = new EntityWrapper<ExOrder>().eq("ex_state", true);
        Page<ExOrder> exOrderPage = exOrderService.selectPage(page, initWrapper);
        List<EcErpOrderVo> ecErpOrderVos = new ArrayList<>();
        for (ExOrder exOrder : exOrderPage.getRecords()) {
            System.out.println();
            EcErpOrder ecErpOrder = ecErpOrderService.selectById(exOrder.getEcErpOrderId());
            EcErpOrderVo ecErpOrderVo = EcErpOrderVo.adapt(ecErpOrder);
            ecErpOrderVo.setExType(exOrder.getExType());
            //得到异常类型
            String flowName = FlowUtil.getFlowName(exOrder.getFlowId());
            ecErpOrderVo.setOrderStateInfo("待" + iDeptService.selectById(exOrder.getExamDepId()).getSimplename() + "审核" + "(" + flowName + ")");
            ecErpOrderVo.setExamFlowButton(true);//异常订单审核查看按钮
            ecErpOrderVo.setExOrderId(exOrder.getId());
            ecErpOrderVo.setExType(exOrder.getExType());
            ecErpOrderVos.add(ecErpOrderVo);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", exOrderPage.getTotal());
        map.put("rows", ecErpOrderVos);
        return map;
    }

    /**
     * 异常订单待审核
     */
    @RequestMapping("/dsh")
    @ResponseBody
    public Object dsh(Integer pageNumber, Integer pageSize) {
        Page page = new Page(pageNumber, pageSize);
        Integer depId = ((ShiroUser) this.getSession().getAttribute("shiroUser")).getDeptId();
        Wrapper depIdWrapper = new EntityWrapper<ExOrder>().eq("exam_dep_id", depId);
        depIdWrapper.orderBy("createtime", false);
        Page<ExOrder> exOrderPage = exOrderService.selectPage(page, depIdWrapper);
        List<EcErpOrderVo> ecErpOrderVos = new ArrayList<>();
        for (ExOrder exOrder : exOrderPage.getRecords()) {
            EcErpOrder ecErpOrder = ecErpOrderService.selectById(exOrder.getEcErpOrderId());
            EcErpOrderVo ecErpOrderVo = EcErpOrderVo.adapt(ecErpOrder);
            ecErpOrderVo.setExType(exOrder.getExType());
            //得到异常类型
            String flowName = FlowUtil.getFlowName(exOrder.getFlowId());
            ecErpOrderVo.setOrderStateInfo("待" + iDeptService.selectById(exOrder.getExamDepId()).getSimplename() + "审核" + "(" + flowName + ")");
            ecErpOrderVo.setExamButton(true);
            ecErpOrderVo.setExamFlowButton(true);//异常订单审核查看按钮
            if (exOrder.getIfReject() != null && exOrder.getIfReject().equals(true)) {
                ecErpOrderVo.setCancelCheckButton(true);//撤销按钮
            }
            ecErpOrderVo.setExOrderId(exOrder.getId());
            ecErpOrderVo.setExType(exOrder.getExType());
            ecErpOrderVos.add(ecErpOrderVo);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", exOrderPage.getTotal());
        map.put("rows", ecErpOrderVos);
        return map;
    }

    /**
     * 异常订单已审核
     */
    @RequestMapping("/ysh")
    @ResponseBody
    public Object ysh(Integer pageNumber,
                      Integer pageSize,
                      String content,
                      String startTime,
                      String endTime) {
        //根据当前登录人部门id查询异常订单数据
        Integer depId = ((ShiroUser) this.getSession().getAttribute("shiroUser")).getDeptId();

        Integer index = pageSize * (pageNumber - 1) ;

        Integer total = ecErpOrderMapper.selectByDepIdysh(depId, content, startTime, endTime, null, null).size();

        List<ExOrder> exOrderList = ecErpOrderMapper.selectByDepIdysh(depId, content, startTime, endTime, index, pageSize);

        List<EcErpOrder> ecErpOrderList = exOrderList.stream().map(exOrder -> {
            EcErpOrder ecErpOrder = exOrder.getEcErpOrder();
            ecErpOrder.setExOrder(exOrder);
            ecErpOrder.setExOrderId(exOrder.getId());
            return ecErpOrder;
        }).collect(Collectors.toList());

        List<EcErpOrderVo> ecErpOrderVos = new ArrayList<>();

        for (EcErpOrder ecErpOrder : ecErpOrderList) {
            //创建vo数据存入集合
            EcErpOrderVo ecErpOrderVo = EcErpOrderVo.adapt(ecErpOrder);
            ecErpOrderVo.setExType(ecErpOrder.getExOrder().getExType());
            //得到异常类型
            String flowName = FlowUtil.getFlowName(ecErpOrder.getExOrder().getFlowId());
            if (ecErpOrder.getExOrder().getExamDepId() != 0) {
                String flowInfo = "待" + iDeptService.selectById(ecErpOrder.getExOrder().getExamDepId()).getSimplename() + "审核" + "(" + flowName + ")" + "<br/>";
                ecErpOrderVo.setOrderStateInfo(flowInfo);
            } else {
                ecErpOrderVo.setOrderStateInfo("审核流程已结束" + "(" + flowName + ")");
            }
            ecErpOrderVo.setExOrderId(ecErpOrder.getExOrder().getId());
            ecErpOrderVo.setExamFlowButton(true);//异常订单审核查看按钮
            ecErpOrderVos.add(ecErpOrderVo);
        }
        //通过部门id得到审核记录
//        Page page = new Page(pageNumber, 1);
//        Wrapper examWrapper = new EntityWrapper();
//        examWrapper.eq("sys_dept_id", depId).eq("if_launch", false);
//        Page<ExOrderExam> exOrderExamPage = iExOrderExamService.selectPage(page, examWrapper);
        //通过审核记录得到异常订单，并且管理关联订单信息
//        for (ExOrderExam exOrderExam : exOrderExamPage.getRecords()) {
//            ExOrder exOrder = exOrderService.selectById(exOrderExam.getExOrderId());
//            EcErpOrder ecErpOrder = ecErpOrderService.selectById(exOrder.getEcErpOrderId());
//            //创建vo数据存入集合
//            EcErpOrderVo ecErpOrderVo = EcErpOrderVo.adapt(ecErpOrder);
//            ecErpOrderVo.setExType(exOrder.getExType());
//            if (exOrder.getExamDepId() == depId) {
//                ecErpOrderVo.setExamButton(true);
//            }
//            //得到异常类型
//            String flowName = FlowUtil.getFlowName(exOrder.getFlowId());
//            if (exOrder.getExamDepId() != 0) {
//                String flowInfo = "待" + iDeptService.selectById(exOrder.getExamDepId()).getSimplename() + "审核" + "(" + flowName + ")" + "<br/>";
//                ecErpOrderVo.setOrderStateInfo(flowInfo);
//            } else {
//                ecErpOrderVo.setOrderStateInfo("审核流程已结束" + "(" + flowName + ")");
//            }
//            ecErpOrderVo.setExOrderId(exOrderExam.getExOrderId());
//            ecErpOrderVo.setExamFlowButton(true);//异常订单审核查看按钮
//            ecErpOrderVos.add(ecErpOrderVo);
//        }
        //返回数据的时候需要返回这个订单的基本信息、审核信息（异常类型、）、审核流程信息
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", total);
        map.put("rows", ecErpOrderVos);
        return map;
    }


    /**
     * 已审核(导出)
     */
    @RequestMapping("/yshExport")
    @ResponseBody
    public String yshExport(Integer pageNumber,
                            Integer pageSize,
                            String content,
                            String startTime,
                            String endTime,
                            HttpServletResponse response
    ) throws IOException {
//        LocalDate date1 = LocalDate.of(2018, 12, 12);
//        LocalDate date2 = LocalDate.of(2018, 12, 28);
//        ZoneId zoneId = ZoneId.systemDefault();
//        ZonedDateTime zdt1 = date1.atStartOfDay(zoneId);
//        startTime = Date.from(zdt1.toInstant());
//        ZonedDateTime zdt2 = date2.atStartOfDay(zoneId);
//        endTime=Date.from(zdt2.toInstant());
//        pageNumber = 1;
//        pageSize = Integer.MAX_VALUE;
//        startTime = "2018-01-01";
//        endTime = "2019-03-08";
        pageNumber = 1;
        pageSize = Integer.MAX_VALUE;
        Map map = (Map) ysh(pageNumber, pageSize, content, startTime, endTime);
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

        sheet.addMergedRegion(new CellRangeAddress(0, 2, 0, 22));
        //表格名称
        String[] head = {"已审核订单"};
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

        String[] headers = {"序号", "销售单号", "销售日期", "客户", "红包", "金额", "支付方式", "备注"
                , "核销金额", "现金", "张新微信", "支付宝", "公司微信", "银行转账/合并支付/余额/小程序", "退货退款方式", "退货退款金额", "退红包方式", "退红包金额", "异常金额", "余额", "交款人", "核销时间","审核详情"};
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
            EcErpOrderVo ecErpOrder = ecErpOrderVoList.get(i);
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
            row.createCell(8).setCellValue(ecErpOrder.getCheckMoney() != null ? ecErpOrder.getCheckMoney().toString() : "");//核销金额
            row.createCell(9).setCellValue("");//现金
            row.createCell(10).setCellValue("");//张新微信
            row.createCell(11).setCellValue("");//支付宝
            row.createCell(12).setCellValue("");//公司微信
            row.createCell(13).setCellValue("");//公司微信

            List<PayModeBean> payModeList = (List<PayModeBean>) JSONArray.toList(JSONArray.fromObject(ecErpOrder.getPayMode()), PayModeBean.class);

            if (payModeList.get(0) != null) {
                for (PayModeBean payModeBean : payModeList) {
                    if (payModeBean.getPayType().equals("现金")) {
                        row.getCell(9).setCellValue(payModeBean.getAmoun().toString());//现金
                        break;
                    }
                    if (payModeBean.getPayType().equals("张新微信")) {
                        row.getCell(10).setCellValue(payModeBean.getAmoun().toString());//张新微信
                        break;
                    }
                    if (payModeBean.getPayType().equals("支付宝")) {
                        row.getCell(11).setCellValue(payModeBean.getAmoun().toString());//支付宝
                        break;
                    }
                    if (payModeBean.getPayType().equals("微信")) {
                        row.getCell(12).setCellValue(payModeBean.getAmoun().toString());//微信
                        break;
                    }
                    row.getCell(13).setCellValue(payModeBean.getAmoun().toString());//其他支付方式金额
                }
            }


            row.createCell(14).setCellValue(ecErpOrder.getReturnSingleType());//退货退款方式
            row.createCell(15).setCellValue(ecErpOrder.getRefundMoney() != null ? ecErpOrder.getRefundMoney().toString() : "");//退货退款金额
            row.createCell(16).setCellValue(ecErpOrder.getReturnRedType());//退红包方式
            row.createCell(17).setCellValue(ecErpOrder.getReturnRedMoney() != null ? ecErpOrder.getReturnRedMoney().toString() : "");//退红包金额
            row.createCell(18).setCellValue(ecErpOrder.getExMoney() != null ? ecErpOrder.getExMoney().toString() : "");//异常金额
            row.createCell(19).setCellValue("");//余额
            row.createCell(20).setCellValue(ecErpOrder.getPayer());//交款人
            if(ecErpOrder.getCheckTime()!=null){
                System.out.println(ecErpOrder.getCheckTime());
                row.createCell(21).setCellValue(ecErpOrder.getCheckTime()!=null?formatter.format(ecErpOrder.getCheckTime()):"");//核销时间
            }else {
                row.createCell(21).setCellValue("");//核销时间
            }

            ExOrderVo exOrderVo=exOrderExamController.selectExanList(ecErpOrder.getExOrderId());


//            ecErpOrder.getExOrder();
            //审核详情


            row.createCell(22).setCellValue(exOrderVo.getExOrderInfo().replaceAll(" ","").replaceAll("<br>","   "));//审核详情
            for (int j = 0; j < 23; j++) {
                cell = row.getCell(j);
                cell.setCellStyle(style2);
            }
        }
        //设置自适应宽度
        for (int i = 0; i < 23; i++) {
            sheet.autoSizeColumn(i);
        }
        //通过获取请求头中浏览器信息
        String agent = getHttpServletRequest().getHeader("User-Agent");

        //根据浏览器不同将文本进行编码
        String fileName = "已审核订单" + ".xls";//设置要导出的文件的名字
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
     * 订单管理详情
     */
    @RequestMapping(value = "/statement")
    public Object saleSummary() {
        return "/cancel/statement/" + "salesStatistics.html";
    }

    /**
     * 订单管理详情
     */
    @RequestMapping(value = "/detail/{ecErpOrderId}")
    @ResponseBody
    public EcErpOrderVo orderItemDetail(@PathVariable("ecErpOrderId") Long ecErpOrderId) {
        EcErpOrder ecErpOrder = ecErpOrderService.selectById(ecErpOrderId);
        EcErpOrderVo ecErpOrderVo = EcErpOrderVo.adapt(ecErpOrder);
        Map<String, Object> map = new HashMap<>();
        map.put("ec_erp_order_id", ecErpOrderId);
        List<OrderItem> orderItemList = iOrderItemService.selectByMap(map);
        //订单详情排序
        for (OrderItem orderItem : orderItemList) {
            orderItem.setProductCategoryId(CategoryUtil.getBigcategory(orderItem.getProductCategoryId()));
        }
        //关联审核信息列表
        Wrapper wrapper = new EntityWrapper();
        wrapper.eq("ec_erp_order_id", ecErpOrderId);
        List<ExOrder> exOrderList = exOrderService.selectList(wrapper);

        List<ExOrderVo> exOrderVoList = exOrderList.stream().map(x -> ExOrderVo.adapt(x)).collect(Collectors.toList());

        ecErpOrderVo.setExOrderVoList(exOrderVoList);
        //增加异常审核记录
        for (ExOrderVo exOrderVo : exOrderVoList) {
            ExOrderVo exOrderVo1 = exOrderExamController.selectExanList(exOrderVo.getId());
            //删除商品信息转换
            List<OrderItemRo> orderItemRoList = JSONArray.toList(JSONArray.fromObject(exOrderVo1.getDeleteOrderItems()), OrderItemRo.class);

            if (orderItemRoList.get(0) != null) {
                orderItemRoList.stream().forEach(
                        i -> i.setOrderItem(iOrderItemService.selectById(i.getId()))
                );
            }
            exOrderVo.setDeleteOrderItems(JSONArray.fromObject(orderItemRoList).toString());
            exOrderVo.setExOrderExamList(exOrderVo1.getExOrderExamList());
            exOrderVo.setExOrderInfo(exOrderVo1.getExOrderInfo());
        }
        //修改订单
        Collections.sort(orderItemList, Comparator.comparingInt(OrderItem::getProductCategoryId));
        ecErpOrderVo.setOrderItemList(orderItemList);


        return ecErpOrderVo;
    }


    /**
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     * 订单管理详情(删除商品详情)
     * 需要对待删除数量进行特别处理
     */
    @RequestMapping(value = "/detail/deleteList/{ecErpOrderId}")
    @ResponseBody
    public EcErpOrderVo deleteOrderItemDetail(@PathVariable("ecErpOrderId") Integer ecErpOrderId) {
        EcErpOrder ecErpOrder = ecErpOrderService.selectById(ecErpOrderId);
        EcErpOrderVo ecErpOrderVo = EcErpOrderVo.adapt(ecErpOrder);
        Map<String, Object> map = new HashMap<>();
        map.put("ec_erp_order_id", ecErpOrderId);
        List<OrderItem> orderItemList = iOrderItemService.selectByMap(map);
        map = new HashMap<>();
        map.put("ex_state", 1);
        map.put("ex_type", 2);
        List<ExOrder> exOrderList = exOrderService.selectByMap(map);
        ecErpOrderVo.setExOrderList(exOrderList);
        //订单详情排序
        for (int i = orderItemList.size() - 1; i >= 0; i--) {
            OrderItem orderItem = orderItemList.get(i);
            if (orderItem.getReturnNum() == null) {
                orderItem.setReturnNum(0);
            }

            if (orderItem.getForReturnNum() == null) {
                orderItem.setForReturnNum(0);
            }

            orderItem.setUnitE(orderItem.getUnitE() - orderItem.getForReturnNum() - orderItem.getReturnNum());

            if (orderItem.getUnitE() == 0) {
                orderItemList.remove(i);
            }
            orderItem.setProductCategoryId(CategoryUtil.getBigcategory(orderItem.getProductCategoryId()));
        }

        Collections.sort(orderItemList, Comparator.comparingInt(OrderItem::getProductCategoryId));
        ecErpOrderVo.setOrderItemList(orderItemList);
        return ecErpOrderVo;
    }

    /**
     * 订单管理首页
     */
    @RequestMapping(value = "/detail")
    public Object detail(Integer ecErpOrderId) {
        return PREFIX + "orderDetail.html";
    }


}
