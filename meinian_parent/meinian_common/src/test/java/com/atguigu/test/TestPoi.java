package com.atguigu.test;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestPoi {

    public static void main(String[] args) throws IOException {
        //        WorkbookSettings workbookSettings = new WorkbookSettings();
//        workbookSettings.setEncoding("ISO-8859-1");
//        Workbook wb= Workbook.getWorkbook(is,workbookSettings);

        //创建工作簿
        //XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream("D:\\hello.xlsx"));
        XSSFWorkbook workbook = new XSSFWorkbook("D:\\hello.xlsx");


        //创建工作表
        XSSFSheet sheet = workbook.getSheetAt(0);

        //遍历工作表，获取行对象
        for (Row row : sheet) {
            //获取单元格
            for (Cell cell : row) {
                //获取单元格的值
                String value = cell.getStringCellValue();
                System.out.println(new String(value.getBytes("UTF-8"),"GBK"));
                //System.out.println(value);
            }
        }
    }

    //@Test
    public void testReadExcel() throws IOException {
//        WorkbookSettings workbookSettings = new WorkbookSettings();
//        workbookSettings.setEncoding("ISO-8859-1");
//        Workbook wb= Workbook.getWorkbook(is,workbookSettings);

        //创建工作簿
        //XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream("D:\\hello.xlsx"));
        XSSFWorkbook workbook = new XSSFWorkbook("D:\\hello.xlsx");


        //创建工作表
        XSSFSheet sheet = workbook.getSheetAt(0);

        //遍历工作表，获取行对象
        for (Row row : sheet) {
            //获取单元格
            for (Cell cell : row) {
                //获取单元格的值
                String value = cell.getStringCellValue();
                System.out.println(new String(value.getBytes("UTF-8"),"GBK"));
                //System.out.println(value);
            }
        }

    }

    //@Test
    public void testReadExcel2() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook("D:\\hello.xlsx");
        XSSFSheet sheet = workbook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        for (int i = 0; i < lastRowNum ; i++) {
            XSSFRow row = sheet.getRow(i);
            short lastCellNum = row.getLastCellNum();
            for (int j = 0; j < lastCellNum ; j++) {
                String value = row.getCell(j).getStringCellValue();
                System.out.println(new String(value.getBytes("UTF-8"),"GBK"));
            }
        }
    }

    //@Test
    public void testWriteExcel() throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFSheet sheet = workbook.createSheet("尚硅谷");

        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("编号");
        row.createCell(1).setCellValue("姓名");
        row.createCell(2).setCellValue("年龄");

        XSSFRow row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("1");
        row1.createCell(1).setCellValue("小明");
        row1.createCell(2).setCellValue("22");

        XSSFRow row2 = sheet.createRow(2);
        row2.createCell(0).setCellValue("2");
        row2.createCell(1).setCellValue("小王");
        row2.createCell(2).setCellValue("23");

        FileOutputStream fos = new FileOutputStream("D:\\atguigu.xlsx");

        workbook.write(fos);

        fos.flush();
        fos.close();
        workbook.close();

    }




















}
