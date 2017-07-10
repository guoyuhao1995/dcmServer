package com.dryork.vision.base.util;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;

import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.dryork.vision.manage.pojo.PatientReportPojo;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfUtil {
	/**
	 * 创建pdf文件
	 * @param record  患者眼底信息
	 * @param pdfPath pdf路径
	 */
	public static Integer createPdf(PatientReportPojo record,String pdfPath){
		Integer result = 0;
		Document document = new Document(PageSize.A4); 
		 try 
	        {
	            PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
	            //设置字体
	            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);   
	            com.itextpdf.text.Font FontChinese24 = new com.itextpdf.text.Font(bfChinese, 24, com.itextpdf.text.Font.BOLD);
	            com.itextpdf.text.Font FontChinese18 = new com.itextpdf.text.Font(bfChinese, 18, com.itextpdf.text.Font.BOLD); 
	            com.itextpdf.text.Font FontChinese16 = new com.itextpdf.text.Font(bfChinese, 16, com.itextpdf.text.Font.BOLD);
	            com.itextpdf.text.Font FontChinese12 = new com.itextpdf.text.Font(bfChinese, 12, com.itextpdf.text.Font.NORMAL);
	            com.itextpdf.text.Font FontChinese11Bold = new com.itextpdf.text.Font(bfChinese, 11, com.itextpdf.text.Font.BOLD);
	            com.itextpdf.text.Font FontChinese11 = new com.itextpdf.text.Font(bfChinese, 11, com.itextpdf.text.Font.ITALIC);
	            com.itextpdf.text.Font FontChinese11Normal = new com.itextpdf.text.Font(bfChinese, 11, com.itextpdf.text.Font.NORMAL);

	            document.open();
	            //体检中心名称
	            PdfPTable title = new PdfPTable(1);
	            PdfPCell cell11 = new PdfPCell(new Paragraph(record.getMecName(),FontChinese24));
	            cell11.setVerticalAlignment(Element.ALIGN_MIDDLE);
	            cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
	            cell11.setBorder(0);
	            //设置每列宽度比例   
	            int width11[] = {100};
	            title.setWidths(width11); 
	            title.getDefaultCell().setBorder(0);
	            title.addCell(cell11);  
	            document.add(title);
	            
	            //加入空行
	            Paragraph blankRow1 = new Paragraph(8f, " ", FontChinese12); 
	            document.add(blankRow1);
	            
	            //眼底图像筛查报告
	            PdfPTable title1 = new PdfPTable(1);
	            PdfPCell cellTitle1 = new PdfPCell(new Paragraph("眼底图像筛查报告",FontChinese18));
	            cellTitle1.setVerticalAlignment(Element.ALIGN_MIDDLE);
	            cellTitle1.setHorizontalAlignment(Element.ALIGN_CENTER);
	            cellTitle1.setBorder(0);
	            //设置每列宽度比例   
	            int widthTitle1[] = {100};
	            title1.setWidths(widthTitle1); 
	            title1.getDefaultCell().setBorder(0);
	            title1.addCell(cellTitle1);  
	            document.add(title1);
	            
	            //加入空行
	            document.add(blankRow1);
	            
	            
	            //患者基本信息
	            PdfPTable table2 = new PdfPTable(3);
	            //设置每列宽度比例   
	            int width21[] = {33,33,34};
	            int width20[] = {50,16,34};
	            table2.setWidths(width20); 
	            table2.getDefaultCell().setBorder(0);
	            PdfPCell cellPatientInfo1 = new PdfPCell(new Paragraph("患者编号："+record.getPatNo(),FontChinese12));
	            cellPatientInfo1.setBorder(0);
	            cellPatientInfo1.setBorderWidthBottom(0.5f);
	            PdfPCell cellPatientInfo2 = new PdfPCell(new Paragraph(" "));
	            cellPatientInfo2.setBorder(0);
	            cellPatientInfo2.setBorderWidthBottom(0.5f);
	            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
	            PdfPCell cellPatientInfo3 = new PdfPCell(new Paragraph("筛查日期："+sdf.format(record.getScreenTime()),FontChinese12));
	            cellPatientInfo3.setBorder(0);
	            cellPatientInfo3.setBorderWidthBottom(0.5f);
	            table2.addCell(cellPatientInfo1); 
	            table2.addCell(cellPatientInfo2); 
	            table2.addCell(cellPatientInfo3); 
	            document.add(table2);
	            //加入空行
	            document.add(blankRow1);
	            
	            PdfPTable table3 = new PdfPTable(3);
	            //设置每列宽度比例   
	            table3.setWidths(width21); 
	            table3.getDefaultCell().setBorder(0);
	            PdfPCell cellPatientInfo11 = new PdfPCell(new Paragraph("姓名："+record.getName(),FontChinese12));
	            cellPatientInfo11.setBorder(0);
	            cellPatientInfo11.setBorderWidthBottom(0.5f);
	            PdfPCell cellPatientInfo21 = new PdfPCell(new Paragraph("年龄："+record.getAge(),FontChinese12));
	            cellPatientInfo21.setBorder(0);
	            cellPatientInfo21.setBorderWidthBottom(0.5f);
	            PdfPCell cellPatientInfo31 = new PdfPCell(new Paragraph("性别："+record.getSex(),FontChinese12));
	            cellPatientInfo31.setBorder(0);
	            cellPatientInfo31.setBorderWidthBottom(0.5f);
	            table3.addCell(cellPatientInfo11); 
	            table3.addCell(cellPatientInfo31); 
	            table3.addCell(cellPatientInfo21); 
	            document.add(table3);
	            //加入空行
	            document.add(blankRow1);
	            
	            PdfPTable table4 = new PdfPTable(3);
	            //设置每列宽度比例   
	            table4.setWidths(width21); 
	            table4.getDefaultCell().setBorder(0);
	            PdfPCell cellPatientInfo12 = new PdfPCell(new Paragraph("眼压(左)："+record.getIopLeft(),FontChinese12));
	            cellPatientInfo12.setBorder(0);
	            cellPatientInfo12.setBorderWidthBottom(0.5f);
	            PdfPCell cellPatientInfo22 = new PdfPCell(new Paragraph("视力(左)："+record.getVisionLeft(),FontChinese12));
	            cellPatientInfo22.setBorder(0);
	            cellPatientInfo22.setBorderWidthBottom(0.5f);
	            PdfPCell cellPatientInfo32 = new PdfPCell(new Paragraph("矫正视力(左)："+record.getCorrectedLeft(),FontChinese12));
	            cellPatientInfo32.setBorder(0);
	            cellPatientInfo32.setBorderWidthBottom(0.5f);
	            table4.addCell(cellPatientInfo12); 
	            table4.addCell(cellPatientInfo22); 
	            table4.addCell(cellPatientInfo32); 
	            document.add(table4);
	            //加入空行
	            document.add(blankRow1);
	            
	            PdfPTable table5 = new PdfPTable(3);
	            //设置每列宽度比例   
	            table5.setWidths(width21); 
	            table5.getDefaultCell().setBorder(0);
	            table5.getDefaultCell().setBorderWidthBottom(1f);
	            PdfPCell cellPatientInfo13 = new PdfPCell(new Paragraph("眼压(右)："+record.getIopRight(),FontChinese12));
	            cellPatientInfo13.setBorder(0);
	            cellPatientInfo13.setBorderWidthBottom(0.5f);
	            PdfPCell cellPatientInfo23 = new PdfPCell(new Paragraph("视力(右)："+record.getVisionRight(),FontChinese12));
	            cellPatientInfo23.setBorder(0);
	            cellPatientInfo23.setBorderWidthBottom(0.5f);
	            PdfPCell cellPatientInfo33 = new PdfPCell(new Paragraph("矫正视力(右)："+record.getCorrectedRight(),FontChinese12));
	            cellPatientInfo33.setBorder(0);
	            cellPatientInfo33.setBorderWidthBottom(0.5f);
	            table5.addCell(cellPatientInfo13); 
	            table5.addCell(cellPatientInfo23); 
	            table5.addCell(cellPatientInfo33); 
	            document.add(table5);
	            //加入空行
	            document.add(blankRow1);
	            
	            //设置眼底照片
	            PdfPTable tableImg = new PdfPTable(2);
	            int[] imgwidth = {50,50};
	            tableImg.setWidths(imgwidth);
	            tableImg.getDefaultCell().setBorder(0);
	            String imagePathl = record.getImages().get(0).getUrl();
	            String imagePathr = record.getImages().get(1).getUrl();
	            Image image = Image.getInstance(imagePathl); 
	            tableImg.addCell(image);
	            Image image1 = Image.getInstance(imagePathr); 
	            tableImg.addCell(image1);
	            document.add(tableImg);
	            //加入空行
	            document.add(blankRow1);
	            
	            //设置眼睛
	            PdfPTable tableYj = new PdfPTable(2);
	            tableYj.setWidths(imgwidth);
	            PdfPCell cellYjl = new PdfPCell(new Paragraph("眼睛："+record.getImages().get(0).getWhichStr(), FontChinese12));
	            PdfPCell cellYjr = new PdfPCell(new Paragraph("眼睛："+record.getImages().get(1).getWhichStr(), FontChinese12));
	            cellYjl.setBorder(0);
	            cellYjr.setBorder(0);
	            tableYj.addCell(cellYjl);
	            tableYj.addCell(cellYjr);
	            document.add(tableYj);
	            //加入空行
	            document.add(blankRow1);
	            
	            //设置影像描述
	            PdfPTable tableYx = new PdfPTable(2);
	            tableYx.setWidths(imgwidth);
	            PdfPCell cellYxl= new PdfPCell(new Paragraph("影像描述：", FontChinese12));
	            PdfPCell cellYxr = new PdfPCell(new Paragraph("影像描述：", FontChinese12));
	            cellYxl.setBorder(0);
	            cellYxr.setBorder(0);
	            tableYx.addCell(cellYxl);
	            tableYx.addCell(cellYxr);
	            document.add(tableYx);
	            //加入空行
	            document.add(blankRow1);
	            
	            //设置CD
	            PdfPTable tableCD = new PdfPTable(4);
	            int[] yxms = {3,47,3,47};
	            tableCD.setWidths(yxms);
	            PdfPCell cellCDl = new PdfPCell(new Paragraph("C/D："+record.getImages().get(0).getCdStr(), FontChinese12));
	            PdfPCell cellCDr = new PdfPCell(new Paragraph("C/D："+record.getImages().get(1).getCdStr(), FontChinese12));
	            PdfPCell cellyxms = new PdfPCell(new Paragraph(" ", FontChinese12));
	            cellCDl.setBorder(0);
	            cellCDr.setBorder(0);
	            cellyxms.setBorder(0);
	            cellyxms.setBorder(0);
	            tableCD.addCell(cellyxms);
	            tableCD.addCell(cellCDl);
	            tableCD.addCell(cellyxms);
	            tableCD.addCell(cellCDr);
	            document.add(tableCD);
	            //加入空行
	            document.add(blankRow1);
	            
	            //设置盘沿形态
	            PdfPTable tablePy = new PdfPTable(4);
	            tablePy.setWidths(yxms);
	            PdfPCell cellPyl = new PdfPCell(new Paragraph("盘沿形态："+record.getImages().get(0).getPlateFormStr(), FontChinese12));
	            PdfPCell cellPyr = new PdfPCell(new Paragraph("盘沿形态："+record.getImages().get(1).getPlateFormStr(), FontChinese12));
	            cellPyl.setBorder(0);
	            cellPyr.setBorder(0);
	            tablePy.addCell(cellyxms);
	            tablePy.addCell(cellPyl);
	            tablePy.addCell(cellyxms);
	            tablePy.addCell(cellPyr);
	            document.add(tablePy);
	            //加入空行
	            document.add(blankRow1);
	            
	            //设置AV值
	            PdfPTable tableAV = new PdfPTable(4);
	            tableAV.setWidths(yxms);
	            PdfPCell cellAVl = new PdfPCell(new Paragraph("A/V值："+record.getImages().get(0).getAvStr(), FontChinese12));
	            PdfPCell cellAVr = new PdfPCell(new Paragraph("A/V值："+record.getImages().get(1).getAvStr(), FontChinese12));
	            cellAVl.setBorder(0);
	            cellAVr.setBorder(0);
	            tableAV.addCell(cellyxms);
	            tableAV.addCell(cellAVl);
	            tableAV.addCell(cellyxms);
	            tableAV.addCell(cellAVr);
	            document.add(tableAV);
	            //加入空行
	            document.add(blankRow1);
	            
	            //设置黄斑区出血、渗出
	            PdfPTable tableHb = new PdfPTable(4);
	            tableHb.setWidths(yxms);
	            PdfPCell cellHbl = new PdfPCell(new Paragraph("黄斑区："+record.getImages().get(0).getMacularRegionStr(), FontChinese12));
	            PdfPCell cellHbr = new PdfPCell(new Paragraph("黄斑区："+record.getImages().get(1).getMacularRegionStr(), FontChinese12));
	            cellHbl.setBorder(0);
	            cellHbr.setBorder(0);
	            tableHb.addCell(cellyxms);
	            tableHb.addCell(cellHbl);
	            tableHb.addCell(cellyxms);
	            tableHb.addCell(cellHbr);
	            document.add(tableHb);
	            //加入空行
	            document.add(blankRow1);
	            
	            //设置周边视网膜出血、渗出
	            PdfPTable tableZb = new PdfPTable(4);
	            tableZb.setWidths(yxms);
	            PdfPCell cellZbl = new PdfPCell(new Paragraph("周边视网膜："+record.getImages().get(0).getRetinaRegionStr(), FontChinese12));
	            PdfPCell cellZbr = new PdfPCell(new Paragraph("周边视网膜："+record.getImages().get(1).getRetinaRegionStr(), FontChinese12));
	            cellZbl.setBorder(0);
	            cellZbr.setBorder(0);
	            tableZb.addCell(cellyxms);
	            tableZb.addCell(cellZbl);
	            tableZb.addCell(cellyxms);
	            tableZb.addCell(cellZbr);
	            document.add(tableZb);
	            //加入空行
	            document.add(blankRow1);
	            
	            //设置其他
	            PdfPTable tableQt = new PdfPTable(4);
	            tableQt.setWidths(yxms);
	            PdfPCell cellQtl = new PdfPCell(new Paragraph("其他："+record.getImages().get(0).getOtherStr(), FontChinese12));
	            cellQtl.setBorderWidthBottom(0.5f);
	            PdfPCell cellQtr = new PdfPCell(new Paragraph("其他："+record.getImages().get(1).getOtherStr(), FontChinese12));
	            cellQtr.setBorderWidthBottom(0.5f);
	            cellQtl.setBorder(0);
	            cellQtr.setBorder(0);
	            cellyxms.setBorderWidthBottom(0.5f);
	            cellQtl.setBorderWidthBottom(0.5f);
	            cellQtr.setBorderWidthBottom(0.5f);
	            tableQt.addCell(cellyxms);
	            tableQt.addCell(cellQtl);
	            tableQt.addCell(cellyxms);
	            tableQt.addCell(cellQtr);
	            document.add(tableQt);
	            //加入空行
	            document.add(blankRow1);
	            
	            //设置印象、建议、二维码、提示
	            PdfPTable tableEw = new PdfPTable(2);
	            int[] ewm = {85,15};
	            tableEw.setWidths(ewm);
	            	int[] left = {100};
	            	//印象
	            	PdfPTable Ewl = new PdfPTable(1);
	            	Ewl.setWidths(left);
	            	PdfPCell cellYin = new PdfPCell(new Paragraph("印象："+record.getImpressionStr(), FontChinese12));
	            	PdfPCell cellJian = new PdfPCell(new Paragraph("建议："+record.getSuggestStr(), FontChinese12));
	            	PdfPCell cellTi = new PdfPCell(new Paragraph("此报告仅作为筛查结果,不作为临床诊断。", FontChinese12));
	            	PdfPCell cellUrl = new PdfPCell(new Paragraph("在线报告地址："+ConstantsPond.SERVER_PATH+"report/toReport?id="+record.getId(), FontChinese12));
	            	cellJian.setBorder(0);
	            	cellTi.setBorder(0);
	            	cellUrl.setBorder(0);
	            	cellYin.setBorder(0);
	            	cellJian.setBorderWidthBottom(0.5f);
	            	Ewl.addCell(cellYin);
	            	Ewl.addCell(cellJian);
	            	Ewl.addCell(cellTi);
	            	Ewl.addCell(cellUrl);
	            	PdfPCell ewlCell = new PdfPCell(Ewl);
	            	ewlCell.setBorder(0);
	            tableEw.addCell(ewlCell);
	            String ewmPath = ConstantsPond.PATIENT_QRCODE+record.getUrl();
	            Image ewmImage = Image.getInstance(ewmPath); 
	            ewmImage.scaleAbsolute(mmTopx(36), mmTopx(36));
	            ewmImage.setBorder(0);
	            PdfPCell ewlimage = new PdfPCell(ewmImage);
	            ewlimage.setBorder(0);
	            tableEw.addCell(ewlimage);
	            document.add(tableEw);
	            result = 1;
	        } catch (Exception ex) 
	        {
	          ex.printStackTrace();
	          TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	          result = 0;
	        }finally{
	        	document.close();
	        }
		 return result;
	}
	public static float mmTopx(float mm){
	     mm = (float) (mm *3.33) ;
	     return mm ;
	    }
	public static void main(String[] args) {
		PdfUtil.createPdf(null, null);
	}
}
