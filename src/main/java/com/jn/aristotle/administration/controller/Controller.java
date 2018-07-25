package com.jn.aristotle.administration.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.jn.aristotle.administration.entity.NewspaperMeal;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/jn")
public class Controller {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 报餐
     * @param name
     * @return
     */
    @RequestMapping("/add")
    public String add(String name){
        Date dt = new Date();
        SimpleDateFormat dateformat1 = new SimpleDateFormat("HHmm");
        Integer ti = Integer.valueOf(dateformat1.format(dt));
        if(ti>1400 && ti<1500){
            return "还没有到报餐时间 别猴急";
        }
        NewspaperMeal entity = (NewspaperMeal) redisTemplate.opsForValue().get("JN-Aristotle:user:" + name);
        if(null==entity){
            return "没有当前用户 欢迎带上一箱港荣蒸蛋糕 来7楼进行友好交流 达成账号事项";
        }
        if(0==entity.getIsEnable()){
            return "没有当前用户 欢迎带上一箱港荣蒸蛋糕 来7楼进行友好交流 达成账号事项";
        }

        NewspaperMeal ob = (NewspaperMeal) redisTemplate.opsForValue().get("JN-Aristotle:report"+name);
        if(null!=ob){
            return "报过了还报？ 社会主义羊毛不是这么薅的";
        }

        SimpleDateFormat dateformat2 = new SimpleDateFormat("yyyy年MM月dd日-HH时mm分ss秒");
        entity.setTime(dateformat2.format(dt));
        redisTemplate.opsForValue().set("JN-Aristotle:report:"+name,entity);
        redisTemplate.expire("JN-Aristotle:report:"+name,21600,TimeUnit.SECONDS);
        return entity.getName()+"报餐成功";
    }

    /**
     * 注册
     * @param entity
     * @return
     */
    @RequestMapping("/registered")
    public String registered(NewspaperMeal entity){
        NewspaperMeal o = (NewspaperMeal) redisTemplate.opsForValue().get("JN-Aristotle:user:" + entity.getName());
        if(null!=o){
            return entity.getName()+"用户已注册过";
        }
        entity.setIsEnable(0);
        redisTemplate.opsForValue().set("JN-Aristotle:user:"+entity.getName(),entity);

        return entity.getName()+"注册成功等待审核  注：非本部人员请带上一箱港荣蒸蛋糕来7楼进行友好交流 达成审核事项";

    }

    /**
     * 启用账号
     * @param name
     * @return
     */
    @RequestMapping("/enable")
    public String enable(String name){
        NewspaperMeal entity = (NewspaperMeal) redisTemplate.opsForValue().get("JN-Aristotle:user:" + name);
        if (null == entity){
            return "没有当前注册账号";
        }
        entity.setIsEnable(88);
        redisTemplate.delete("JN-Aristotle:user:" + name);
        redisTemplate.opsForValue().set("JN-Aristotle:user:" + name,entity);
        return "启用账号成功";

    }

    /**
     * 当前用户列表
     * @return
     */
    @RequestMapping("/userList")
    public void userList(HttpServletResponse response) throws IOException {
        /**
         * 获取用户列表
         */
        List<NewspaperMeal> list = new ArrayList<>();
        Set keys = redisTemplate.keys("JN-Aristotle:user:*");
        keys.forEach(x->{
            list.add((NewspaperMeal) redisTemplate.opsForValue().get(x));
        });
        response.setCharacterEncoding("gbk");
        PrintWriter printWriter = response.getWriter();
        printWriter.print("<html><head>");
        printWriter.print("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">");
        printWriter.print("<title>Aristotle OS</title>");
        printWriter.print("</head>");
        printWriter.print("<body>");
        printWriter.print("<table border=1 cellpadding=10 cellspacing=0>");
        printWriter.print("<tr  ><td colspan='4' text-align=center >Aristotle-OS 用户列表</td></tr>");
        printWriter.print("<tr><td>用户</td><td>单位</td><td>部门</td><td>电话</td></tr>");
        list.forEach(et->{
            printWriter.print(("<tr><td>" +et.getName() + "</td><td>" + et.getUnit()+ "</td><td>" +et.getDepartment() + "</td><td>" + et.getPhone()+ "</td></tr>"));
        });
        printWriter.print("</table>");
        printWriter.print("</body></html>");
    }



    /**
     * 目前报餐列表
     * @return
     */
    @RequestMapping("/addList")
    public void addList(HttpServletResponse response) throws IOException {
        /**
         * 获取用户列表
         */
        List<NewspaperMeal> list = new ArrayList<>();
        Set keys = redisTemplate.keys("JN-Aristotle:report:*");
        keys.forEach(x->{
            list.add((NewspaperMeal) redisTemplate.opsForValue().get(x));
        });
        response.setCharacterEncoding("gbk");
        PrintWriter printWriter = response.getWriter();
        printWriter.print("<html><head>");
        printWriter.print("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">");
        printWriter.print("<title>目前报餐列表</title>");
        printWriter.print("</head>");
        printWriter.print("<body>");
        printWriter.print("<table border=1 cellpadding=10 cellspacing=0>");
        printWriter.print("<tr  ><td colspan='4' text-align=center >今日报餐列表</td></tr>");
        printWriter.print("<tr><td>用户</td><td>单位</td><td>部门</td><td>报餐时间</td></tr>");
        list.forEach(et->{
            printWriter.print(("<tr><td>" +et.getName() + "</td><td>" + et.getUnit()+ "</td><td>" +et.getDepartment() + "</td><td>" + et.getTime()+ "</td></tr>"));
        });
        printWriter.print("</table>");
        printWriter.print("</body></html>");


    }


    @RequestMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        Set keys = redisTemplate.keys("JN-Aristotle:*");
        List<NewspaperMeal> list = new ArrayList<>();
        keys.forEach(x->{
            System.out.println(x);
            NewspaperMeal o = (NewspaperMeal) redisTemplate.opsForValue().get(x);
            System.out.println(o.toString());
            list.add(o);
        });
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        String format = dateFormat.format(new Date());
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(format+"报餐情况","报餐情况"),
                NewspaperMeal.class, list);


        String gbk = new String((format+"报餐情况.xls").getBytes("utf-8"), "iso8859-1");
        response.setHeader("Content-Disposition", "attachment;fileName=" + gbk);
        workbook.write(response.getOutputStream());
        workbook.close();



    }

}
