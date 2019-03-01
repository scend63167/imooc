package com.imooc.demo.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 菜单管理
 * </p>
 *
 * @author zhachengwei
 * @since 2019-02-25
 */
@TableName("sys_menu")
public class SysMenu extends Model<SysMenu> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 父菜单ID，一级菜单为0
     */
	@TableField("parent_id")
	private Long parentId;
    /**
     * 菜单名称
     */
	private String name;
    /**
     * 菜单URL
     */
	private String url;
    /**
     * 授权标识
     */
	private String code;
    /**
     * 类型   0：目录   1：菜单   2：按钮
     */
	private Integer type;
    /**
     * 菜单图标
     */
	private String icon;
    /**
     * 排序
     */
	@TableField("order_num")
	private Integer orderNum;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "SysMenu{" +
			"id=" + id +
			", parentId=" + parentId +
			", name=" + name +
			", url=" + url +
			", code=" + code +
			", type=" + type +
			", icon=" + icon +
			", orderNum=" + orderNum +
			"}";
	}
}
