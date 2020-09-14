package com.stylefeng.guns.core.util;

import com.stylefeng.guns.modular.system.model.OrderItem;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/test/excel")
public class Excel {
    private static String fileName;

    //上传Excel
    @RequestMapping("/uploadExcel")
    @ResponseBody
    public boolean uploadExcel(@RequestParam MultipartFile file, HttpServletRequest request) throws IOException {

        if (!file.isEmpty()) {
            String filePath = file.getOriginalFilename();
            //windows
            String savePath = request.getSession().getServletContext().getRealPath(filePath);

            //linux
            //String savePath = "/home/odcuser/webapps/file";

            File targetFile = new File(savePath);

            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }

            file.transferTo(targetFile);
            return true;
        }

        return false;
    }

    @RequestMapping("/readExcel")
    @ResponseBody
    public static void readExcel() throws Exception {


        InputStream is = new FileInputStream(new File(fileName));
        Workbook hssfWorkbook = null;
        if (fileName.endsWith("xlsx")) {
            hssfWorkbook = new XSSFWorkbook(is);//Excel 2007
        } else if (fileName.endsWith("xls")) {
            hssfWorkbook = new HSSFWorkbook(is);//Excel 2003

        }
        // HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        // XSSFWorkbook hssfWorkbook = new XSSFWorkbook(is);
        OrderItem orderItem = null;
        List<OrderItem> list = new ArrayList<OrderItem>();
        // 循环工作表Sheet
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            //HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // 循环行Row
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                //HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                Row hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {
                    orderItem = new OrderItem();
                    //HSSFCell name = hssfRow.getCell(0);
                    //HSSFCell pwd = hssfRow.getCell(1);
                    Cell id = hssfRow.getCell(0);
                    Cell productName = hssfRow.getCell(1);
                    Cell sn = hssfRow.getCell(2);
                    Cell unit = hssfRow.getCell(3);
                    Cell unitState = hssfRow.getCell(4);
                    Cell count = hssfRow.getCell(5);
                    Cell noValency = hssfRow.getCell(6);
                    Cell salesPrice = hssfRow.getCell(7);
                    Cell disPrice = hssfRow.getCell(8);
                    Cell money = hssfRow.getCell(9);
                    Cell model = hssfRow.getCell(10);
                    Cell brand = hssfRow.getCell(11);
                    Cell reArea = hssfRow.getCell(12);
                    Cell client = hssfRow.getCell(13);
                    Cell saleDate = hssfRow.getCell(14);
                    Cell openBillStore = hssfRow.getCell(15);
                    Cell lib = hssfRow.getCell(16);
                    Cell payType = hssfRow.getCell(17);
                    Cell paymentAmount = hssfRow.getCell(18);
                    Cell customerName = hssfRow.getCell(19);
                    Cell deliveryAddress = hssfRow.getCell(20);
                    Cell saleNo = hssfRow.getCell(21);
                    Cell ecNo = hssfRow.getCell(22);
                    Cell totalQnty = hssfRow.getCell(23);
                    Cell moneyAmount = hssfRow.getCell(24);
                    Cell actualAmount = hssfRow.getCell(25);
                    Cell discountAmount = hssfRow.getCell(26);
                    //这里是自己的逻辑

                }
            }
        }


    }


    //创建Excel
    @RequestMapping("/createExcel")
    @ResponseBody
    public String createExcel(HttpServletResponse response) throws IOException {

        //创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        //建立新的sheet对象（excel的表单）
        HSSFSheet sheet = wb.createSheet("成绩表");
        HSSFRow row = sheet.createRow(0);
        String[] headers = {"名称", "年龄", "身高", "体重"};
        //headers表示excel表中第一行的表头
        //在excel表中添加表头
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i); //Cell表格最小单位
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1 = sheet.createRow(1);
        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell = row1.createCell(0);
        //设置单元格内容
        cell.setCellValue("订单详情");
        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
        //在sheet里创建第二行
        HSSFRow row2 = sheet.createRow(2);
        //创建单元格并设置单元格内容
        row2.createCell(0).setCellValue("商品id");
        row2.createCell(1).setCellValue("商品名称");
        row2.createCell(2).setCellValue("条形码");
        row2.createCell(3).setCellValue("单位");
        row2.createCell(4).setCellValue("单位说明");
        row2.createCell(5).setCellValue("数量");
        row2.createCell(6).setCellValue("正常价");
        row2.createCell(7).setCellValue("销售价");
        row2.createCell(8).setCellValue("折后价");
        row2.createCell(9).setCellValue("金额");
        row2.createCell(10).setCellValue("规格/型号");
        row2.createCell(11).setCellValue("品牌");
        row2.createCell(12).setCellValue("库区");
        row2.createCell(13).setCellValue("客户");
        row2.createCell(14).setCellValue("销售日期");
        row2.createCell(15).setCellValue("开单门店");
        row2.createCell(16).setCellValue("库区");
        row2.createCell(17).setCellValue("付款方式");
        row2.createCell(18).setCellValue("付款金额");
        row2.createCell(19).setCellValue("收货人");
        row2.createCell(20).setCellValue("收货地址");
        row2.createCell(21).setCellValue("销售单号");
        row2.createCell(22).setCellValue("电商单号");
        row2.createCell(23).setCellValue("总数量");
        row2.createCell(24).setCellValue("总金额");
        row2.createCell(25).setCellValue("折后金额");
        row2.createCell(26).setCellValue("已优惠");

        //输出Excel文件
        String fileName = "商品" + ".xls";//设置要导出的文件的名字

        //这句话的意思，是让浏览器用utf8来解析返回的数据
//        response.setHeader("Content-type", "text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        OutputStream output = response.getOutputStream();
        response.reset();
        response.setHeader("Content-disposition", "attachment; filename="+java.net.URLDecoder.decode(fileName));
        response.setContentType("application/msexcel");

        wb.write(output);
        output.close();
        return null;
    }
}
