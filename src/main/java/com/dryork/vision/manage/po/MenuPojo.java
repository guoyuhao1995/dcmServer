package com.dryork.vision.manage.po;

import java.util.List;

public class MenuPojo {

	private Integer id;

	private String text;

	private String icon;

	private String url;

	private boolean isOpen = false;

	private List<MenuPojo> menus;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public List<MenuPojo> getMenus() {
		return menus;
	}

	public void setMenus(List<MenuPojo> menus) {
		this.menus = menus;
	}

}
