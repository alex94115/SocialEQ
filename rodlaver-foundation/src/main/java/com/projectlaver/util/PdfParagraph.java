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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class PdfParagraph {

    /** position X */
    private float x;

    /** position Y */
    private float y;

    /** width of this paragraph */
    private static final int PARAGRAPH_WIDTH = 552;

    /** text to write */
    private String text;

    /** font to use */
    private PDType1Font PARAGRAPH_FONT = PDType1Font.HELVETICA;

    /** font size to use */
    private int fontSize = 14;

    private int PARAGRAPH_COLOR = 255;

    public PdfParagraph(float x, float y, String text, int size ) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.fontSize = size;
    }

    /**
     * Break the text in lines
     * @return
     */
    public List<String> getLines() throws IOException {
        List<String> result = new ArrayList<String>();

        String[] split = text.split("(?<=\\W)");
        int[] possibleWrapPoints = new int[split.length];
        possibleWrapPoints[0] = split[0].length();
        for ( int i = 1 ; i < split.length ; i++ ) {
            possibleWrapPoints[i] = possibleWrapPoints[i-1] + split[i].length();
        }

        int start = 0;
        int end = 0;
        for ( int i : possibleWrapPoints ) {
            float width = PARAGRAPH_FONT.getStringWidth(text.substring(start,i)) / 1000 * fontSize;
            if ( start < end && width > PARAGRAPH_WIDTH ) {
                result.add(text.substring(start,end));
                start = end;
            }
            end = i;
        }
        // Last piece of text
        result.add(text.substring(start));
        return result;
    }

    public float getFontHeight() {
        return PARAGRAPH_FONT.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize;
    }

//    public Paragraph withWidth(int width) {
//        this.width = width;
//        return this;
//    }

//    public Paragraph withFont(PDType1Font font, int fontSize) {
//        this.PARAGRAPH_FONT = font;
//        this.fontSize = fontSize;
//        return this;
//    }

//    public Paragraph withColor(int color) {
//        this.color = color;
//        return this;
//    }

    public int getColor() {
        return PARAGRAPH_COLOR;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getWidth() {
        return PARAGRAPH_WIDTH;
    }

    public String getText() {
        return text;
    }

    public PDType1Font getFont() {
        return PARAGRAPH_FONT;
    }

    public int getFontSize() {
        return fontSize;
    }

}
