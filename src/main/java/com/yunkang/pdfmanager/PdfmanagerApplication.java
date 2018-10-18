package com.yunkang.pdfmanager;

import com.yunkang.pdfmanager.pdf.PdfCut;
import org.opencv.core.Core;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class PdfmanagerApplication {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        SpringApplication.run(PdfmanagerApplication.class, args);
        String path = "d:\\外周血.pdf";
        PdfCut.cut(path);
    }
}
