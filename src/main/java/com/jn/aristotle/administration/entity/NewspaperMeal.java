package com.jn.aristotle.administration.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.util.Date;

/**
 * 报餐实体
 */
public class NewspaperMeal {
    /**
     * 名字
     */
    @Excel(name = "员工名字", height = 10, width = 30, isImportField = "true_st")
    private String name ;
    /**
     * 单位
     */
    @Excel(name = "单位", height = 10, width = 30, isImportField = "true_st")
    private String unit;
    /**
     * 部门
     */
    @Excel(name = "部门", height = 10, width = 30, isImportField = "true_st")
    private String department;
    /**
     * 电话
     */
    private String phone;
    /**
     * 时间
     */
    @Excel(name = "报餐时间", height = 10, width = 30, isImportField = "true_st")
    private String time ;

    /**
     * 是否启用 0 未启用 99为启用
     * @return
     */
    private Integer isEnable;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    @Override
    public String toString() {
        return "NewspaperMeal{" +
                "name='" + name + '\'' +
                ", unit='" + unit + '\'' +
                ", department='" + department + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
