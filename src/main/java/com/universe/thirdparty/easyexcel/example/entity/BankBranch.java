package com.universe.thirdparty.easyexcel.example.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @author 刘亚楼
 * @date 2020/12/21
 */
public class BankBranch implements Serializable {

	private static final long serialVersionUID = 6603207909962881929L;

	/**
	 * 分行ID
	 */
	@ExcelIgnore
	private Long branchId;

	/**
	 * 分行名称
	 */
	@ExcelProperty(index = 1)
	private String branchName;

	/**
	 * 所属省份
	 */
	@ExcelProperty(index = 2)
	private String province;

	/**
	 * 所属市
	 */
	@ExcelProperty(index = 3)
	private String city;

	/**
	 * 详细地址
	 */
	@ExcelProperty(index = 4)
	private String address;

	/**
	 * 联系电话
	 */
	@ExcelProperty(index = 5)
	private String contact;

	/**
	 * 服务描述(英文)
	 */
	@ExcelProperty(index = 6)
	private String serviceEn;

	/**
	 * 服务描述(印尼文)
	 */
	@ExcelProperty(index = 7)
	private String serviceIn;

	/**
	 * 创建时间
	 */
	@ExcelIgnore
	private long createTime;

	/**
	 * 修改时间
	 */
	@ExcelIgnore
	private long updateTime;

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getServiceEn() {
		return serviceEn;
	}

	public void setServiceEn(String serviceEn) {
		this.serviceEn = serviceEn;
	}

	public String getServiceIn() {
		return serviceIn;
	}

	public void setServiceIn(String serviceIn) {
		this.serviceIn = serviceIn;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
