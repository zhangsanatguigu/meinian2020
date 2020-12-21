package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.service.MemberService;
import com.atguigu.service.ReportService;
import com.atguigu.service.SetmealService;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.channels.NonWritableChannelException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    MemberService memberService;

    @Reference
    SetmealService setmealService;

    @Reference
    ReportService reportService;

    @RequestMapping("/getMemberReport")
    public Result getMemberReport(){
        Map map = new HashMap();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-12);

        List<String> list = new ArrayList();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        for (int i = 0; i < 12; i++) {
            calendar.add(Calendar.MONTH,1);
            list.add(format.format(calendar.getTime()));
        }

        map.put("months",list);

        List<Integer> memberCounts = memberService.findMemberCountByMonth(list);

        map.put("memberCount",memberCounts);

        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
    }

    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport(){
        Map<String,Object> map = new HashMap<String,Object>();

        List<String> setmealNames = new ArrayList<String>();

        List<Map<String,Object>> setmealCount = setmealService.findSetmealCount();
        for (Map<String, Object> m : setmealCount) {
            String name = (String)m.get("name");
            setmealNames.add(name);
        }
        map.put("setmealNames",setmealNames); // ['直接访问', '邮件营销', '联盟广告', '视频广告', '搜索引擎']
        map.put("setmealCount",setmealCount); // [{value: 335, name: '直接访问'},{value: 310, name: '邮件营销'}]

        return new Result(true,MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,map) ;
    }

    /** 运营统计数据*/
    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData(){
        try {
            Map<String,Object> data = reportService.getBusinessReportData();
            return new Result(true,MessageConstant.GET_BUSINESS_REPORT_SUCCESS,data) ;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }

    /**
     * 将运营数据统计导出到Excel
     * @return
     */
    @RequestMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response){
        try {
            //获取运营数据统计
            Map<String, Object> result = reportService.getBusinessReportData();
            String reportDate = (String)result.get("reportDate");
            Integer todayNewMember = (Integer)result.get("todayNewMember");
            Integer totalMember = (Integer)result.get("totalMember");
            Integer thisWeekNewMember = (Integer)result.get("thisWeekNewMember");
            Integer thisMonthNewMember = (Integer)result.get("thisMonthNewMember");
            Integer todayOrderNumber = (Integer)result.get("todayOrderNumber");
            Integer todayVisitsNumber = (Integer)result.get("todayVisitsNumber");
            Integer thisWeekOrderNumber = (Integer)result.get("thisWeekOrderNumber");
            Integer thisWeekVisitsNumber = (Integer)result.get("thisWeekVisitsNumber");
            Integer thisMonthOrderNumber = (Integer)result.get("thisMonthOrderNumber");
            Integer thisMonthVisitsNumber = (Integer)result.get("thisMonthVisitsNumber");
            List<Map> hotSetmeal = (List<Map>)result.get("hotSetmeal");

            //获取模板文件
            String templateRealPath = request.getSession().getServletContext().getRealPath("/template")+ File.separator+"report_template.xlsx";

            //创建工作簿对象
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(templateRealPath)));

            //获取页签
            XSSFSheet sheet = workbook.getSheetAt(0);

            Row row = sheet.getRow(2);
            row.getCell(5).setCellValue(reportDate);

            row = sheet.getRow(4);
            row.getCell(5).setCellValue(todayNewMember);//新增会员数（本日）
            row.getCell(7).setCellValue(totalMember);//总会员数

            row = sheet.getRow(5);
            row.getCell(5).setCellValue(thisWeekNewMember);//本周新增会员数
            row.getCell(7).setCellValue(thisMonthNewMember);//本月新增会员数

            row = sheet.getRow(7);
            row.getCell(5).setCellValue(todayOrderNumber);//今日预约数
            row.getCell(7).setCellValue(todayVisitsNumber);//今日出游数

            row = sheet.getRow(8);
            row.getCell(5).setCellValue(thisWeekOrderNumber);//本周预约数
            row.getCell(7).setCellValue(thisWeekVisitsNumber);//本周出游数

            row = sheet.getRow(9);
            row.getCell(5).setCellValue(thisMonthOrderNumber);//本月预约数
            row.getCell(7).setCellValue(thisMonthVisitsNumber);//本月出游数

            int rowNum = 12 ;

            for (Map map : hotSetmeal) {
                String name = (String)map.get("name");
                Long setmeal_count = (Long)map.get("setmeal_count");
                BigDecimal proportion = (BigDecimal)map.get("proportion");
                row = sheet.getRow(rowNum++);
                row.getCell(4).setCellValue(name);
                row.getCell(5).setCellValue(setmeal_count);
                row.getCell(6).setCellValue(proportion.doubleValue());
            }

            //通过输出流进行文件下载
            ServletOutputStream out = response.getOutputStream();
            //下载的数据类型:excel类型
            response.setContentType("application/vnd.ms-excel");
            // 下载形式：附件形式
            response.setHeader("content-Disposition","attachment;filename=report.xlsx");
            workbook.write(out);
            out.flush();
            out.close();
            workbook.close();

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EXPORT_BUSINESS_REPORT_FAIL);
        }
    }























}
