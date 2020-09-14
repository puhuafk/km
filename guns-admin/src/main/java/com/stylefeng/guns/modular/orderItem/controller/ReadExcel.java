//package com.stylefeng.guns.modular.orderItem.controller;
//
//import com.stylefeng.guns.modular.system.model.orderItem;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.List;
//
//public class ReadExcel {
//    //总行数
//    private int totalRows = 0;
//    //总条数
//    private int totalCells = 0;
//    //错误信息接收器
//    private String errorMsg;
//
//    //构造方法
//    public ReadExcel() {
//    }
//
//    //获取总行数
//    public int getTotalRows() {
//        return totalRows;
//    }
//
//    //获取总列数
//    public int getTotalCells() {
//        return totalCells;
//    }
//
//    //获取错误信息
//    public String getErrorMsg() {
//        return errorMsg;
//    }
//
//    /**
//     * 读Excel文件，获取信息集合
//     *
//     * @return
//     */
//    public List<orderItem> getExcelInfo(MultipartFile mFile) throws IOException {
//        String fileName = mFile.getOriginalFilename();
//        if (!validateExcel(fileName)) {
//            return null;
//        }
//        boolean isExcel2003 = true;
//        if (isExcel2007(fileName)) {
//            isExcel2003 = false;
//        }
//        List<orderItem> orderItem = createExcel(mFile.getInputStream(), isExcel2003);
//        return orderItem;
//
//    }
//
//    /**
//     * 根据excel里面的内容读取客户信息
//     *
//     * @param inputStream
//     * @param isExcel2003
//     * @return
//     */
//    private List<orderItem> createExcel(InputStream inputStream, boolean isExcel2003) {
//
//        Workbook wb = null;
//        if (isExcel2003) {
//            wb = new HSSFWorkbook();
//        } else {
//            wb = new XSSFWorkbook();
//        }
//        List<orderItem> orderItem = readExcelValue(wb);
//
//        return orderItem;
//    }
//
//    /**
//     * 读取Excel里面客户的信息
//     *
//     * @param wb
//     * @return
//     */
//    private List<orderItem> readExcelValue(Workbook wb) {
//        //得到第一个shell
//        Sheet sheet = wb.getSheetAt(0);
//        this.totalRows = sheet.getPhysicalNumberOfRows();
//        if (totalRows > 1 && sheet.getRow(0) != null) {
//            this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
//        }
//        List<orderItem> orderItemList = new ArrayList<orderItem>();
//        for (int r = 1; r < totalRows; r++) {
//            Row row = sheet.getRow(r);
//            if (row == null) {
//                continue;
//            }
//            orderItem orderItem = new orderItem();
//            for (int c = 0; c < this.totalCells; c++
//                    ) {
//                Cell cell = row.getCell(c);
//                if (null != cell) {
//                    if (c == 0) {
//                        orderItem.getId();
//                    } else if (c == 1) {
//                        orderItem.getProductName();
//                    } else if (c == 2) {
//                        orderItem.getSn();
//                    } else if (c == 3) {
//                        orderItem.getUnit();
//                    } else if (c == 4) {
//                        orderItem.getUnitState();
//                    } else if (c == 5) {
//                        orderItem.getCount();
//                    } else if (c == 6) {
//                        orderItem.getNoValency();
//                    } else if (c == 7) {
//                        orderItem.getSalesPrice();
//                    } else if (c == 8) {
//                        orderItem.getDisPrice();
//                    } else if (c == 9) {
//                        orderItem.getMoney();
//                    } else if (c == 10) {
//                        orderItem.getModel();
//                    } else if (c == 11) {
//                        orderItem.getBrand();
//                    } else if (c == 12) {
//                        orderItem.getReArea();
//                    } else if (c == 13) {
//                        orderItem.getClient();
//                    } else if (c == 14) {
//                        orderItem.getSaleDate();
//                    } else if (c == 15) {
//                        orderItem.getOpenBillStore();
//                    } else if (c == 16) {
//                        orderItem.getLib();
//                    } else if (c == 17) {
//                        orderItem.getPayType();
//                    } else if (c == 18) {
//                        orderItem.getPaymentAmount();
//                    } else if (c == 19) {
//                        orderItem.getCustomerName();
//                    } else if (c == 20) {
//                        orderItem.getDeliveryAddress();
//                    } else if (c == 21) {
//                        orderItem.getSaleNo();
//                    } else if (c == 22) {
//                        orderItem.getEcNo();
//                    } else if (c == 23) {
//                        orderItem.getTotalQnty();
//                    } else if (c == 24) {
//                        orderItem.getMoneyAmount();
//                    } else if (c == 25) {
//                        orderItem.getActualAmount();
//                    } else if (c == 26) {
//                        orderItem.getDiscountAmount();
//                    }
//
//                }
//
//            }
//            orderItemList.add(orderItem);
//        }
//        return orderItemList;
//    }
//
//
//    /**
//     * 验证EXCEL文件
//     *
//     * @param filePath
//     * @return
//     */
//    private boolean validateExcel(String filePath) {
//        if (filePath == null || !(isExcel2003(filePath)) || isExcel2007(filePath)) {
//            errorMsg = "文件名不是excel格式";
//            return false;
//        }
//        return true;
//    }
//
//    //@描述：是否是2003的excel，返回true是2003
//    private boolean isExcel2007(String filePath) {
//        return filePath.matches("^.+\\.(?i)(xls)$");
//    }
//
//    //@描述：是否是2007的excel，返回true是2007
//    private boolean isExcel2003(String filePath) {
//        return filePath.matches("^.+\\.(?i)(xls)$");
//    }
//
//}
