package com.pifss.myway;

import android.graphics.Bitmap;

public class ItemMenu {
	String title;
	Bitmap image;
	
	public ItemMenu() {
		// TODO Auto-generated constructor stub
	}

	
	public ItemMenu(String title,  Bitmap image) {
		super();
		this.title = title;
		this.image = image;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		
		
		return result;
	}
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemMenu other = (ItemMenu) obj;
		
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		
		return true;
	}
	
	
	
	
}
