package com.dryork.vision.base.util;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;


public class FreemarkerUtil {

    @SuppressWarnings("deprecation")
	public static Template getTemplate(String name) {
        try {
            Configuration cfg = new Configuration();
            cfg.setClassForTemplateLoading(FreemarkerUtil.class, "/templates");
            cfg.setObjectWrapper(new DefaultObjectWrapper());  
            cfg.setDefaultEncoding("UTF-8");   
            Template temp = cfg.getTemplate(name);
//            temp.setEncoding("UTF-8");  
            return temp;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 
     * @param name
     * @param root
     */
    @SuppressWarnings("static-access")
	public void print(String name, Map<String, Object> root) {
        try {
            Template temp = this.getTemplate(name);
            temp.process(root, new PrintWriter(System.out));
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param name
     * @param root
     * @param outFile
     */
    public static void fprint(String name, Map<String, Object> root, String outFile) {
        Writer out = null;
        try {
        	
        	File file = new File("D:/templates" +"/"+ outFile);
            out =  new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));  
            Template temp = getTemplate(name);
            temp.process(root, out);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    

    /**
     * 获取freemarker的配置，freemarker本身支持classpath,目录或从ServletContext获取.
     * @Title: getFreeMarkerCFG
     * @param templateFilePath  获取模板路径  
     * @return 返回freemaker的配置属性  
     * @throws Exception 
     * @return Configuration
     * @author yaoxiaolin
     * @date 2016年6月14日 下午4:20:24
     */
    @SuppressWarnings("deprecation")
	private static Configuration getFreeMarkerCFG(String templateFilePath) throws Exception {
    	Configuration config = new Configuration();
    	config.setDefaultEncoding("UTF-8"); 
            try {    
               config.setDirectoryForTemplateLoading(new File(templateFilePath));    
            } catch (Exception ex) {    
                throw ex;    
            }    

        return config;    
    }  
    
    /**
     * 模板生成静态html
     * @Title: createHtmlFile 
     * @param templateFileName 模板文件名
     * @param templateFilePath 指定模板目录
     * @param htmlFilePath 指定生成静态html的目录
     * @param htmlFileName 生成的静态文件名
     * @param contextMap 生成静态页面中的数据
     * @return 
     * @return byte[]
     * @author yaoxiaolin
     * @date 2016年6月14日 下午4:23:19
     */
    public static byte[] createHtmlFile(String templateFileName, Map<String, Object> contextMap) {    
    	  Writer out = null;
    	  ByteArrayOutputStream outByte = new ByteArrayOutputStream();
        try {    
            Template t = getFreeMarkerCFG(FreemarkerUtil.class.getResource("/templates").getFile().toString()).getTemplate(templateFileName);        
            out = new BufferedWriter(new OutputStreamWriter(outByte, "UTF-8"));    
            t.process(contextMap, out); 
            out.flush(); 
            byte[] bs = outByte.toByteArray();
            return bs;
            
        } catch (TemplateException e) {    
        	e.printStackTrace();
        } catch (IOException e) {    
        	e.printStackTrace();
        } catch (Exception e) {    
        	e.printStackTrace();
        } finally {
    		try {
    			if(out!=null) {
    				out.close();
    			}
    			if(outByte!=null) {
    				outByte.close();
    			}
			} catch (IOException e) {
				e.printStackTrace();
			}   
        	
		} 
        return null;
    }

    public static void main(String[] args) {
    	String rootPath= FreemarkerUtil.class.getResource("/templates").getFile().toString(); 
    	System.out.println(rootPath);
	}
}