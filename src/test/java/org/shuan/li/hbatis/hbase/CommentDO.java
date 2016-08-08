package org.shuan.li.hbatis.hbase;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

/**
 * CommentDO
 */
public class CommentDO implements Serializable {


	private static final long serialVersionUID = 5262854138601413631L;
	/**
	 * 主键
	 */
	@HColumn(name = "id")
	private Long id=0L;

	/**
	 * 创建时间
	 */
	@HColumn(name = "gmt_create")
	private Date gmtCreate;

	/**
	 * 修改时间
	 */
	@HColumn(name = "gmt_modified")
	private Date gmtModified;


	@HColumn(name = "app_name")
	private String appName;
	/**
	 * 外部关联id
	 */
	@HColumn(name = "source_id")
	private String sourceId;

	/**
	 * 评论的内容，仅包括文本
	 */
	@HColumn(name = "text")
	private String text;


	/**
	 * 评论的来源
	 */
	@HColumn(name = "source")
	private String source;
	/**
	 * 评论状态，0表示未审核状态，1表示审核通过，-1表示审核不通过
	 */
	@HColumn(name = "status")
	private int status;
	/**
	 * 评论的类型
	 */
	@HColumn(name = "type")
	private int type;
	/**
	 * 设备信息
	 */
	@HColumn(name = "device_info")
	private String deviceInfo;

	/**
	 * 楼层
	 */
	@HColumn(name = "floor")
	private Integer floor=0;

	/**
	 * 审核时间，用于正确展示楼层
	 */
	@HColumn(name = "audit_time")
	private Date auditTime;


	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public Integer getFloor() {
		return floor;
	}

	public void setFloor(Integer floor) {
		this.floor = floor;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
}
