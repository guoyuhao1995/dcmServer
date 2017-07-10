/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dryork.vision.base.util.dcm;

import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.ElementDictionary;
import org.dcm4che3.data.Fragments;
import org.dcm4che3.data.Sequence;
import org.dcm4che3.data.Tag;
import org.dcm4che3.data.VR;
import org.dcm4che3.io.DicomEncodingOptions;
import org.dcm4che3.io.DicomInputStream;
import org.dcm4che3.io.DicomOutputStream;
import org.dcm4che3.util.SafeClose;

/**
 * Using for metadata of Dicom 3.0
 * @author Dimitri
 *@email : dimitri.pianeta@gmail.com
 */
public final class DisplayTag {
    private static Attributes obj=null, object =null;
    private static  DicomInputStream din; 
    private static double resultFactorDix;
    private String result = null;
    private Double result2 = null;
    private String nom = null;
	private String nounString = null;
    private int val2 = 0;
    private int valeurReturn;
    private String nounUnit = null;
    private static double resultFacteurDix	= 0;
    private Double valueSpatial = null;
    private String nounUnitRatio = null;
    private   DicomInputStream dis;
    private static final char[] HEX_DIGITS = {
	'0' , '1' , '2' , '3' , '4' , '5' ,
	'6' , '7' , '8' , '9' , 'A' , 'B' ,
	'C' , 'D' , 'E' , 'F'
	};
    private DicomEncodingOptions encOpts = DicomEncodingOptions.DEFAULT;
    private static ElementDictionary dict = ElementDictionary.getStandardElementDictionary();
    
    
    public DisplayTag(File file ){
        try { 
            setObject(loadDicomObject(file) );
        } catch (IOException ex) {
            Logger.getLogger(DisplayTag.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    
    
    /**
     *  Read metadata of Dicom 3.0
     * @param f : input file
     * @return Attributes
     * @throws IOException 
     */
    public static Attributes loadDicomObject(File f) throws IOException {
        if (f == null){
            return null;
        }else{
        	DicomInputStream dis = new DicomInputStream(f);
        	//attr.setSpecificCharacterSet("GBK");
            return dis.readDataset(-1, -1);
        }
    }
    
    /**
     * Put attribut
     * @param obj 
     */
     public  void setObject(Attributes obj){
         this.obj = obj; 
     }
     
     
     /**
      * Giving attribut of metadata
      * @return 
      */
     public static Attributes getObject(){
         return obj; 
         
     }
     
    
          /**
	 * Display metadata 
	 * @param file : file inout
	 * @param TAG : new tag
	 * @throws IOException
	 */
     public String readTagDicom(File file ) throws IOException{
	    din = new DicomInputStream(file);
        object = din.readFileMetaInformation() ; 
	 	String value = object.toString();
        object = din.readDataset(-1, -1);
     //  String value2 =  object.toString(0x00020002,Tag.PixelData);
        //String value2 =  object.toString(0x00020002,Tag.PatientName);
        return value; //+ value2;
     }


	/**
	 * Permet d'afficher l'heure d'une valeur dicom en standard international yyyy.mm.dd/ Permit display time in format yyyy.mm.dd
	 * @param Tag : valeur du tag / int tag
	 * @param valueBool : si true Format yyyy.mm.dd sinon format dd.mm.yyyy/ if true then format yyyy.mm.dd else dd.mm.yyyy
	 * @param valueNoun :  "dot" mettre la date en format yyyy.mm.dd ou dd.mm.yyyy sinon en format yyyy mm dd ou dd mm yyyy/ "dot" put yyyy.mm.dd or dd.mm.dd or dd.mm.yyyy else yyyy mm or dd mm yyyy
	 * @return afficher le string du tag selon le standard international/ return string Date
	 * @throws IOException
	 */
     public String dicomDate(int Tag,boolean valueBool, String valueNoun) throws IOException{
	    if(getObject().contains(Tag)==true ){
	    	String tagValue =  getObject().getString(Tag);
	    	String tagDayFomat = FormatDate(tagValue,valueBool,valueNoun);
	    	return tagDayFomat;
        }else 
        	return null;
     }

    
    /**
	 * Permet d'afficher l'heure d'une valeur dicom en standard international yyyy.mm.dd/ Permit display a time in metadata for yyyy.mm.dd 
	 * @param object
	 * @param Tag : valeur du tag/ value of tag
	 * @param valueBool : si true Format yyyy.mm.dd sinon format dd.mm.yyyy/ if true format yyyy.mm.dd else dd.mm.yyyy
	 * @param valueNoun :  "dot" mettre la date en format yyyy.mm.dd ou dd.mm.yyyy sinon en format yyyy mm dd ou dd mm yyyy/dot" put yyyy.mm.dd or dd.mm.dd or dd.mm.yyyy else yyyy mm or dd mm yyyy
	 * @return afficher le string du tag selon le standard international/ return string date
	 * @throws IOException
	 */
	public static String dicomDate(Attributes object , int Tag,boolean valueBool, String valueNoun) throws IOException{
		 String tagValue = object.getString(Tag);
		 String tagDayFomat = FormatDate(tagValue,valueBool,valueNoun);
		 return tagDayFomat;
	}
    
	/**
	 * Format tag
	 * @param Numero : String date
	 * @param valueBool : if true Format yyyy.mm.dd else format dd.mm.yyyy
	 * @param valueNoun :  "dot" put  the date in format yyyy.mm.dd or dd.mm.yyyy else in format yyyy mm dd or dd mm yyyy
	 * @return
	 */
	public static String FormatDate(String Numero, boolean valueBool,String valueNoun) {
		if (Numero.matches("^[0-9]*$")) {//If la chaine de caractère est un nombre ou un chiffre
			StringBuffer r = new StringBuffer();
            if (valueBool ==true){//Format yyyy.mm.dd
            	for (int i = 0, j = Numero.length(); i < j; i++) {
            		r.append(Numero.charAt(i));
            		if ((i == 3)||(i == 5) ){
                        if(valueNoun == null ? "dot" == null : valueNoun.equals("dot")){
                            r.append('.');
                        }else{
                            r.append(' ');
                        }
            		}
            	}
            	return r.toString();

            }else{
                 for (int i = 6, j =8; i<j; i++) {//jours
                     r.append(Numero.charAt(i));
                     if(i ==7 ){
                        if(valueNoun == null ? "dot" == null : valueNoun.equals("dot")){
                           r.append('.');
                        }else{
                           r.append(' ');
                        }
                     }

                  }
                  for (int i = 4, j =6; i<j; i++) {
                	  r.append(Numero.charAt(i));//The first char value of the sequence is at index zero, the next at index one, and so on, as for array indexing.
                	  if(i ==5 ){
                          if(valueNoun == null ? "dot" == null : valueNoun.equals("dot")){
                             r.append('.');
                          }else{
                             r.append(' ');
                          }
                      }
                  }
                  for (int i = 0, j =4; i<j; i++) {
                     r.append(Numero.charAt(i));//The first char value of the sequence is at index zero, the next at index one, and so on, as for array indexing.
                  }
                  return r.toString();
           }
		}
        return Numero;
	}    




 /**
  * Read value tag of VR = DA
  *
  * If use setDicomObject(readDicomObject(File f)), and getHeaderDateValue(getDicomObject())
  * @param tagNr "0000,0010"
  * @return
  */
	public Date getHeaderDateValue(String tagNr) {
	    return getHeaderDateValue(toTagInt(tagNr));
	}
/**
  * Read value tag of VR = DA
  * 
  * @param tagNr see dcm4che2
  * @return
  */
	public Date getHeaderDateValue(int tagNr) {
	    return getObject().getDate(tagNr);
    }


      /**
	 * Converts the string representation of a header number
	 * e.g. 0008,0010 to the corresponding integer as 0x00080010
	 * as used in the @see org.dcm4che2.data.Tag
	 * @param headerNr e.g. 0008,0010
	 * @return 0x00080010 as int
	 * @throws DicomHeaderParseException
	 */
	public static int toTagInt(String headerNr){
        return Integer.parseInt(headerNr.replaceAll(",", ""), 16);
    }
		
	
        /**
         * Read value tag of VR = DA
         * @param tagNr
         * @param dicomObj
         * @return
         */
        public Date getHeaderDateValue(int tagNr,Attributes dicomObj) {
	    return dicomObj.getDate(tagNr);
	}

        /**
         * Read value tag of VR = DA
         * @param tagNr :"0000,0010"
         * @param dicomObj
         * @return
         */
public Date getHeaderDateValue(String tagNr,Attributes dicomObj) {
	    return getHeaderDateValue(toTagInt(tagNr), dicomObj);
	}

/**
  * Remove string ^ in file dicom
  * @param num
  * @return
  */
 public static String texteDicom(String num) {

		num = num.replaceAll("\\^+", " ");

return num;
	}

  /**
  * Convertor tag to String
  * Using VM !=1
   * example result [25, 25]
  * @param Tag
  * @return
  */
 public static String getStringTag(Attributes object, int Tag){
	 	String tagValue2[] = object.getStrings(Tag);//Conversion table in List to String
	 	String tagValue = Arrays.asList(tagValue2).toString();
return tagValue;
 }

 /**
  * Convertor tag to String
  * Using VM !=1
  *  example result 25/25
  * @param object
  * @param Tag
  * @return
  */
 public static String getStringTag2(Attributes object, int Tag){
                 String tagValue2[] = object.getStrings(Tag);//Conversion table in List to String
                 String tagValue =DisplayTag.arrayToString(tagValue2,"\\");
 return tagValue;
 }

/**
 * Convert an array of strings to one string
 * Put the 'separator' string between each element
 * @param a
 * @param separator
 * @return
 */
public static String arrayToString(String[] a, String separator){
    StringBuffer result = new StringBuffer();
    if(a.length>0) {
        result.append(a[0]);
        for(int i=1;i<a.length;i++){
        result.append(separator);
        result.append(a[i]);
    }
  }
return result.toString();
}


/**
 * Permit display time in hh.mm.ss
 * (0008,0030) AT S Study Time
 * (0008,0031) AT S Series Time
 * (0008,0032) AT S Acquisition Time
 * (0008,0033) AT S Image Time
 * @param file : input file
 * @param Tag : giving tag
 * @return
 * @throws IOException
 */
public  String dicomTime(int Tag) throws IOException{
	           if(getObject().contains(Tag)==true ){
	  String tagValue = getObject().getString(Tag);
	  String tagValueNotDot = formatNotDot(tagValue);
	  String tagTimeFomat = FormatTimes(tagValueNotDot);
	  return tagTimeFomat;
           } else return null;
}



/**
 * Permit display time in hh.mm.ss.fac
 * (0008,0030) AT S Study Time
 * (0008,0031) AT S Series Time
 * (0008,0032) AT S Acquisition Time
 * (0008,0033) AT S Image Time
 * @param Tag : giving tag
 * @return
 * @throws IOException
 */
public String dicomTimeTotal( int Tag) throws IOException{
	    if(getObject().contains(Tag)==true ){
	  String tagValue =  getObject().getString(Tag);
	  String tagTimeFomat = FormatTimes(tagValue);
	  return tagTimeFomat;
           } else return null;
}


/**
 * Permit display time in hh.mm.ss
 * (0008,0030) AT S Study Time
 * (0008,0031) AT S Series Time
 * (0008,0032) AT S Acquisition Time
 * (0008,0033) AT S Image Time
 * @param object : Metadata
 * @param Tag : value dicom
 * @return new value String
 * @throws IOException
 */
public String dicomTime2(Attributes object, int Tag) throws IOException{
	  String tagValue =  object.getString(Tag);
	  String tagValueNotDot = formatNotDot(tagValue);
	  System.out.println(FormatTime(tagValueNotDot));
	  String tagTimeFomat = FormatTimes(tagValueNotDot);
	  return tagTimeFomat;
}


/**
 * Permit display time in hh.mm.ss.frac
 * (0008,0030) AT S Study Time
 * (0008,0031) AT S Series Time
 * (0008,0032) AT S Acquisition Time
 * (0008,0033) AT S Image Time
 * @param object : Metadata
 * @param Tag : value dicom
 * @return new value String
 * @throws IOException
 */
public String dicomTime3(Attributes object, int Tag) throws IOException{
	  String tagValue = object.getString(Tag);
	  
	  String tagTimeFomat = FormatTimes(tagValue);
	  return tagTimeFomat;
}




	/**
	 * reads a int value from the Dicomheader
	 * @param tagNr the Tag to read
	 * @return the value as int
	 */
	public int getHeaderIntegerValue(int tagNr) {
	    return getObject().getInt(tagNr,0);
	}

	/**
	 *
	 * @param headerNr e.g. "0018,0050" to get Slice Thickness<br>
	 * or "0008,0102#0054,0220" to get the Coding Scheme Designator after View Code Sequence
	 * @return int
	 * @throws DicomHeaderParseException
	 */
	public int getHeaderIntegerValue(String tagNr) {
	    return getHeaderIntegerValue(toTagInt(tagNr));
	}

        

	        


	/**
	 * checks if the Header contains the given tag
	 * @param tagNr
	 * @return
	 * @throws DicomHeaderParseException
	 */
	public boolean containsHeaderTag(String tagNr) {
	    return containsHeaderTag(toTagInt(tagNr));
	}

	/**
	 * checks if the Header contains the given tag
	 * @param tagNr
	 * @return
	 */
	public boolean containsHeaderTag(int tagNr) {
	    return getObject().contains(tagNr);
	}

        /**
	 * returns the name of the given Tag
	 * @param tagNr
	 * @return
	 */
	public static String getHeaderName(int tagNr) {
//	    return ElementDictionary.getDictionary().nameOf(tagNr);
	    return dict.keywordOf(tagNr);
	}
        
        
        /**
	 * returns the name of the given Header field
	 * @param tagNr
	 * @return the name of the Field e.g. Patients Name
	 */
	public String getHeaderName(String tagNr) {
		try {
			return getHeaderName(toTagInt(tagNr));
		} catch (Exception e) {
		
			return "";
		}
	}

       /**
	 * returns the String representation of the given header field
	 * if it exists in the header
	 * @param tagNr
	 * @return
	 */
	public String getHeader(int tagNr) {
	    try {
	    	String  dcmele = getObject().getString(tagNr);
	    	return toElementString(dcmele, tagNr);
	    } catch (Exception e) {
	
		return "";
	    }
	}
        
       private static  String toElementString(String dcmele,int tag) {
	    StringBuffer sb = new StringBuffer();
            
            int TAG[] = getObject().tags(); 
        StringBuffer append = sb.append(TAG)
                .append(" [").append(getObject().getVR(tag)).append("] ")
                .append(object.tags()).append(": ")
                .append(dcmele);
	    return sb.toString();
	}

       
        /**
	 * checks wether the header is empty or not
	 * @return
	 */
	public boolean isEmpty() {
	    if (getObject() == null || getObject().isEmpty()) {
			return true;
	    }
		return false;
	}

/**
	 * Converts the string representation of a header number
	 * e.g. 0008,0010 to the corresponding integer as 0x00080010
	 * as used in the @see org.dcm4che2.data.Tag
	 * @param headerNr e.g. 0008,0010
	 * @return 0x00080010 as int
	 * @throws DicomHeaderParseException
	 */
	public static int toTagInt2(String headerNr){
		return Integer.parseInt(headerNr.replaceAll(",", ""), 16);
	}



/**
 * Removing comma in String
 * @param num
 * @return
 */
public static String formatNotDot(String num) {
	num = num.trim().replaceAll("[^0-9\\+]", "");
	if (num.matches("^0*$"))
		num = "";
	return num;
}

/**
 * Format 
 * hh.mm.ss
 * @param Numero
 * @return
 */
 public static String FormatTime(String Numero) {

	if (Numero.matches("^[0-9]*$")) {
		StringBuilder r = new StringBuilder();
		for (int i = 0, j = 6; i < j; i++) {
			r.append(Numero.charAt(i));
			if ((i % 2 == 1) && (i < (j - 1)))
				r.append(':');
		}
		return r.toString();
	}
	return Numero;
}

 /**
 * Format
 * hh.mm.ss.frac
 * @param Numero
 * @return
 */
 public static String FormatTimes(String Numero) {

	if (Numero.matches("^[0-9].*$")) {
		StringBuilder r = new StringBuilder();
		for (int i = 0,j=Numero.length();i<j; i++) {
			r.append(Numero.charAt(i));
			if ((i % 2 == 1)&(i<5))
				r.append(':');
		}
		return r.toString();
	}
	return Numero;
}

 
 
        
          /**
	 	 * Round  double after dot
	 	 * @param a : value convertor
	 	 * @param n number of decade 
	 	 * @return new value
	 	 */
	  public double floor(double a, int n){
	  		double p =Math.pow(10.0,n);
	  	return Math.floor((a*p)+0.5)/p;
	  	}

        
                /**
	  	 * Giving power
                 * Example:
                 *          setFactorPower(10,2)//10^2
	  	 * @param result3
	  	 * @param factor
	  	 * @return
	  	 * @return
	  	 */
	  	public static double setFactorPower(double result3, double factor){
	  	 	   return resultFactorDix= Math.pow(result3, factor);
  		 	}
	  	
                /**
	  	 * Giving  getFactorPower
	  	 */
	  	public static double getFactorPower(){
	  		return resultFactorDix;
	  	}

                /**
                 *  Giving pixelData
                 * @param dcmObj
                 * @return 
                 */
                public static int[] lattricePixelData(Attributes dcmObj){
                    int[] data = dcmObj.getInts(Tag.PixelData);
                    return data;
                }

                /**
                 * Giving pixel data
                 * @param fileInput
                 * @return
                 * @throws IOException 
                 */
               public int[] lattricePixelData2() throws IOException{
                 
                    int[] data = getObject().getInts(Tag.PixelData);
                    return data;
                 }

               /**
                * Giving pixel data
                * @param dcmObj
                * @return
                * @throws IOException 
                */
               public byte[] lattricePixelDataBytes(Attributes dcmObj) throws IOException{
                    byte[] data = dcmObj.getBytes(Tag.PixelData);
                    return data;
                }
               
               /**
                * Giving pixel data
                * @param fileInput
                * @return
                * @throws IOException 
                */
                public byte[] lattricePixelDataBytes2() throws IOException{
                   
                    byte[] data = getObject().getBytes(Tag.PixelData);
                    return data;
                }

                     

        /**
         * Extraction PixelData 
         * @param raster of dicom
         * @return
         */
      private int[][] extractData(Raster raster) {
       // int w = image.getWidth();
       // int h = image.getHeight();
        int w = raster.getWidth();
        int h = raster.getHeight();

        System.out.printf("w = %d  h = %d%n", w, h);
        //WritableRaster raster = (WritableRaster) getMyImage();
        int[][] data = new int[h][w];
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                data[y][x] =raster.getSample(x, y, 0);
            }
        }
        return data;
    }

       /**
         * Extraction PixelData
         * @param raster
         * @return
         */
  private int[] getPixelData(int[][] data2){
          int h = data2.length;
        int w = data2[0].length;
      int[] array = new int[h*w];
       for(int y = 0; y < h; y++) {
              for(int x = 0; x < w; x++) {
                   int index = y*w + x;
                   array[index] = data2[y][x];//ligne
            }
            }

return array;
}
        
  /**
 * Return value table input 
 * @param object
 * @param PATIENT_ADDITIONAL_TAGS : Table int 
 *
 * example :
 *  public static final  int[] tag = {
    		0x00080020,
                0x00080022,
        };
 *
 *FileInputStream fis = new FileInputStream(fileInput);
 *DicomInputStream dis = new DicomInputStream(fis);
 *DicomObject obj = dis.readDicomObject();
 *String nounValue[] =getValue(obj,tag);
 *
 * @return
 */
private static String[] getValue(Attributes object, int[]  PATIENT_ADDITIONAL_TAGS){
    String [] value = new String [PATIENT_ADDITIONAL_TAGS.length];
    int i =0;
    while (i<PATIENT_ADDITIONAL_TAGS.length){
	    for (int tag : PATIENT_ADDITIONAL_TAGS) {

                 value[i]=object.getString(tag);
                i++;
	    }

//System.out.print(value[0]+"\n");
//System.out.print(value[1]);
	}
     return value;
    }
    

/**
 * Reading VR = SQ 
 * 
 * @param inputFile : File
 * @param tag : VR =SQ
 * @return 
 */
public String[] readItem (File inputFile, int tag){
    
  
    DisplayTag dcm = new DisplayTag(inputFile); 
                
                
               Sequence seq= dcm.getObject().getSequence(tag); 
                String valueString[] = new String[seq.size()];
          
              for (int i = 0; i<seq.size(); i++){
             Attributes attr =  seq.get(i); 
             valueString[i] = attr.toString(); 
             
             
              }
              
             
             return  valueString; 
               
}

/**
 * Value inside VR = SQ
 * @param inputFile : input File
 * @param tagSQ : tag VR = SQ
 * @param tag : Tag inside VR= SQ
 * @return 
 */
public String tagItem(File inputFile, int tagSQ, int tag){
    String valueString = null; 
     
    DisplayTag dcm = new DisplayTag(inputFile); 
                
                
               Sequence seq= dcm.getObject().getSequence(tagSQ); 
                
          
            
                       Attributes attr =  seq.get(0); 
             valueString = attr.getString(tag); 
                                        
              return valueString; 
    
    
}

                 /**
	  	 * Les unités spécifiques selon les tags pour vr= SQ/ Unity specical for tags VR= SQ
	  	 * @param TAG : 
                 *      - RegionSpatialFormat
                 *      - RegionDataType
                 *      - PhysicalUnitsXDirection
                 *      - PhysicalUnitsXDirection
                 *      - PixelComponentPhysicalUnits
                 * 
                 * 
                 * 
	  	 * @param result : value string
	  	 */

	  	public void unit(int TAG, String result){

	  		if (TAG == Tag.RegionSpatialFormat ){
	  			val2= Integer.valueOf(result).intValue();//convertie en int

	  			switch(val2)
	  			{
	  			case 5:
	  				setNounUnit("Graphics");
	  			break;
	  			case 4:
	  				setNounUnit("Wave form(physiological traces, doppler traces,...");
	  			break;
	  			case 3:
	  				setNounUnit("Spectral(CW or PW Doppler");
	  			break;
	  			case 2:
	  				this.setNounUnit("M-Mode(tissue or flow)");
	  			break;
	  			case 1:
	  				this.setNounUnit("2D(tissue or flow");
	  			break;
	  			case 0:
	  				setNounUnit("None or not applicable");
	  			break;

	  			default:
	  				break;
	  			}

	  		}else if (TAG == Tag.RegionDataType ){
	  			val2= Integer.valueOf(result).intValue();//convertie en int

	  			switch(val2)
	  			{
	  			case 12:
	  				setNounUnit("Orther Physiological(Amplitude vs. Time)");
	  			break;
	  			case 11:
	  				setNounUnit("d(area)/dt");
	  			break;
	  			case 10:
	  				setNounUnit("Area Trace");
	  			break;
	  			case 9:
	  				setNounUnit("d(Volume)/dt Trace");
	  			break;
	  			case 8:
	  				setNounUnit("Volume Trace");
	  			break;
	  			case 7:
	  				setNounUnit("Doppler Max Trace");
	  			break;
	  			case 6:
	  				this.setNounUnit("Doppler Mode Trace");
	  			break;
	  			case 5:
	  				this.setNounUnit("Doppler Mean Trace");
	  			break;
	  			case 4:
	  				setNounUnit("CW Spectral Doppler");
	  			break;
	  			case 3:
	  				this.setNounUnit("PW Spectral Doppler");
	  			break;
	  			case 2:
	  				this.setNounUnit("Color Flow");
	  			break;
	  			case 1:
	  				this.setNounUnit("Tissue");
	  			break;
	  			case 0:
	  				this.setNounUnit("None or not applicable");
	  			break;
	  			default:
	  				break;
	  			}
                              switch (result) {
                                  case "A":
                                      this.setNounUnit("ECG Trace");
                                      break;
                                  case "B":
                                      this.setNounUnit("Pulse Trace");
                                      break;
                                  case "C":
                                      this.setNounUnit("Phonocardiogram Trace");
                                      break;
                                  case "D":
                                      this.setNounUnit("Gray bar");
                                      break;
                                  case "E":
                                      this.setNounUnit("Color bar");
                                      break;
                                  case "F":
                                      this.setNounUnit("Integrated Backscatter");
                                      break;
                                  default:
                                      return;
                              }

	  			}else
	  				if (TAG == Tag.PhysicalUnitsXDirection || TAG == Tag.PhysicalUnitsXDirection || TAG == Tag.PixelComponentPhysicalUnits){
	  			val2= Integer.valueOf(result).intValue();//convertie en int

	  			switch(val2)
	  			{
	  			case 9:
	  				setNounUnit("cm*cm.pixel/sec");
	  			break;
	  			case 8:
	  				setNounUnit("cm*cm/pixel");
	  			break;
	  			case 7:
	  				setNounUnit("cm*pixel/sec");
	  			break;
	  			case 6:
	  				this.setNounUnit("dB*pixel/seconds");
	  			break;
	  			case 5:
	  				this.setNounUnit("hertz/pixel");
	  			break;
	  			case 4:
	  				setNounUnit("seconds/pixel");
	  			break;
	  			case 3:
	  				this.setNounUnit("cm/pixel");
	  			break;
	  			case 2:
	  				this.setNounUnit("dB/pixel");
	  			break;
	  			case 1:
	  				this.setNounUnit("percent/pixel");
	  			break;
	  			case 0:
	  				this.setNounUnit("None or not applicable");
	  			break;
	  			default:
	  				break;
	  			}
                              switch (result) {
                                  case "A":
                                      this.setNounUnit("cm*cm*cm/pixel");
                                      break;
                                  case "B":
                                      this.setNounUnit("cm*cm*cm*pixel/sec");
                                      break;
                                      
                                  case "C":
                                      
                        		this.setNounUnit("degrees");
                                      break; 
	  			}

	  		}else if (TAG == Tag.PixelComponentDataType  ){
	  			val2= Integer.valueOf(result).intValue();//convertie en int

	  			switch(val2)
	  			{
	  			case 9:
	  				setNounUnit("Computed Border");
	  			break;
	  			case 8:
	  				setNounUnit("Integrated Backscatter");
	  			break;
	  			case 7:
	  				setNounUnit("Color bar");
	  			break;
	  			case 6:
	  				this.setNounUnit("Gray bar");
	  			break;
	  			case 5:
	  				this.setNounUnit("Color Flow Intensity");
	  			break;
	  			case 4:
	  				setNounUnit("Color Flow Variance");
	  			break;
	  			case 3:
	  				this.setNounUnit("Color Flow Velocity");
	  			break;
	  			case 2:
	  				this.setNounUnit("Spectral doppler");
	  			break;
	  			case 1:
	  				this.setNounUnit("Tissue");
	  			break;
	  			case 0:
	  				this.setNounUnit("None or not applicable");
	  			break;
	  			default:
	  				break;
	  			}

	  			if("A".equals(result)){
	  				this.setNounUnit("Tissue Classification");
	  			 }
	  		}
	  		else this.setNounUnit("None or not applicable");


	   	}

                /**
	  	 * Enregistre l'unité des items/ Put unity of items
	  	 * @param nounUnit
	  	 * @return this.nounUnit = nounUnit
	  	 */
	  	public String setNounUnit(String nounUnit){
	  		return this.nounUnit = nounUnit;

	  	}

	  	/**
	  	 * On obtient l'unité des items./Giving unity of items
	  	 * @return le nom de l'unité
	  	 */
	  	public String getNounUnit(){
	  		return nounUnit;
	  	}
                
                
                	  	/**
	  	 * Special Ratio Spatial toutes les unites sont en mm/ Giving tag ratio Spatial of mm
	  	 * @param TAG : entree choisi
                 *         - PhysicalUnitsXDirection
                 *         - PhysicalUnitsYDirection 
                 *         -PixelComponentPhysicalUnits
                 * 
	  	 * @param result: prend l'unite 
	  	 */
	  	public void unitRatioSpatial(int TAG, String result){
			if (TAG == Tag.PhysicalUnitsXDirection || TAG == Tag.PhysicalUnitsYDirection || TAG == Tag.PixelComponentPhysicalUnits){
	  			val2= Integer.valueOf(result).intValue();//convertie en int 
	  			
	  			switch(val2)
	  			{
	  			case 9:
	  				Double valueSpatial1 = getValeurTagItemDoubleRatio()* setFacteurPuissance(10,1);
	  			  	setTagItemDoubleRatio(valueSpatial1);//prend la valeur
	  				setNounUnitRatio("mm*mm.pixel/sec");
	  			break;
	  			case 8:
	  				Double valueSpatial2 = getValeurTagItemDoubleRatio()* setFacteurPuissance(10,1);
		    	    setTagItemDoubleRatio(valueSpatial2);//prend la valeur
	  				setNounUnitRatio("mm*mm/pixel");
	  			break;
	  			case 7:
	  				setNounUnitRatio("mm*pixel/sec");
	  			break;
	  			case 6:
	  				this.setNounUnitRatio("dB*pixel/seconds");
	  			break;
	  			case 5:
	  				this.setNounUnitRatio("hertz/pixel");
	  			break;
	  			case 4:
	  				setNounUnitRatio("seconds/pixel");
	  			break; 	
	  			case 3:
	  				this.setNounUnitRatio("mm/pixel");
	  			break;	
	  			case 2:
	  				this.setNounUnitRatio("dB/pixel");
	  			break;
	  			case 1:
	  				this.setNounUnitRatio("percent/pixel");
	  			break;
	  			case 0:
	  				this.setNounUnitRatio("None or not applicable");
	  			break;
	  			default:
	  				break;
	  			}
                            switch (result) {
                                case "A":
                                    Double valueSpatial3 = getValeurTagItemDoubleRatio()* setFacteurPuissance(10,2);
                                    setTagItemDoubleRatio(valueSpatial3);//prend la valeur
                                    this.setNounUnitRatio("mm*mm*mm/pixel");
                                    break;
                                case "B":
                                    Double valueSpatial4 = getValeurTagItemDoubleRatio()* setFacteurPuissance(10,2);
                                    setTagItemDoubleRatio(valueSpatial4);//prend la valeur
                                    this.setNounUnit("mm*mm*mm*pixel/sec");
                                    break;
                                    
                                case "C": 
                                    this.setNounUnit("degrees");
                                    break; 
                                    
                            }
                          
			}  		
	  	}
                
                
                    	/**
	  	 * Prend la valeur d'un Ratio Spatial/Put value Ratio Spatial
	  	 * @param valueSpatial
	  	 * @return
	  	 */
	  	public Double setTagItemDoubleRatio(double valueSpatial){
	  		return this.valueSpatial = valueSpatial;
	  	}

	  	/**
	  	 * Donne la valeur du Ratio/Diving value ratio Spatial 
	  	 * @return
	  	 */
	public Double getValeurTagItemDoubleRatio(){

	  		return valueSpatial;
	  	}
        
        
       /**
	  	 * Donne les valeurs calculer des puissances/ Put and computing power 
	  	 * @param result3
	  	 * @param facteur
	  	 * @return
	  	 * @return
	  	 */
	  	public static double setFacteurPuissance(double result3, double facteur){
	  	 	   return resultFacteurDix	= Math.pow(result3, facteur);
  		 	}

	  	/**
	  	 * Obtient la valeur de puissance/ Giving value power
	  	 * @return
	  	 */
	  	public static double getFacteurPuissance(){
	  		return resultFacteurDix;
	  	}
                
                /**
	  	 * Enregistre l'unite des items /Put unity unity items 
	  	 * @param nounUnit
	  	 * @return this.nounUnit = nounUnit
	  	 */
	  	public String setNounUnitRatio(String nounUnitRatio){
	  		return this.nounUnitRatio = nounUnitRatio;

	  	}

	  	/**
	  	 * On obtient l'unite des items./Giving unity items
	  	 * @return le nom de l'unité
	  	 */
	  	public String getNounUnitRatio(){
	  		return nounUnitRatio;
	  	}
                
                /**
	  	 * Prend la valeur interne d'un tag Item/ Put tag Item
	  	 * @param result
	  	 * @return
	  	 */
	  	public String setTagItem(String result){

	  		return this.result = result;
	  	}

	  	/**
	  	 * Donne la valeur du tag rechercher/Giving a value of tag seek 
	  	 * @return le String de la valeur rechercher du tag dans un item
	  	 */
	 	public String getValeurTagItem(){
	  		return result;
	  	}

	  	/**
	  	 * Prend la valeur interne d'un tag Item/ Put the value tag iteù
	  	 * @param result
	  	 * @return
	  	 */
	  	public Double setTagItemDouble(double result2){

		  		return this.result2 = result2;
		  	}

		/**
	  	 * Donne la valeur du tag rechercher/Giving the value Tag 
	  	 * @return le Double de la valeur rechercher du tag dans un item
	  	 */

	  	public Double getValeurTagItemDouble(){

	  		return result2;
	  	}

  /**
	 * reads a String value from tag dicom (dcm4che2)
	 * @param tagNr the Tag to read
	 * @return the value as String
         *  Returns the Specific Character Set defined by Attribute Specific Character Set (0008,0005)
         * of this or the root Data Set, if this is a Nested Data Set containing in a Sequence Eleme
	 */
	public String getHeaderStringValue(int tagNr) {
	    try {
		/*Attributes elem  = getObject(); 
                elem.setSpecificCharacterSet("ISO_IR 100"); */
	    	System.out.println(111);
	    	Attributes elem  = getObject(); 
            elem.setSpecificCharacterSet("GB18030"); 
		String val = elem.getString(tagNr);
		if (val == null) val = "";
		return val;
	    } catch (Exception e) {
		return "";
	    }
	}
        
        
        
        
        
        /**
	 * reads a String value from tag dicom (dcm4che2)
	 * @param tagNr the Tag to read
	 * @return the value as String
         *  Returns the Specific Character Set defined by Attribute Specific Character Set (0008,0005)
         * of this or the root Data Set, if this is a Nested Data Set containing in a Sequence Eleme
	 */
	public String[] getHeaderStringValues(int tagNr) {
	    try {
		/*Attributes elem  = getObject(); 
                elem.setSpecificCharacterSet("ISO_IR 100");*/ 
           System.out.println(222);
                Attributes elem  = getObject(); 
                elem.setSpecificCharacterSet("GB18030"); 
		String[] val = elem.getStrings(tagNr); 
		return val;
	    } catch (Exception e) {
		return null;
	    }
	}
        
        
	/**
	 * reads a String value from the Dicomheader
	 * @param tagNr the Tag to read
	 * @param dcmelement
	 * @return the value as String
	 */
	public String getHeaderStringValue(Attributes dcmelement, int tagNr) {
	    try {
                System.out.println(333);
                /* dcmelement.setSpecificCharacterSet("ISO_IR 100"); */
	    	 dcmelement.setSpecificCharacterSet("GB18030"); 
		String val = dcmelement.getString(tagNr);
		if (val == null) val = "";
		return val;
	    } catch (Exception e) {
		return "";
	    }
	}

	/**
	 *reads the tag (group,element)
	 * @param headerNr e.g. "0018,0050" to get Slice Thickness<br>
	 * @return String
	 * @throws DicomHeaderParseException
	 */
	public String getHeaderStringValue(String headerNr) {
	    headerNr = headerNr.replaceAll("xx", "00").replaceAll("XX", "00");
	    return getHeaderStringValue(toTagInt(headerNr));
	}
        
        
          /**
         * Giving time a tag ("xxxx,")
         * @param tagNr
         * @return
         */
	public Time getHeaderTimeValue(String tagNr) {
	    return getHeaderTimeValue(toTagInt(tagNr));
	}

        /**
         * Giving time a tag
         * @param tagNr
         * @return time
         */
	public Time getHeaderTimeValue(int tagNr) {
	    String time = getHeaderStringValue(tagNr);
	    if (time.length() != 6)
		return null;

	    try {
		int hour = Integer.parseInt(time.substring(0,2));
		int min = Integer.parseInt(time.substring(2,4));
		int sec = Integer.parseInt(time.substring(4,6));
		return new Time(hour,min,sec);
	    } catch (Exception e) {
	
	    }
	    return null;
	}


	/**
	 * retrieves a specific HeaderTag that is inside anotehr tag
	 * or "0008,0102, 0054,0220" to get the Coding Scheme Designator after View Code Sequence
	 * @return String
	 * 	 * @param tagHierarchy; e.g. {Tag.UID, Tag.SOPInstanceUID, Tag.CodeMeaning}
	 * @return
	 */
	public String getHeaderValueInsideTag(int[] tagHierarchy) {

		try {
		
			for (int i = 0; i < tagHierarchy.length-1; i++) {
				return getObject().getString(tagHierarchy[i]);
			}
	
		 
		} catch (Exception e) {
			String tags = "";
			for (int i = 0; i < tagHierarchy.length; i++) {
				tags += toTagString(tagHierarchy[i]) + " ";
			}
		    
		    return "";
	    }
        return null;
	}
 
 
 
 
 
        /**
	 * converts the int representation of a header number
	 * e.g. 0x00080010 to the corresponding String 0008,0010
	 * @param headerNr e.g. 0x00080010 as int
	 * @return 0008,0010 as String
	 */
	public static String toTagString(int tagNr) {
            
                       
	    return shortToHex(tagNr >> 16) +
		',' + shortToHex(tagNr);
	}

         public static String shortToHex(int val) {
        char[] ch = new char[4];
        shortToHex(val, ch, 0);
		return new String(ch);
	}
        
 
        
        
        public static StringBuffer shortToHex(int val, StringBuffer sb) {
		sb.append(HEX_DIGITS[(val >> 12) & 0xf]);
		sb.append(HEX_DIGITS[(val >> 8) & 0xf]);
		sb.append(HEX_DIGITS[(val >> 4) & 0xf]);
		sb.append(HEX_DIGITS[val & 0xf]);
		return sb;
	}

         public static void shortToHex(int val, char[] ch, int off) {
        ch[off] = HEX_DIGITS[(val >> 12) & 0xf];
        ch[off+1] = HEX_DIGITS[(val >> 8) & 0xf];
        ch[off+2] = HEX_DIGITS[(val >> 4) & 0xf];
        ch[off+3] = HEX_DIGITS[val & 0xf];
    }
        
         
         
         
         
         
         
         
         
         
         
         
         
         
         
         
         
         
         
    // Put metadata

/**
 *  Create file output dicom 
 * @param fileOutput : file output
 * @throws IOException 
 * 
 * 
 * Example: 
 *
 * 
 * 
 * 
 */
public void writeTo(File fileOutput, Attributes fmi, Attributes object) throws IOException {
                   
            
              
        DicomOutputStream dos = new DicomOutputStream( new File (fileOutput +".dcm")); 
        dos.setEncodingOptions(encOpts);
        dos.writeDataset(fmi, object);
        dos.finish();
        dos.flush();
    


    }

/**
 * Writting 
 * @param fileOutput
 * @param h
 * @param w
 * @throws IOException 
 */
  public void writeToSegment(File fileOutput, int h, int w) throws IOException{
        DicomOutputStream dos = new DicomOutputStream( new File (fileOutput +".dcm")); 
        dos.setEncodingOptions(encOpts);
     //  Fragments fragements = new Fragments(VR.OB, false,h*w);  
      //  fragements.writeTo(dos, VR.OB);
       
   }

    
    
  
  /**
   * Create overlay in pixelData
   * @param object 
   */
  public void overlayCreate(Attributes object){
  int position = object.getInt( Tag.OverlayBitPosition, 0 );
if( position == 0 ) return;

// Remove the overlay data in high-bit specified.
//
int bit = 1 << position;
int[] pixels = object.getInts( Tag.PixelData );
int count = 0;
for( int pix : pixels )
{
   int overlay = pix & bit;
   pixels[ count++ ] = pix - overlay;
}
object.setInt( Tag.PixelData, VR.OW, pixels );
    
  }
  
  /**
   *  dicom.setString(Tag.PerformingPhysicianName, VR.PN, "Jean");
             dicom.setString(Tag.AdmittingDiagnosesDescription, VR.LO, "CHU");
                Sequence seq= dicom.newSequence(Tag.AnatomicRegionSequence,0);
                    Attributes dicom2 = new Attributes(); 
   * @param dicom 
   */
  
  public void setItem(Attributes dicom, int TagSequenceName){
      
                Sequence seq= dicom.newSequence(TagSequenceName,0);
                   
                    dicom.setString(Tag.CodingSchemeDesignator, VR.SH, "SRT");
                    dicom.setString(Tag.CodeValue, VR.SH, "T-AA000");
                    dicom.setString(Tag.CodeMeaning, VR.LO, "Eye");             
                seq.add(dicom);
             
  }
  
  
}
