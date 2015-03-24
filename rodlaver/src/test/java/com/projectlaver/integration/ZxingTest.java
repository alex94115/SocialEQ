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
package com.projectlaver.integration;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.junit.Test;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.aztec.AztecWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class ZxingTest {

	
	/**
	 * This test fails because the data is too long to be encoded into the barcode.
	 * @throws Exception
	 */
	@Test
	public void aztecCodeTest() throws Exception {
		
		String myCodeText = "http://rodlaver.elasticbeanstalk.com/listing/redeem/aab697d69bd0828e37f67daa855da0010c3358a19c1b411a9f051cbfffd1d046eace5eead2e54afcdab620f2db5f4145";
        String filePath = "~/Downloads/CrunchifyAztec.png";
        int size = 125;
        String fileType = "png";
        File myFile = new File( filePath );
		Map<EncodeHintType, Object> hintMap = new HashMap<EncodeHintType, Object>();
		hintMap.put( EncodeHintType.ERROR_CORRECTION, new Integer( 33 ) );
		hintMap.put( EncodeHintType.AZTEC_LAYERS, new Integer( 6 ) );
		
		AztecWriter aztecWriter = new AztecWriter();
		BitMatrix byteMatrix = aztecWriter.encode( myCodeText, BarcodeFormat.AZTEC, size, size, hintMap );
		
		int crunchifyWidth = byteMatrix.getWidth();
		BufferedImage image = new BufferedImage( crunchifyWidth, crunchifyWidth, BufferedImage.TYPE_INT_RGB );
		image.createGraphics();
		 
		Graphics2D graphics = (Graphics2D) image.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, crunchifyWidth, crunchifyWidth);
		graphics.setColor(Color.BLACK);
		 
		for (int i = 0; i < crunchifyWidth; i++) {
		    for (int j = 0; j < crunchifyWidth; j++) {
		        if (byteMatrix.get(i, j)) {
		        	graphics.fillRect(i, j, 1, 1);
		        }
		    }
		}
		ImageIO.write(image, fileType, myFile);
	}
	
	@Test
	public void qrCodeTest() throws Exception {
		
		String myCodeText = "https://rodlaver.elasticbeanstalk.com/listing/redeem/aab697d69bd0828e37f67daa855da0010c3358a19c1b411a9f051cbfffd1d046eace5eead2e54afcdab620f2db5f4145";
		//String myCodeText = "12345678901234567";
        String filePath = "~/Downloads/CrunchifyQR.png";
        int size = 200;
        String fileType = "png";
        File myFile = new File( filePath );
		Map<EncodeHintType, Object> hintMap = new HashMap<EncodeHintType, Object>();
		hintMap.put( EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M );
		
		QRCodeWriter qrWriter = new QRCodeWriter();
		BitMatrix byteMatrix = qrWriter.encode( myCodeText, BarcodeFormat.QR_CODE, size, size, hintMap );
		
		int crunchifyWidth = byteMatrix.getWidth();
		BufferedImage image = new BufferedImage( crunchifyWidth, crunchifyWidth, BufferedImage.TYPE_INT_RGB );
		image.createGraphics();
		 
		Graphics2D graphics = (Graphics2D) image.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, crunchifyWidth, crunchifyWidth);
		graphics.setColor(Color.BLACK);
		 
		for (int i = 0; i < crunchifyWidth; i++) {
		    for (int j = 0; j < crunchifyWidth; j++) {
		        if (byteMatrix.get(i, j)) {
		        	graphics.fillRect(i, j, 1, 1);
		        }
		    }
		}
		ImageIO.write(image, fileType, myFile);
	}
}

