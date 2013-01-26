/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/citations/trunk/citations-osid/xserver/src/java/org/sakaibrary/xserver/XMLCleanup.java $
 * $Id: XMLCleanup.java 105079 2012-02-24 23:08:11Z ottenhoff@longsight.com $
 ***********************************************************************************
 *
 * Copyright (c) 2006, 2007, 2008 The Sakai Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.opensource.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 **********************************************************************************/

package org.sakaibrary.xserver;

import java.io.*;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;

public class XMLCleanup extends DefaultHandler {
	private static final org.apache.commons.logging.Log LOG =
		org.apache.commons.logging.LogFactory.getLog(
				"org.sakaibrary.xserver.XMLCleanup" );
	
    private ByteArrayOutputStream bytes;
    private Writer out;
    private InputStream inputXml;
    StringBuilder textBuffer;
    private String indentString = "  "; // Amount to indent
    private int indentLevel = 0;
    
    // X-Server error handling
    private boolean error = false;
    private boolean error_codeFlag = false;
    private boolean error_textFlag = false;
    private String error_code;
    private String error_text;
    
    public XMLCleanup() {
    	bytes = new ByteArrayOutputStream();
    	
    	try {
    		out = new OutputStreamWriter( bytes, "UTF8" );
    	} catch( UnsupportedEncodingException e ) {
    		LOG.warn( "XMLCleanup() unsupported encoding: " + e.getMessage() );
    	}
    }

    public ByteArrayOutputStream cleanup( InputStream xml) throws XServerException {
    	inputXml = xml;
    	
        // Use the default (non-validating) parser
        SAXParserFactory factory = SAXParserFactory.newInstance();

        try {
            // Parse the input
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse( inputXml, this );
            
            // close the stream
            inputXml.close();
        } catch (SAXParseException spe) {
            // Use the contained exception, if any
            Exception x = spe;

            if (spe.getException() != null) {
                x = spe.getException();
            }

            // Error generated by the parser
        	LOG.warn("XMLCleanup.cleanup() parsing exception: " + spe.getMessage() +
        			" - xml line " + spe.getLineNumber() + ", uri " +
        			spe.getSystemId(), x );
        } catch (SAXException sxe) {
            // Error generated by this application
            // (or a parser-initialization error)
            Exception x = sxe;

            if (sxe.getException() != null) {
                x = sxe.getException();
            }

            LOG.warn( "XMLCleanup.cleanup() SAX exception: " + sxe.getMessage(),
            		x );
        } catch (ParserConfigurationException pce) {
            // Parser with specified options can't be built
        	LOG.warn( "XMLCleanup.cleanup() SAX parser cannot be built with " +
        			"specified options" );
        } catch (IOException ioe) {
            // I/O error
        	LOG.warn( "XMLCleanup.cleanup() IO exception", ioe );
        } catch (Throwable t) {
        	LOG.warn( "XMLCleanup.cleanup() exception", t );
        }
        
        if( error ) {
        	throw new XServerException( error_code, error_text );
        }
        
        return bytes;
    }

    //===========================================================
    // SAX DocumentHandler methods
    //===========================================================
    public void setDocumentLocator(Locator l) {
        // Save this to resolve relative URIs or to give diagnostics.
        try {
            out.flush();
        } catch (IOException e) {
            // Ignore errors
        }
    }

    public void startDocument() throws SAXException {
        emit("<?xml version='1.0' encoding='UTF-8'?>");
    }

    public void endDocument() throws SAXException {
        nl();
        try {
            out.flush();
        } catch (IOException e) {
            throw new SAXException("I/O error", e);
        }
    }

    public void startElement(String namespaceURI, String sName, // simple name
        String qName, // qualified name
        Attributes attrs) throws SAXException {
        echoText();
        indentLevel++;
        nl();

        String eName = sName; // element name

        if ("".equals(eName)) {
            eName = qName; // not namespaceAware
        }

        emit("<" + eName);

        if( eName.equals( "error_code" ) ) {
        	// hit an error
        	error = true;
        	error_codeFlag = true;
        } else if( eName.equals( "error_text" ) ) {
        	error_textFlag = true;
        }
        
        if (attrs != null) {
            for (int i = 0; i < attrs.getLength(); i++) {
                String aName = attrs.getLocalName(i); // Attr name 

                if ("".equals(aName)) {
                    aName = attrs.getQName(i);
                }

                if( !aName.equals( "xmlns" ) && !aName.equals( "xmlns:xsi" ) &&
                		!aName.equals( "xsi:schemaLocation" ) ) {
                	emit(" ");
                	emit(aName);
                	emit(" = \"");
                	emit(attrs.getValue(i));
                	emit("\"");
                }
            }
        }

        emit(">");
    }

    public void endElement(String namespaceURI, String sName, // simple name
        String qName // qualified name
    ) throws SAXException {
        echoText();
        nl();
        
        // X-Server error handling
        if( error_codeFlag ) error_codeFlag = false;
        if( error_textFlag ) error_textFlag = false;

        String eName = sName; // element name

        if ("".equals(eName)) {
            eName = qName; // not namespaceAware
        }

        emit("</" + eName + ">");
        indentLevel--;
    }

    public void characters(char[] buf, int offset, int len)
        throws SAXException {
        String s = new String(buf, offset, len);
        
        // take care of basic entity references before printing
    	s = s.replaceAll( "&", "&amp;" );
    	s = s.replaceAll( "<", "&lt;" );
    	s = s.replaceAll( ">", "&gt;" );
//    	s = s.replaceAll( "'", "&apos;" );
//    	s = s.replaceAll( "\"", "&quot;" );

        if (textBuffer == null) {
            textBuffer = new StringBuilder(s);
        } else {
            textBuffer.append(s);
        }
        
        if( error_codeFlag ) error_code = textBuffer.toString();
        if( error_textFlag ) error_text = textBuffer.toString();
    }

    public void ignorableWhitespace(char[] buf, int offset, int len)
        throws SAXException {
        // Ignore it
    }

    //===========================================================
    // SAX ErrorHandler methods
    //===========================================================
    public void error(SAXParseException e) throws SAXParseException {
        throw e;
    }

    // dump warnings too
    public void warning(SAXParseException err) throws SAXParseException {
        LOG.warn("SAXParser warning" + ", xml line " + err.getLineNumber() +
            ", uri " + err.getSystemId());
        LOG.warn("   " + err.getMessage());
    }

    //===========================================================
    // Utility Methods
    //===========================================================
    private void echoText() throws SAXException {
        if (textBuffer == null) {
            return;
        }

        String s = "" + textBuffer;

        if (!s.trim().equals("")) {
            emit(s);
        }

        textBuffer = null;
    }

    // Wrap I/O exceptions in SAX exceptions, to
    // suit handler signature requirements
    private void emit(String s) throws SAXException {
        try {
            out.write(s);
            out.flush();
        } catch (IOException e) {
            throw new SAXException("I/O error", e);
        }
    }

    // Start a new line
    // and indent the next line appropriately
    private void nl() throws SAXException {
        String lineEnd = System.getProperty("line.separator");

        try {
            out.write(lineEnd);

            for (int i = 0; i < indentLevel; i++)
                out.write(indentString);
        } catch (IOException e) {
            throw new SAXException("I/O error", e);
        }
    }
}
