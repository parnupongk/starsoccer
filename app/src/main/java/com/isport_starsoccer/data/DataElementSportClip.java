package com.isport_starsoccer.data;

import java.io.Serializable;

public class DataElementSportClip implements Serializable
{
	public String clipId = "";
	public String clipTopic = "";
	public String clipImage = "";
	public String clipURL = "";
	public String clipDate = "";
//	public Bitmap 
	public boolean checkLoad = false;
	
	public DataElementSportClip(String clipId, String clipTopic , String clipImage, String clipURL, String clipDate)
	{
		this.clipId = clipId;
		this.clipTopic = clipTopic;
		this.clipImage = clipImage;
		this.clipURL = clipURL;
		this.clipDate = clipDate;
	}
}
