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

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDPixelMap;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.support.incrementer.AbstractDataFieldMaxValueIncrementer;
import org.springframework.jdbc.support.incrementer.MySQLMaxValueIncrementer;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

@Service
public class VoucherService {

	private final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	AbstractDataFieldMaxValueIncrementer voucherMaxValueIncrementer;
	
	// properties
	
	@Value("${APP_ENCRYPTION_PASSWORD}")
	private String password;
	
	@Value("${application.url}")
	private String applicationUrl;
	
	@Value("${aws.s3.access.key}")
	private String s3accessKey;

	@Value("${aws.s3.secret.key}")
	private String s3secretKey;

	@Value("${aws.s3.private.bucket.name}")
	private String s3privateBucketName;
	
	@Value("${voucher.public.s3.logo.url}")
	private String socialEqLogoUrl;
	
	/**
	 * Static Constants
	 */
	private static final String BARCODE_FILE_TYPE = "png";
	private static final String PDF_FILE_TYPE = "pdf";
	
	private static final int BARCODE_SIDE_LENGTH_PIXELS = 200;
	private static final PDFont TITLE_FONT = PDType1Font.HELVETICA_BOLD;
	
	public VoucherDTO createVoucher( VoucherDTO dto ) {
		
		this.generateVoucherAttributes( dto );
		
		String barcodeImageFilename = this.generateTempFilename( BARCODE_FILE_TYPE );
		File barcodeImage = this.generateBarcodeImage( barcodeImageFilename, dto.getSerialNumber() );
		
		String pdfFilename = this.generateTempFilename( PDF_FILE_TYPE );
		File voucherPdf = this.generateVoucherPdf( pdfFilename, barcodeImage, dto );
		
		this.savePdf( pdfFilename, voucherPdf );
		
		// add the pdf filename to the dto so it can be persisted
		dto.setFilename( pdfFilename );
		
		this.cleanupTempFiles( barcodeImage, voucherPdf );
		
		return dto;
	}
	
	File generateVoucherPdf( String pdfFilename, File barcodeImageFile, VoucherDTO dto ) {
        
		// the result 
		File pdfFile = null;
		
		// the document
        PDDocument doc = null;
        try
        {
        	// Create a new document and add a page to it
            doc = new PDDocument();
            PDPage page = new PDPage();
            doc.addPage( page );
            
            float pageWidth = page.getMediaBox().getWidth();
            float pageHeight = page.getMediaBox().getHeight();
            
            // For some reason this may need to happen after reading the image
            PDPageContentStream contentStream = new PDPageContentStream( doc, page );
            
            /**
             * Read & draw the client logo
             */
            this.drawImageWithMaxEdge( contentStream, 15f, 629, 150, this.readImage( new URL( dto.getClientLogoUrl() ), doc ) );
            
            /**
             *  Read & draw the barcode image
             */

            BufferedImage bim = ImageIO.read( barcodeImageFile );
            PDXObjectImage barcodeImage = new PDPixelMap( doc, bim );

            // inspired by http://stackoverflow.com/a/22318681/535646
            float barcodeScale = 1f; // reduce this value if the image is too large
            
            this.drawImage( contentStream, 206f, 315f, barcodeImage, barcodeScale );

            /** 
             * Read & draw the social eq logo image
             */
            
            float logoScale = 0.15f; // reduce this value if the image is too large
            this.drawImage( contentStream, 246f, 265f, this.readImage( new URL( this.socialEqLogoUrl ), doc ), logoScale );
            
            /**
             * Read & draw the client image
             */
            this.drawImageWithMaxEdge( contentStream, 0f, 145, 612, this.readImage( new URL( dto.getClientCampaignImageUrl() ), doc ) );
            
            
            /**
             * Add some rectangles to the page to set off the sections
             */
            
            contentStream.setNonStrokingColor( new Color( 0.82f, 0.82f, 0.82f, 0.6f )); // light grey

            /**
             *  around the merchant logo / title
             */

            contentStream.fillRect( 0, pageHeight - 10, pageWidth, 10 ); // top edge
            contentStream.fillRect( 0, pageHeight - 175, pageWidth, 10 ); // bottom edge
            contentStream.fillRect( 0, pageHeight - 175, 10, 175 ); // left edge
            contentStream.fillRect( 170, pageHeight - 175, 10, 175 ); // right of the logo
            contentStream.fillRect( pageWidth - 10, pageHeight - 175, 10, 175 ); // right edge
            
            // behind the terms and conditions
            contentStream.fillRect( 0f, 0f, pageWidth, 145f );
            
            /**
             *  Handle the text
             */

            // text color is black
            contentStream.setNonStrokingColor( Color.BLACK );
            
            // merchant name
            this.printNoWrap( page, contentStream, 195, 712, dto.getMerchantName(), 48 );
            
            // listing title
            this.printNoWrap( page, contentStream, 195, 662, dto.getVoucherTitle(), 24 );
            
            // Long description
            this.printNoWrap( page, contentStream, 30, 582, "Details:", 14 );
            
            PdfParagraph ld = new PdfParagraph( 30, 562, dto.getVoucherDetails(), 14 );
            this.printLeftAlignedWithWrap( contentStream, ld );
            
            // Terms and conditions
            this.printNoWrap( page, contentStream, 30, 115, "Terms & Conditions:", 12 );
            
            PdfParagraph toc = new PdfParagraph( 30, 100, dto.getVoucherTerms(), 10 );
            this.printLeftAlignedWithWrap( contentStream, toc );

            // Make sure that the content stream is closed:
            contentStream.close();
            
            String pdfPath = System.getProperty( "java.io.tmpdir" ) + pdfFilename;
            doc.save( pdfPath );
            
            pdfFile = new File( pdfPath );
            
        } catch( Exception e ) {
        	
        	throw new RuntimeException( "Could not create the PDF File.", e );
        	
        } finally {
            if( doc != null ) {
            	
            	try {
            		doc.close();
            	} catch( IOException e ) {
            		throw new RuntimeException( "Could not close the PDF Document.", e );
            	}
            }
        }
        
        return pdfFile;
    }
	
	/**
	 * This method creates the attributes for a new voucher, namely: 1) a salt
	 * value that is used to encrypt our counter (and can be used to decrypt a
	 * serial number, if necessary), and 2) the encrypted and hex-encoded value
	 * of a counter, which is considered to be a voucher's serial number.
	 * 
	 * The method relies on a MySQLMaxValueIncrementer, which will draw a new
	 * value out of a mysql table "sequence" each time this method is called.
	 * Gaps could develop in the sequence but that is not deemed to be a problem.
	 * 
	 * The encryption passphrase is read from an environment property that needs
	 * to be set in any environment that this method is run within.
	 */
	VoucherDTO generateVoucherAttributes( VoucherDTO dto ) {
		
		Long nextValue = this.voucherMaxValueIncrementer.nextLongValue();
		String salt = KeyGenerators.string().generateKey();
		dto.setSalt( salt );
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( String.format( "Creating a payment for a voucher; using the sequence value: [%d] and salt: [%s]", nextValue, salt ));
		}
		
		// Ensure that we always read a passphrase before invoking the encryption
		if( StringUtils.isBlank( this.password )) {
			throw new RuntimeException( "Cannot read the environment variable 'app.encryption.password'. Make sure that it is set in this environment." );
		}
		
		// Initialize an encryptor that will output a hex-encoded value (aka text)
		TextEncryptor encryptor = Encryptors.text( this.password, salt );
		
		// Do the actual encrption step of the counter value treated as a String
		String serialNumber = encryptor.encrypt( Long.toString( nextValue ));
		dto.setSerialNumber( serialNumber );
		
		// Set the initial status
		dto.setStatus( VoucherStatus.UNREDEEMED );
		
		return dto;
	}
	
	/**
	 * Generate the barcode image, write the file to a temp dir, and return a reference to the File.
	 * 
	 * @param barcodeFilename
	 * @param serialNumber
	 * @return a file that hold the image
	 */
	File generateBarcodeImage( String barcodeImageFilename, String serialNumber ) {
		
		// Create a barcode writer
		QRCodeWriter qrWriter = new QRCodeWriter();
		
		// Prepare the parameters to the barcode writer's encode method
		String barcodeUrl = this.createRedemptionUrl( serialNumber );
		Map<EncodeHintType, Object> hintMap = this.createBarcodeEncodingHintMap();
		
		// Encode the data
		BitMatrix byteMatrix = null;
		try {
			byteMatrix = qrWriter.encode( barcodeUrl, BarcodeFormat.QR_CODE, BARCODE_SIDE_LENGTH_PIXELS, BARCODE_SIDE_LENGTH_PIXELS, hintMap );
        } catch( Exception e ) {
        	this.logger.error( String.format( "Exception while trying to create the barcode for serialNumber: %s", serialNumber ), e);
        	throw new RuntimeException( e );
        }
		
		// Create the graphic version (image)
		int matrixLength = byteMatrix.getWidth();
		BufferedImage image = new BufferedImage( matrixLength, matrixLength, BufferedImage.TYPE_INT_RGB );
		image.createGraphics();
		 
		Graphics2D graphics = ( Graphics2D )image.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, matrixLength, matrixLength);
		
		graphics.setColor(Color.BLACK);
		for( int i = 0; i < matrixLength; i++ ) {
		    for( int j = 0; j < matrixLength; j++ ) {
		        if( byteMatrix.get( i, j )) {
		        	graphics.fillRect( i, j, 1, 1 );
		        }
		    }
		}
		
		// Write the barcode image to a temp file
		String barcodePath = System.getProperty( "java.io.tmpdir" ) + barcodeImageFilename;
		File barcodeTempFile = new File( barcodePath );
        
        try {
        	ImageIO.write( image, BARCODE_FILE_TYPE, barcodeTempFile );
        } catch( Exception e ) {
        	this.logger.error( String.format( "Exception while trying to write the voucher barcode for serialNumber: %s to a temp file with path: %s", serialNumber, barcodePath ), e);
        	throw new RuntimeException( e );
        }
		
	    return barcodeTempFile;
	}

	Map<EncodeHintType, Object> createBarcodeEncodingHintMap() {
		Map<EncodeHintType, Object> hintMap = new HashMap<EncodeHintType, Object>();
		hintMap.put( EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M );
		return hintMap;
	}
	
	/**
	 * Generate the environment-specific redemption URL that will be embeded into the barcode.
	 * This will be used to tell the barcode scanner what URL to open to mark the voucher as redeemed.
	 * 
	 * @param serialNumber
	 * @return
	 */
	String createRedemptionUrl( String serialNumber ) {
		
		String barcodeUrl = String.format( "%s/listing/redeem/%s", this.applicationUrl, serialNumber );
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( String.format( "Returning barcodeUrl: %s", barcodeUrl ));
		}
		
		return barcodeUrl;
	}
	
	String generateTempFilename( String fileType ) {
		return String.format( "%s.%s", RandomStringUtils.randomAlphanumeric(20), fileType );
	}
	
	/**
	 * Uploads the pdf file to the private S3 bucket.
	 * 
	 * @param pdfFilename
	 * @param pdf
	 */
	void savePdf( String pdfFilename, File pdf ) {
		AWSCredentials myCredentials = new BasicAWSCredentials( this.s3accessKey, this.s3secretKey );
		TransferManager tx = new TransferManager( myCredentials );
	    Upload myUpload = tx.upload( this.s3privateBucketName, pdfFilename, pdf );
	 
	    try {
	    	myUpload.waitForCompletion();
	    } catch( Exception e ) {
	    	this.logger.error( String.format( "Exception while trying to upload to S3 the PDF using the temp file with path: %s", pdf.getPath() ), e);
	    	throw new RuntimeException( "Unable to upload the PDF file to S3.", e );
	    }
	}
	
	
	/**
	 * Deletes one or more temp files
	 * 
	 * @param files
	 */
	void cleanupTempFiles( File... files ) {
		
		for( File file : files ) {
			file.delete();
		}
	}
	
    void drawImageWithMaxEdge(PDPageContentStream contentStream, float x, float y, int maxEdge, PDXObjectImage image ) throws IOException {
		
		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();
		
		int longerSide = ( imageWidth >= imageHeight ) ? imageWidth : imageHeight;
		float scale = (float)maxEdge / longerSide;

		this.drawImage( contentStream, x, y, image, scale );
	}


	void drawImage(PDPageContentStream contentStream, float x, float y, PDXObjectImage image, float scale ) throws IOException {
		
		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( String.format( "Effective image width, height: [%f,%f]", imageWidth * scale, imageHeight * scale ) );
		}

		contentStream.drawXObject( image, x, y, imageWidth * scale, imageHeight * scale );
	}


	void printCenteredNoWrap( PDPage page, PDPageContentStream contentStream, int offsetX, int offsetY, String text, int fontSize ) throws IOException {
		
		// Do the calculation to get this centered
		float lineWidth = TITLE_FONT.getStringWidth( text ) / 1000 * fontSize;
		
		contentStream.beginText();
		contentStream.setFont( TITLE_FONT, fontSize );
		contentStream.moveTextPositionByAmount( offsetX + ( ( page.getMediaBox().getWidth() - offsetX - lineWidth ) / 2 ), offsetY );
		contentStream.drawString( text );
		contentStream.endText();
	}
	
	void printNoWrap( PDPage page, PDPageContentStream contentStream, int offsetX, int offsetY, String text, int fontSize ) throws IOException {
		
		contentStream.beginText();
		contentStream.setFont( TITLE_FONT, fontSize );
		contentStream.moveTextPositionByAmount( offsetX, offsetY );
		contentStream.drawString( text );
		contentStream.endText();
		
	}


	PDXObjectImage readImage( URL imageUrl, PDDocument doc ) throws IOException, FileNotFoundException {
		
		PDXObjectImage ximage;
		String path = imageUrl.getPath().toLowerCase();
		
		if( path.endsWith( ".jpg" ) ) {
		    //ximage = new PDJpeg( doc, new FileInputStream( imageUrl ));
			throw new IOException( "Image type not supported: " + imageUrl );
		    
		} else if( path.endsWith(".tif") || path.endsWith(".tiff")) {
		    //ximage =  new PDCcitt( doc, new RandomAccessFile( new File( imageUrl ),"r"));
			throw new IOException( "Image type not supported: " + imageUrl );
		    
		} else if( path.endsWith(".gif") || path.endsWith(".bmp") || path.endsWith(".png")) {
		    //BufferedImage bim = ImageIO.read( new File( imageUrl ));
			BufferedImage bim = ImageIO.read( imageUrl );
		    ximage = new PDPixelMap( doc, bim );
		    
		} else {
		    throw new IOException( "Image type not supported: " + imageUrl );
		}
		
		return ximage;
	}
	
	void printLeftAlignedWithWrap( PDPageContentStream contentStream, PdfParagraph paragraph ) throws IOException {
	    contentStream.beginText();
	    contentStream.appendRawCommands( paragraph.getFontHeight() + " TL\n" );
	    contentStream.setFont( paragraph.getFont(), paragraph.getFontSize() );
	    contentStream.moveTextPositionByAmount( paragraph.getX(), paragraph.getY() );
	    contentStream.setStrokingColor( paragraph.getColor() );

	    List<String> lines = paragraph.getLines();
	    for (Iterator<String> i = lines.iterator(); i.hasNext(); ) {
	        contentStream.drawString( i.next().trim() );
	        if( i.hasNext() ) {
	            contentStream.appendRawCommands("T*\n");
	        }
	    }
	    contentStream.endText();

	}

}
