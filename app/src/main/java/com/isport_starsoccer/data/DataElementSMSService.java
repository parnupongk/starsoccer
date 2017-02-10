package com.isport_starsoccer.data;

import java.io.Serializable;

import android.graphics.Bitmap;

public class DataElementSMSService implements Serializable
{
	public String pssv_id = "";
	public String pssv_name = "";
	public String pssv_shortcode = "";
	public String pssv_cancelcode = "";
	public String pssv_price = "";
	public String pssv_promotion = "";
	public String pssv_desc = "";
	public String pssv_image = "";
	public String pssv_remark1 = "";
	public String pssv_remark2 = "";
	
	public Bitmap image = null;
	public boolean checkLoad = false;
	
	public DataElementSMSService(String pssv_id, String pssv_name, String pssv_shortcode, 
			String pssv_price, String pssv_promotion,String pssv_desc
			,String pssv_image,String pssv_cancelcode,String pssv_remark1
			,String pssv_remark2)
	{
		this.pssv_id = pssv_id;
		this.pssv_name = pssv_name;
		this.pssv_shortcode = pssv_shortcode;
		this.pssv_price = pssv_price;
		this.pssv_promotion = pssv_promotion;
		this.pssv_desc = pssv_desc;
		this.pssv_image = pssv_image;
		this.pssv_cancelcode = pssv_cancelcode;
		this.pssv_remark1 = pssv_remark1;
		this.pssv_remark2 = pssv_remark2;
	}
}
