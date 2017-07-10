package com.dryork.vision.base.util.dcm;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.image.PaletteColorModel;
import org.dcm4che3.imageio.plugins.dcm.DicomImageReadParam;

public class ConvertImage {
	public ConvertImage() {
		initImageWriter();
	}

	public static ImageReader initImageReader() {
		ImageIO.scanForPlugins();
		return ImageIO.getImageReadersByFormatName("DICOM").next();
	}

	private String suffix;
	private int frame = 1;
	private int windowIndex;
	private int voiLUTIndex;
	private boolean preferWindow = true;
	private float windowCenter;
	private float windowWidth;
	private boolean autoWindowing = true;
	private Attributes prState;
	private final ImageReader imageReader = initImageReader();
	private ImageWriter imageWriter;
	private ImageWriteParam imageWriteParam;
	private int overlayActivationMask = 0xffff;
	private int overlayGrayscaleValue = 0xffff;

	public void initImageWriter() {
		Iterator<ImageWriter> imageWriters = ImageIO
				.getImageWritersByFormatName("JPG");
		if (!imageWriters.hasNext())
			throw new IllegalArgumentException("FORMATO NAO SUPORTADO!");
		this.suffix = "jpeg";
		imageWriter = imageWriters.next();
		imageWriteParam = imageWriter.getDefaultWriteParam();
	}

	public static void main(String[] args) {
		File file = new File("/opt/dcm/nnnn.jpg");
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		new ConvertImage().mconvert(new File("/opt/dcm/1.2.826.0.1.3680043.6.13011.31273.20170320143712.524.7.dcm"), new File(
				"/opt/dcm/nnnn.jpg"));
	}

	public void mconvert(File src, File dest) {
		if (src.isDirectory()) {
			dest.mkdir();
			for (File file : src.listFiles())
				mconvert(file, new File(dest, file.isFile() ? suffix(file)
						: file.getName()));
			return;
		}
		if (dest.isDirectory())
			dest = new File(dest, suffix(src));
		try {
			convert(src, dest);
			System.out.println("Convertido");
		} catch (Exception e) {
			System.out.println("Falha ao converter. Erro: " + e);
			e.printStackTrace(System.out);
		}
	}

	public BufferedImage getBufferImage(File src) throws IOException {
		ImageInputStream iis = ImageIO.createImageInputStream(src);
		try {
			BufferedImage bi = readImage(iis);
			bi = convert(bi);
			return bi;
		} finally {
			try {
				iis.close();
			} catch (IOException ignore) {
			}
		}
	}

	public void convert(File src, File dest) throws IOException {
		ImageInputStream iis = ImageIO.createImageInputStream(src);
		try {
			BufferedImage bi = readImage(iis);
			bi = convert(bi);
			dest.delete();
			ImageOutputStream ios = ImageIO.createImageOutputStream(dest);
			try {
				writeImage(ios, bi);
			} finally {
				try {
					ios.close();
				} catch (IOException ignore) {
				}
			}
		} finally {
			try {
				iis.close();
			} catch (IOException ignore) {
			}
		}
	}

	private BufferedImage convert(BufferedImage bi) {
		ColorModel cm = bi.getColorModel();
		if (cm instanceof PaletteColorModel)
			return ((PaletteColorModel) cm).convertToIntDiscrete(bi.getData());
		return bi;
	}

	private BufferedImage readImage(ImageInputStream iis) throws IOException {
		imageReader.setInput(iis);
		return imageReader.read(frame - 1, readParam());
	}

	private ImageReadParam readParam() {
		DicomImageReadParam param = (DicomImageReadParam) imageReader
				.getDefaultReadParam();
		param.setWindowCenter(windowCenter);
		param.setWindowWidth(windowWidth);
		param.setAutoWindowing(autoWindowing);
		param.setWindowIndex(windowIndex);
		param.setVOILUTIndex(voiLUTIndex);
		param.setPreferWindow(preferWindow);
		param.setPresentationState(prState);
		param.setOverlayActivationMask(overlayActivationMask);
		param.setOverlayGrayscaleValue(overlayGrayscaleValue);
		return param;
	}

	private void writeImage(ImageOutputStream ios, BufferedImage bi)
			throws IOException {
		imageWriter.setOutput(ios);
		imageWriter.write(null, new IIOImage(bi, null, null), imageWriteParam);
	}

	private String suffix(File src) {
		return src.getName() + '.' + suffix;
	}

}
