package com.dryork.vision.manage.dao;

import java.io.Serializable;
import java.util.List;

import com.dryork.vision.manage.po.Image;

/** (VISION_IMAGE) **/
public interface ImageDao {

	public Image find(Serializable id);

	public List<Image> findAll();

	public int create(Image t);

	public int update(Image t);

	public int delete(Serializable id);

	public int deleteAll();

	/** codegen **/
}