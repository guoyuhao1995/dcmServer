package com.dryork.vision.base.util.image;

import java.io.InputStream;

public abstract class ImageReader{
	
    int width = 0;
	int height = 0;
	int pix[] = null;	
	int bitsPerPixel = 0;
	boolean indexedColor =  false;
    int colorPalette[] = null;

	public abstract void unpackImage(InputStream is) throws Exception;

	
	public int getColorDepth(){
	   return bitsPerPixel;
	}
    
	public int[] getColorPalette(){
       return colorPalette; 
	}

	public boolean isIndexedColor(){
		return indexedColor;
	}

	public int[] getImageData(){
	   return pix;
	}  
} 