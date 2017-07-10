/**
* @author wenghongbo
* @version Created on 上午10:21:52
*/ 

package com.dryork.vision.base.util.image;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;


public class EncodePng {

	
	private byte[] pngBytes;
	private int width, height;
    private int bytePos, maxPos;
    private CRC32 crc = new CRC32();
    private static final byte IHDR[] = {73, 72, 68, 82};
    private static final byte IDAT[] = {73, 68, 65, 84};
    private static final byte PLTE[] = {80, 76, 84, 69};
    private static final byte IEND[] = {73, 69, 78, 68};
    private static final byte tRNS[] ={116, 82, 78, 83};// 0x74524e53;
    private static final byte  pngIdBytes[] = {-119, 80, 78, 71, 13, 10, 26, 10};
    private int pixels[];
    private int palette[];
    private byte trns[];
    
    /**
     * @param int palette[] 调色�?
     * @param int pixels[][] 像素
     * */
	public EncodePng(int palette[], int pixels[],byte trns[],int width,int height){
		this.width=width;
		this.height=height;
		this.pixels=pixels;
		this.palette=palette;
		this.trns=trns;
	}

	public byte[] pngEncode() throws IOException{		      
        pngBytes = new byte[((width + 1) * height * 3) + 200];
        maxPos = 0;
        bytePos = writeBytes(pngIdBytes, 0);
        writeIHDR();
        writePLTE();
        if(trns!=null)writetRNS();
        writeIDAT();
        writeIEND();
        pngBytes = resizeByteArray(pngBytes, maxPos);
        return pngBytes;
	}
	
	 
	public void writeIHDR(){
        int startPos;
        startPos = bytePos = writeInt4(13, bytePos);
        bytePos = writeBytes(IHDR, bytePos);
        bytePos = writeInt4(width, bytePos);
        bytePos = writeInt4(height, bytePos);
        bytePos = writeByte(8, bytePos); 
        bytePos = writeByte(3, bytePos); 
        bytePos = writeByte(0, bytePos); 
        bytePos = writeByte(0, bytePos); 
        bytePos = writeByte(0, bytePos);
        crc.reset();
        crc.update(pngBytes, startPos, bytePos - startPos);
        bytePos = writeInt4((int) crc.getValue(), bytePos);
	}
	
	private void writePLTE(){

		byte[] allPal=new byte[palette.length*3];
        bytePos = writeInt4(allPal.length, bytePos);
        bytePos = writeBytes( PLTE, bytePos);
        crc.reset();
        crc.update(PLTE);
        for(int i=palette.length;i-->0;){
        	allPal[i*3]=(byte) ((palette[i] >> 16) & 0xff);
        	allPal[i*3+1]=(byte) ((palette[i] >> 8) & 0xff);
        	allPal[i*3+2]=(byte) (palette[i] & 0xff);
        }
        bytePos = writeBytes(allPal, bytePos);
        crc.update( allPal );
        bytePos = writeInt4((int)crc.getValue(), bytePos);
	}
	
	private void writetRNS(){
		
		 bytePos = writeInt4(trns.length, bytePos);
		 bytePos = writeBytes(tRNS, bytePos);
	     crc.reset();
	     crc.update(tRNS);
	     bytePos = writeBytes(trns, bytePos);
	     crc.update(trns);
	     bytePos = writeInt4((int)crc.getValue(), bytePos);	 
	}
	
	public void writeIDAT() throws IOException{

        Deflater scrunch = new Deflater(9);
        ByteArrayOutputStream outBytes = new ByteArrayOutputStream(); 
        DeflaterOutputStream compBytes =new DeflaterOutputStream( outBytes, scrunch );
        for(int i=0;i<pixels.length;i++){
        	if(i%width==0)compBytes.write((byte)0);
        	compBytes.write((byte)pixels[i]);
        }
     	compBytes.close();
        byte[] pix=outBytes.toByteArray();
        int nCompressed = pix.length;
		bytePos=writeInt4(nCompressed,bytePos);
		bytePos = writeBytes(IDAT, bytePos);
		crc.reset();
		crc.update(IDAT);
		bytePos = writeBytes(pix, nCompressed, bytePos);
		crc.update(pix, 0, nCompressed);
		
        bytePos = writeInt4((int) crc.getValue(), bytePos);
        scrunch.finish();
	}
	
	 
	public void writeIEND(){
        bytePos = writeInt4(0, bytePos);
        bytePos = writeBytes(IEND, bytePos);
        crc.reset();
        crc.update(IEND);
        bytePos = writeInt4((int) crc.getValue(), bytePos);
	}

	/*********以下为自定义方法*********/
	private byte[] resizeByteArray(byte[] array, int newLength) {
        byte[]  newArray = new byte[newLength];
        int     oldLength = array.length;
        System.arraycopy(array, 0, newArray, 0, Math.min(oldLength, newLength));
        return newArray;
	}
	    
	private int writeBytes(byte[] data, int offset) {
	    maxPos = Math.max(maxPos, offset + data.length);
	    if (data.length + offset > pngBytes.length) {
	        pngBytes = resizeByteArray(pngBytes, pngBytes.length + Math.max(1000, data.length));
	    }
	    System.arraycopy(data, 0, pngBytes, offset, data.length);
	    return offset + data.length;
	}
	
	private int writeInt4(int n, int offset) {
	    byte[] temp = {(byte) ((n >> 24) & 0xff),
	                   (byte) ((n >> 16) & 0xff),
	                   (byte) ((n >> 8) & 0xff),
	                   (byte) (n & 0xff)};
	    return writeBytes(temp, offset);
	}
	
	private int writeByte(int b, int offset) {
	    byte[] temp = {(byte) b};
	    return writeBytes(temp, offset);
	}
	
	private int writeBytes(byte[] data, int nBytes, int offset) {
        maxPos = Math.max(maxPos, offset + nBytes);
        if (nBytes + offset > pngBytes.length) {
            pngBytes = resizeByteArray(pngBytes, pngBytes.length + Math.max(1000, nBytes));
        }
        System.arraycopy(data, 0, pngBytes, offset, nBytes);
        return offset + nBytes;
    }
    
}
