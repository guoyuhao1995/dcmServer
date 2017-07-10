package com.dryork.vision.base.aliyunoss;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.dryork.vision.base.util.DateUtils;
import com.dryork.vision.base.util.Global;
import com.dryork.vision.base.util.ToolUtil;

@Component
public class AliyunossUtil {

	private static String accessKeyId = Global.ACCESS_KEYID;
	private static String accessKeySecret = Global.ACCESS_KEYSECRET;

	private static final String KEYFROMAT = "yunstore/%s/%s/%s";
	private static final String SINGERROMAT = "yunstore/%s/%s";

	private static OSSClient client;

	static {
		client = new OSSClient(Global.ENDPOINT, accessKeyId, accessKeySecret);
	}

	public static byte[] toByteArray(File file) throws IOException {
		byte[] buffer = null;
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int n;
			while ((n = fileInputStream.read(b)) != -1) {
				byteArrayOutputStream.write(b, 0, n);
			}
			fileInputStream.close();
			byteArrayOutputStream.close();
			buffer = byteArrayOutputStream.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}

	public static void putObject(String endpoint, String bucketName, String key, File file) throws IOException {
		InputStream content = new FileInputStream(file);

		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentLength(content.available());
		objectMetadata.setCacheControl("no-cache");
		objectMetadata.setHeader("Pragma", "no-cache");
		String contentType = contentType(file.getName().substring(file.getName().lastIndexOf(".") + 1));
		objectMetadata.setContentType(contentType);
		objectMetadata.setContentDisposition("inline;filename=" + file.getName());
		client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		client.putObject(bucketName, key, content, objectMetadata);
	}

	/**
	 * @Title: putObject
	 * @param bucketName存储桶
	 * @author 武佳兴
	 * @date 2016年5月11日
	 */
	public static String putObject(String bucketName, String filePath, MultipartFile file) throws IOException {
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentLength(file.getInputStream().available());
		objectMetadata.setCacheControl("no-cache");
		objectMetadata.setHeader("Pragma", "no-cache");

		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);

		String contentType = contentType(suffix);
		objectMetadata.setContentType(contentType);

		objectMetadata.setContentDisposition("inline;filename=" + file.getOriginalFilename());

		String newkey = String.format("%s/%s/%s", filePath, DateUtils.getDateStr("yyyyMMdd"), System.currentTimeMillis() + ToolUtil.getRandom(3) + "." + suffix);

		client.putObject(bucketName, newkey, file.getInputStream(), objectMetadata);

		return newkey;
	}

	/**
	 * 
	 * @Title: uploadHtml
	 * @param bucketName
	 * @param filePath
	 * @param bs 要上传的字节
	 * @param suffix 生成文件的后缀
	 * @return
	 * @throws IOException
	 * @return String
	 * @author yaoxiaolin
	 * @date 2016年6月14日 下午6:30:51
	 */
	/**
	 * 
	 * @Title: uploadHtml
	 * @param bucketName
	 * @param origin 上传的目录
	 * @param bs 要上传的字节
	 * @param suffix 生成文件的后缀
	 * @throws IOException
	 * @return String
	 * @author yaoxiaolin
	 * @date 2016年6月14日 下午6:39:49
	 */
	public static String uploadHtml(String bucketName, OriginType origin, byte[] bs, String suffix) throws IOException {
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentLength(bs.length);
		objectMetadata.setCacheControl("no-cache");
		objectMetadata.setHeader("Pragma", "no-cache");

		String contentType = contentType(suffix);
		objectMetadata.setContentType(contentType);

		String key = String.format(KEYFROMAT, origin.getValue(), DateUtils.getDateStr("yyyyMMdd"), System.currentTimeMillis() + ToolUtil.getRandom(3) + "." + suffix);

		client.putObject(bucketName, key, new ByteArrayInputStream(bs), objectMetadata);

		return key;
	}

	public static void deleteObject(String key) {
		client.deleteObject(Global.BUCKETNAME, key);
	}

	public static Map<String, String> getImgMetaData(byte[] imgbytes) throws IOException {
		BufferedImage sourceImg = ImageIO.read(new ByteArrayInputStream(imgbytes));
		Map<String, String> map = new HashMap<String, String>();
		map.put("width", String.valueOf(sourceImg.getWidth(null)));
		map.put("height", String.valueOf(sourceImg.getHeight(null)));
		return map;
	}

	public static String upload(byte[] data, OriginType origin, FileType ftype, String suffix) {
		try {
			ObjectMetadata objMetadata = new ObjectMetadata();
			objMetadata.setContentType(ftype.getValue());
			objMetadata.setContentLength(data.length);
			if (ftype == FileType.IMAGE) {
				objMetadata.setUserMetadata(getImgMetaData(data));
			}
			String key = String.format(KEYFROMAT, origin.getValue(), DateUtils.getDateStr("yyyyMMdd"), System.currentTimeMillis() + ToolUtil.getRandom(3) + "." + suffix);
			client.putObject(Global.BUCKETNAME, key, new ByteArrayInputStream(data), objMetadata);
			return key;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 上传歌曲封面或歌星图片文件
	 * 
	 * @param data
	 * @param origin
	 * @param ftype
	 * @param suffix
	 * @param uuid
	 * @return
	 */
	public static String upload(byte[] data, OriginType origin, FileType ftype, String suffix, String uuid) {
		try {
			ObjectMetadata objMetadata = new ObjectMetadata();
			objMetadata.setContentType(ftype.getValue());
			objMetadata.setContentLength(data.length);
			if (ftype == FileType.IMAGE || ftype == FileType.PNG) {
				objMetadata.setUserMetadata(getImgMetaData(data));
			}
			String key = "";
			if (origin.getValue().equals("singer")) {// 上传歌星头像
				key = String.format(SINGERROMAT, origin.getValue(), uuid + "." + suffix);
			} else if (origin.getValue().equals("songpic")) {// 上传歌曲封面
				key = String.format(KEYFROMAT, origin.getValue(), uuid.charAt(0), uuid + "." + suffix);
			} else {
				key = String.format(KEYFROMAT, origin.getValue(), DateUtils.getDateStr("yyyyMMdd"), uuid + "." + suffix);
			}
			client.putObject("putao-res", key, new ByteArrayInputStream(data), objMetadata);
			return key;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public enum OriginType {
		KTV("ktv"), ZIP("log"), IM("im"), HEAD("head"), SHOW("show"), BEST("best"), SINGER("singer"), GAME("game"), MUSIC("music"), SUBJECT("subject"), SUBJECTIMG("data"), PAR("par"), BANNER("banner"), CARD("card");
		private final String value;

		private OriginType(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	public enum FileType {
		ZIP("application/octet-stream"), PNG("image/png"), IMAGE("image/jpeg"), VIDEO("application/octet-stream"), AUDIO("application/octet-stream");
		private final String value;

		private FileType(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	public static String contentType(String FilenameExtension) {
		if (FilenameExtension.equals("BMP") || FilenameExtension.equals("bmp")) {
			return "image/bmp";
		}
		if (FilenameExtension.equals("GIF") || FilenameExtension.equals("gif")) {
			return "image/gif";
		}
		if (FilenameExtension.equals("JPEG") || FilenameExtension.equals("jpeg") || FilenameExtension.equals("JPG") || FilenameExtension.equals("jpg") || FilenameExtension.equals("PNG") || FilenameExtension.equals("png")) {
			return "image/jpeg";
		}
		if (FilenameExtension.equals("HTML") || FilenameExtension.equals("html")) {
			return "text/html";
		}
		if (FilenameExtension.equals("TXT") || FilenameExtension.equals("txt")) {
			return "text/plain";
		}
		if (FilenameExtension.equals("VSD") || FilenameExtension.equals("vsd")) {
			return "application/vnd.visio";
		}
		if (FilenameExtension.equals("PPTX") || FilenameExtension.equals("pptx") || FilenameExtension.equals("PPT") || FilenameExtension.equals("ppt")) {
			return "application/vnd.ms-powerpoint";
		}
		if (FilenameExtension.equals("DOCX") || FilenameExtension.equals("docx") || FilenameExtension.equals("DOC") || FilenameExtension.equals("doc")) {
			return "application/msword";
		}
		if (FilenameExtension.equals("XML") || FilenameExtension.equals("xml")) {
			return "text/xml";
		}
		return "application/octet-stream";
	}

}
