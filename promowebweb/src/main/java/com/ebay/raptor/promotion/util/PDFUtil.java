package com.ebay.raptor.promotion.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;

import com.ebay.kernel.logger.LogLevel;
import com.ebay.kernel.logger.Logger;
import com.ebay.raptor.promotion.subsidy.controllers.SubsidyController;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.ElementList;

/**
 * a util base on the itextpdf
 * @author pinchen
 *
 */
public class PDFUtil {
	private static final Logger logger = Logger.getInstance(PDFUtil.class);
	
	public static void generatePDF(String content,HttpServletResponse response){
//		Document document = null;
//		BaseFont bf = null;
//		Font fontChinese = null;
//		try {
//			bf = BaseFont.createFont("D:\\simsun.ttf", BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
//			
//			/** create the right font for chinese **/
//			fontChinese = new Font(bf, 10);
//			document = new Document(PageSize.A4);
//			
//			/** get the html content from javabean and convert to string **/
//			PdfWriter pdfWriter = PdfWriter.getInstance(document, outputStream);
//			document.open();
//
//			/** add the head of the pdf **/
//			Paragraph head = new Paragraph("卖家确认函", new Font(bf, 11));
//			head.setAlignment(1); // 0 align to the left , 1 align to the center
//			document.add(head);
//			
//		} catch (Exception e) {
//			logger.log(LogLevel.ERROR,"error occur while create PDF");
//		}
//		String dest = "D:\\itextout.pdf";
//		String inFile = "d:\\in.html";
//		File file = new File(inFile);
//		OutputStream outputStream = new FileOutputStream(new File(dest));
//		try {
//			
//			/** add the content of the pdf **/
////			XMLWorkerHelper.getInstance().parseXHtml(pdfWriter, document,inputFile);
//			
//			Paragraph context = new Paragraph();
//	        ElementList elementList =MyXMLWorkerHelper.parseToElementList("SSS", null);
//	        for (Element element : elementList) {
//	            context.add(element);
//	        }
//	        document.add(context);
//			
//			document.add(new Paragraph("   "));
//			document.add(new Paragraph("卖家（印刷体）：qichi（543290854326423）",
//					fontChinese));
//			document.add(new Paragraph(
//					"亲笔签名/公司公章： _______________________________", fontChinese));
//			document.add(new Paragraph("日期： ", fontChinese));
//			document.close();
//			System.out.println("-----------ok--------------");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

}
