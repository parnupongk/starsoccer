package com.isport_starsoccer.data;

public class DataElementEvent
{
	public String img1 = "";
	public String img2 = "";
	public String img3 = "";
	public String description = "";
	public String phone = "";
	public String url = "";
	public boolean checkLoad = false;
	
	public DataElementEvent(String img1, String img2, String img3, String description, String phone, String url)
	{
		this.img1 = img1;
		this.img2 = img2;
		this.img3 = img3;
		this.description = description;
		this.phone = phone;
		this.url = url;
	}
}
