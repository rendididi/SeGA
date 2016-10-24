package org.sega.viewer.models;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tpg_gzfsqspb")
public class Approve extends BaseModel{

	/**
	 * author WXF
	 */
	private static final long serialVersionUID = 1L;
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public Integer getYWSLID() {
		return YWSLID;
	}
	public void setYWSLID(Integer yWSLID) {
		YWSLID = yWSLID;
	}
	public Integer getYWLB() {
		return YWLB;
	}
	public void setYWLB(Integer yWLB) {
		YWLB = yWLB;
	}
	public Integer getSPND() {
		return SPND;
	}
	public void setSPND(Integer sPND) {
		SPND = sPND;
	}
	public Integer getSQRXM() {
		return SQRXM;
	}
	public void setSQRXM(Integer sQRXM) {
		SQRXM = sQRXM;
	}
	private Integer code;
	private Integer YWSLID;
	private Integer YWLB;
	private Integer SPND;
	private Integer SQRXM;
	
}
