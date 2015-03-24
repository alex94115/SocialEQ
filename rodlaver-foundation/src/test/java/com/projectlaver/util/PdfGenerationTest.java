/*******************************************************************************
 * The MIT License (MIT)
 * 
 * Copyright (c) 2015 PURPLE & GOLD, INC
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package com.projectlaver.util;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDCcitt;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDPixelMap;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;


public class PdfGenerationTest {
	
	private static final PDFont TITLE_FONT = PDType1Font.HELVETICA_BOLD;
	
	@Test
	public void generatePdfTest() throws Exception {
		
		VoucherDTO dto = new VoucherDTO();

		
		// Set the file locations
		dto.setClientLogoUrl( "https://s3.amazonaws.com/qa-public.rodlaver.com/voucher-ab-lunchbox-logo.png" );
		//dto.setSocialEqLogoUrl( "file://~/Development/pdf/powered-by-socialeq.png" );
		dto.setClientCampaignImageUrl( "https://s3.amazonaws.com/qa-public.rodlaver.com/voucher-ab-lunchbox-banner.png" );
		
		// PDF contents
		dto.setMerchantName( 
				"A&B's Lunchbox" );
		dto.setListingTitle( 
				"Facebook Fan Special - Free Soup" );
		dto.setVoucherDetails( 
				"As a special thank you to our Facebook fans, we are offering free bowl of the soup of the day valid with any entree purchase." );
        dto.setVoucherTerms( 
        		"Can not be combined with any other discount. One coupon per person." );
		
        
        VoucherService service = new VoucherService();
        ReflectionTestUtils.setField( service, "socialEqLogoUrl", "https://s3.amazonaws.com/qa-public.rodlaver.com/vouchers-socialeq-logo.png");
        
        String pdfFilename = String.format( "%s.%s", RandomStringUtils.randomAlphanumeric(20), "pdf" );
        File barcodeImageFile = new File( "~/Development/pdf/CrunchifyQR.png" );
        
        File pdfFile = service.generateVoucherPdf(pdfFilename, barcodeImageFile, dto);
        assertTrue( pdfFile.length() > 0 );
        pdfFile.delete();
	}
    
}
