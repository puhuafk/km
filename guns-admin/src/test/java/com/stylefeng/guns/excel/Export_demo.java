//package com.stylefeng.guns.excel;
//
//import com.stylefeng.guns.modular.system.model.Student;
//import org.apache.poi.hssf.usermodel.*;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class Export_demo {
//    public static void main(String[] args) {
//        export();
//    }
//
//    public static void export() {
//        List<Student> studens = new ArrayList<Student>();
//        for (int i = 1; i <= 20; i++) {
//            Student s = new Student(i, "a" + i, 20 + i - 20, "三年级");
//            studens.add(s);
//        }
//
//        HSSFWorkbook wb = new HSSFWorkbook();//创建一个excel文件
//        HSSFSheet sheet = wb.createSheet("学生信息");//创建一个工作薄
//        sheet.setColumnWidth((short) 3, 20 * 256);    //---》设置单元格宽度，因为一个单元格宽度定了那么下面多有的单元格高度都确定了所以这个方法是sheet的
//        sheet.setColumnWidth((short) 4, 20 * 256);    //--->第一个参数是指哪个单元格，第二个参数是单元格的宽度
//        sheet.setDefaultRowHeight((short) 300);    // ---->有得时候你想设置统一单元格的高度，就用这个方法
//        HSSFDataFormat format = wb.createDataFormat();   //--->单元格内容格式
//        HSSFRow row1 = sheet.createRow(0);   //--->创建一行
//        // 四个参数分别是：起始行，起始列，结束行，结束列 (单个单元格)
//        sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 5));//可以有合并的作用
//        HSSFCell cell1 = row1.createCell((short) 0);   //--->创建一个单元格
//        cell1.setCellValue("学生信息总览");
//
//
//        sheet.addMergedRegion(new Region(1, (short) 0, 1, (short) 0));
//        HSSFRow row2 = sheet.createRow(1);   ////创建第二列 标题
//        HSSFCell fen = row2.createCell((short) 0);   //--->创建一个单元格
//        fen.setCellValue("编号/属性 ");
//        HSSFCell no = row2.createCell((short) 1);   //--->创建一个单元格
//        no.setCellValue("姓名 ");
//        HSSFCell age = row2.createCell((short) 2);   //--->创建一个单元格
//        age.setCellValue("年龄 ");
//        HSSFCell grage = row2.createCell((short) 3);   //--->创建一个单元格
//        grage.setCellValue("年级 ");
//
//        for (int i = 0; i < studens.size(); i++) {
//            sheet.addMergedRegion(new Region(1 + i + 1, (short) 0, 1 + i + 1, (short) 0));
//            HSSFRow rows = sheet.createRow(1 + i + 1);   ////创建第二列 标题
//            HSSFCell fens = rows.createCell((short) 0);   //--->创建一个单元格
//            fens.setCellValue(studens.get(i).getNo());
//            HSSFCell nos = rows.createCell((short) 1);   //--->创建一个单元格
//            nos.setCellValue(studens.get(i).getName());
//            HSSFCell ages = rows.createCell((short) 2);   //--->创建一个单元格
//            ages.setCellValue(studens.get(i).getAge());
//            HSSFCell grages = rows.createCell((short) 3);   //--->创建一个单元格
//            grages.setCellValue(studens.get(i).getGrage());
//        }
//        FileOutputStream fileOut = null;
//        try {
//            fileOut = new FileOutputStream("d:\\studens.xls");
//            wb.write(fileOut);
//            //fileOut.close();
//            System.out.print("OK");
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (fileOut != null) {
//                try {
//                    fileOut.close();
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//}