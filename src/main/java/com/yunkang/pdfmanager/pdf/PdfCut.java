package com.yunkang.pdfmanager.pdf;

import com.yunkang.pdfmanager.EnvConfig;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class PdfCut {


    public static void cut(String path){
        int origialDensity = Integer.parseInt(EnvConfig.getProperty("output.picture.density"));
        if(origialDensity < 0 || origialDensity > 1000) {
            System.out.println("Density配置异常");
            return;
        }

        double ratio;
        int startX = 0;
        int startY = 0;
        int cutWidth = 0;
        int cutHeight = 0;
        int whiteX = 0;
        int whiteY = 0;
        if((500 - origialDensity) == 0){
            startX = 448;
            startY = 1025;
            cutWidth = 660;
            cutHeight = 1512;
            whiteX = 134;
            whiteY = 100;
        }else if((500 - origialDensity) > 0){
            ratio = (500 - origialDensity)*0.002;
            startX = (int)(448-448*ratio);
            startY = (int)(1025-1025*ratio);
            cutWidth = (int)(660-660*ratio);
            cutHeight = (int)(1512-1512*ratio);
            whiteX = (int)(134-134*ratio);
            whiteY = (int)(100-100*ratio);
        }else if((500 - origialDensity) < 0){
            ratio = 1+(Math.abs(origialDensity - 500))*0.002;
            startX = (int)(448+448*ratio);
            startY = (int)(1025+1025*ratio);
            cutWidth = (int)(660+660*ratio);
            cutHeight = (int)(1512+1512*ratio);
            whiteX = (int)(134+134*ratio);
            whiteY = (int)(100+100*ratio);
        }

        try {
            PDDocument load = PDDocument.load(new File(path));
            PDFRenderer pdfRenderer = new PDFRenderer(load);
            BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(0, origialDensity, ImageType.RGB);
            BufferedImage subimage = bufferedImage.getSubimage(startX, startY, bufferedImage.getWidth()-cutWidth, bufferedImage.getHeight()-cutHeight);
            int width = subimage.getWidth();
            int height = subimage.getHeight();
            for(int i=width/2-whiteX;i<width;i++){for (int j=height-whiteY;j<height;j++){subimage.setRGB(i,j,-1);}}

            String model = EnvConfig.getProperty("output.storage.model");
            String suffix = EnvConfig.getProperty("output.picture.suffix");
            if("test".equals(model)){
                String filename = EnvConfig.getProperty("output.picture.dir")+ UUID.randomUUID().toString()+"."+suffix;
                ImageIOUtil.writeImage(subimage,filename,origialDensity);
            }else if("prod".equals(model)){
                //存储到生产环境
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(subimage,suffix,baos);
                byte[] bytes = baos.toByteArray();
//                key = FsServiceUtil.getFastdfsService().storeFile(new MetadataInfo(Constants.SYSID), type, picData);

                baos.close();
            }


            load.close();



//            JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(new FileOutputStream(outFile));
//            JPEGEncodeParam jpegEncodeParam = jpegEncoder.getDefaultJPEGEncodeParam(bufferedImage);
//            jpegEncodeParam.setDensityUnit(JPEGDecodeParam.DENSITY_UNIT_DOTS_INCH);
//            jpegEncoder.setJPEGEncodeParam(jpegEncodeParam);
//            jpegEncodeParam.setQuality(1f,false);
//            jpegEncodeParam.setXDensity(3000);
//            jpegEncodeParam.setYDensity(3000);
//            jpegEncoder.encode(bufferedImage,jpegEncodeParam);
//            bufferedImage.flush();

//            密度为500配置
//            System.out.println("width=" + bufferedImage.getWidth() + ",height:"+bufferedImage.getHeight());
//            BufferedImage subimage = bufferedImage.getSubimage(448, 1025, bufferedImage.getWidth()-660, bufferedImage.getHeight()-1512);
//            int width = subimage.getWidth();
//            int height = subimage.getHeight();
//            for(int i=width/2-134;i<width;i++){
//                for (int j=height-100;j<height;j++){
//                    int rgb = subimage.getRGB(i, j);
//                    System.out.println("old rgb = [" + rgb + "]");
//                    subimage.setRGB(i,j,-1);
//                }
//            }

//            密度为300配置
//            BufferedImage subimage = bufferedImage.getSubimage(268, 615, bufferedImage.getWidth()-396, bufferedImage.getHeight()-907);
//            int width = subimage.getWidth();
//            int height = subimage.getHeight();
//            for(int i=width/2-80;i<width;i++){
//                for (int j=height-60;j<height;j++){
//                    int rgb = subimage.getRGB(i, j);
//                    System.out.println("old rgb = [" + rgb + "]");
//                    subimage.setRGB(i,j,-1);
//                }
//            }


//            ImageIOUtil.writeImage(bufferedImage,"d:\\345.jpg",300);
//            ImageIO.write(bufferedImage,"JPEG",outFile);



        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
