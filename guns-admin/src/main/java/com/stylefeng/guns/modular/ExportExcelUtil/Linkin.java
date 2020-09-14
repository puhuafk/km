//package com.stylefeng.guns.modular.ExportExcelUtil;
//
//import org.apache.poi.hssf.usermodel.*;
//import org.apache.poi.hssf.util.HSSFColor;
//import org.apache.poi.ss.usermodel.HorizontalAlignment;
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//
//public class Linkin
//{
//    public static void main(String[] args)
//    {
//        HSSFWorkbook workbook = new HSSFWorkbook();
//        HSSFSheet sheet = workbook.createSheet();
//
//        HSSFRow row = sheet.createRow(1);
//        HSSFCell cell1 = row.createCell((short) 1);
//        HSSFCell cell2 = row.createCell((short) 2);
//
//        HSSFCellStyle style1 = workbook.createCellStyle();
//        //设置居中
//        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
//
//        style1.setAlignment(HorizontalAlignment.CENTER);
//
//        style1.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);
//        style1.setBorderLeft(HSSFCellStyle.BORDER_DOUBLE);
//        style1.setTopBorderColor(HSSFColor.GOLD.index);
//        style1.setLeftBorderColor(HSSFColor.PLUM.index);
//        cell1.setCellStyle(style1);
//
//        HSSFCellStyle style2 = workbook.createCellStyle();
//        style2.setBorderBottom(HSSFCellStyle.BORDER_DOUBLE);
//        style2.setBorderRight(HSSFCellStyle.BORDER_DOUBLE);
//        style2.setBottomBorderColor(HSSFColor.ORANGE.index);
//        style2.setRightBorderColor(HSSFColor.SKY_BLUE.index);
//        cell2.setCellStyle(style2);
//
//        cell1.setCellValue("U & L");
//        cell2.setCellValue("B & R");
//
//        FileOutputStream out = null;
//        try
//        {
//            out = new FileOutputStream("sample.xls");
//            workbook.write(out);
//        }
//        catch (IOException e)
//        {
//            System.out.println(e.toString());
//        }
//        finally
//        {
//            try
//            {
//                out.close();
//            }
//            catch (IOException e)
//            {
//                System.out.println(e.toString());
//            }
//        }
//    }
//}