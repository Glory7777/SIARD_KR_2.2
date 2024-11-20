package com.tmax.tibero.jdbc;

import com.tmax.tibero.jdbc.driver.TbConnection;
import com.tmax.tibero.jdbc.err.TbError;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stax.StAXResult;
import javax.xml.transform.stax.StAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.sql.*;

public class TbSQLXML implements SQLData, SQLXML {
    private TbConnection conn;

    private TbXMLInputStream contents;

    private TbXMLOutputStream outputStream;

    private Document docValue;

    private String strValue;

    private boolean isUsed;

    private boolean isFreed;

    private boolean isEmpty;

    public TbSQLXML(TbConnection paramTbConnection) {
        this.conn = paramTbConnection;
        this.contents = null;
        this.outputStream = null;
        this.docValue = null;
        this.strValue = null;
        this.isUsed = false;
        this.isFreed = false;
        this.isEmpty = false;
    }

    public TbSQLXML(TbConnection paramTbConnection, TbXMLInputStream paramTbXMLInputStream) {
        this.conn = paramTbConnection;
        this.contents = paramTbXMLInputStream;
        this.outputStream = null;
        this.docValue = null;
        this.strValue = null;
        this.isUsed = false;
        this.isFreed = false;
    }

    private void checkReadStatus() throws SQLException {
        if (this.strValue != null || this.isEmpty == true)
            return;
        if (this.contents == null)
            throw TbError.newSQLException(-90921);
        if (this.isUsed)
            throw TbError.newSQLException(-90922);
        try {
            this.contents.checkClosed();
        } catch (IOException iOException) {
            throw TbError.newSQLException(-90920, iOException.getMessage());
        }
    }

    private void checkWriteStatus() throws SQLException {
        if (this.contents != null)
            throw TbError.newSQLException(-90923);
        if (this.isUsed)
            throw TbError.newSQLException(-90924);
    }

    private void checkXMLClosed() throws SQLException {
        if (this.isFreed || (this.conn != null && this.conn.isClosed()))
            throw TbError.newSQLException(-90920);
    }

    public void free() throws SQLException {
        if (!this.isFreed) {
            this.isFreed = true;
            if (this.contents != null)
                try {
                    this.contents.close();
                } catch (IOException iOException) {
                    throw TbError.newSQLException(-90925, iOException.getMessage());
                }
        }
    }

    public InputStream getBinaryStream() throws SQLException {
        checkXMLClosed();
        checkReadStatus();
        this.isUsed = true;
        return getValue();
    }

    public Reader getCharacterStream() throws SQLException {
        checkXMLClosed();
        checkReadStatus();
        this.isUsed = true;
        return (Reader) ((this.contents == null) ? new StringReader(this.strValue) : new InputStreamReader(this.contents));
    }

    private final DOMResult getDOMResult() throws SQLException {
        if (this.outputStream != null)
            throw new AssertionError();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            this.docValue = documentBuilder.newDocument();
            return new DOMResult(this.docValue);
        } catch (ParserConfigurationException parserConfigurationException) {
            throw TbError.newSQLException(-90926, parserConfigurationException.getMessage());
        }
    }

    private final DOMSource getDOMSource() throws SQLException {
        Document document = null;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            try {
                document = documentBuilder.parse(this.contents);
            } catch (IOException iOException) {
                throw TbError.newSQLException(-90927, iOException.getMessage());
            }
            return new DOMSource(document);
        } catch (ParserConfigurationException parserConfigurationException) {
            throw TbError.newSQLException(-90926, parserConfigurationException.getMessage());
        } catch (SAXException sAXException) {
            throw TbError.newSQLException(-90926, sAXException.getMessage());
        }
    }

    private final SAXResult getSAXResult() throws SQLException {
        if (this.outputStream != null)
            throw new AssertionError();
        TransformerHandler transformerHandler = null;
        try {
            SAXTransformerFactory sAXTransformerFactory = (SAXTransformerFactory) TransformerFactory.newInstance();
            transformerHandler = sAXTransformerFactory.newTransformerHandler();
        } catch (TransformerConfigurationException transformerConfigurationException) {
            throw TbError.newSQLException(-90926, transformerConfigurationException.getMessage());
        } catch (ClassCastException classCastException) {
            throw TbError.newSQLException(-90926, classCastException.getMessage());
        }
        this.outputStream = new TbXMLOutputStream();
        transformerHandler.setResult(new StreamResult(this.outputStream));
        return new SAXResult(transformerHandler);
    }

    private final SAXSource getSAXSource() throws SQLException {
        try {
            InputSource inputSource = new InputSource(this.contents);
            XMLReader xMLReader = XMLReaderFactory.createXMLReader();
            return new SAXSource(xMLReader, inputSource);
        } catch (SAXException sAXException) {
            throw TbError.newSQLException(-90926, sAXException.getMessage());
        }
    }

    @Override
    public <T extends Source> T getSource(Class<T> paramClass) throws SQLException {
        checkXMLClosed();
        checkReadStatus();
        Source source = (paramClass == null) ? getSourceInternal(StreamSource.class) : getSourceInternal(paramClass);

        if (paramClass != null && !paramClass.isInstance(source)) {
            throw new SQLException("The request source type is not compatible");
        }
        return paramClass.cast(source);
    }

    private Source getSourceInternal(Class<?> paramClass) throws SQLException {
        Source source = null;
        if (paramClass == DOMSource.class) {
            source = (Source) paramClass.cast(getDOMSource());
        } else if (paramClass == SAXSource.class) {
            source = (Source) paramClass.cast(getSAXSource());
        } else if (paramClass == StAXSource.class) {
            source = (Source) paramClass.cast(getStAXSource());
        } else if (paramClass == StreamSource.class) {
            source = (Source) paramClass.cast(new StreamSource(this.contents));
        } else {
            throw TbError.newSQLException(-90928);
        }
        this.isUsed = true;
        return source;
    }

    private final StAXResult getStAXResult() throws SQLException {
        XMLOutputFactory xMLOutputFactory = XMLOutputFactory.newInstance();
        this.outputStream = new TbXMLOutputStream();
        try {
            XMLStreamWriter xMLStreamWriter = xMLOutputFactory.createXMLStreamWriter(this.outputStream);
            return new StAXResult(xMLStreamWriter);
        } catch (XMLStreamException xMLStreamException) {
            throw TbError.newSQLException(-90926, xMLStreamException.getMessage());
        }
    }

    private final StAXSource getStAXSource() throws SQLException {
        XMLInputFactory xMLInputFactory = XMLInputFactory.newInstance();
        try {
            XMLStreamReader xMLStreamReader = xMLInputFactory.createXMLStreamReader(this.contents);
            return new StAXSource(xMLStreamReader);
        } catch (XMLStreamException xMLStreamException) {
            throw TbError.newSQLException(-90926, xMLStreamException.getMessage());
        }
    }

    public String getString() throws SQLException {
        checkXMLClosed();
        checkReadStatus();
        if (this.contents == null)
            return this.strValue;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            int i;
            while ((i = this.contents.read()) != -1)
                stringBuilder.append((char) i);
        } catch (IOException iOException) {
            throw new SQLException(iOException.getMessage());
        }
        return stringBuilder.toString();
    }

    public InputStream getValue() throws SQLException {
        ByteArrayInputStream byteArrayInputStream;
        checkXMLClosed();
        if (!this.isUsed)
            throw TbError.newSQLException(-90929);
        if (this.outputStream != null) {
            byteArrayInputStream = this.outputStream.getInputStream();
        } else if (this.docValue != null) {
            TbXMLOutputStream tbXMLOutputStream = new TbXMLOutputStream();
            try {
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                transformerFactory.newTransformer().transform(new DOMSource(this.docValue), new StreamResult(tbXMLOutputStream));
            } catch (TransformerException transformerException) {
                throw TbError.newSQLException(-90926, transformerException.getMessage());
            }
            byteArrayInputStream = tbXMLOutputStream.getInputStream();
        } else if (this.strValue != null) {
            byteArrayInputStream = new ByteArrayInputStream(this.strValue.getBytes());
        } else {
            byteArrayInputStream = new ByteArrayInputStream(new byte[0]);
        }
        this.isFreed = true;
        return byteArrayInputStream;
    }

    public OutputStream setBinaryStream() throws SQLException {
        checkXMLClosed();
        checkWriteStatus();
        this.isUsed = true;
        this.outputStream = new TbXMLOutputStream();
        return this.outputStream;
    }

    public Writer setCharacterStream() throws SQLException {
        checkXMLClosed();
        checkWriteStatus();
        this.outputStream = new TbXMLOutputStream();
        this.isUsed = true;
        return new OutputStreamWriter(this.outputStream);
    }

    @Override
    public <T extends Result> T setResult(Class<T> paramClass) throws SQLException {
        checkXMLClosed();
        checkWriteStatus();
        Result result = (paramClass == null) ? setResultInternal(StreamResult.class) : setResultInternal(paramClass);

        if (paramClass != null && !paramClass.isInstance(result)) {
            throw new SQLException("The requested Result type is not compatible.");
        }

        return paramClass.cast(result);
    }

    private Result setResultInternal(Class<?> paramClass) throws SQLException {
        Result result = null;
        if (paramClass == DOMResult.class) {
            result = (Result) paramClass.cast(getDOMResult());
        } else if (paramClass == SAXResult.class) {
            result = (Result) paramClass.cast(getSAXResult());
        } else if (paramClass == StAXResult.class) {
            result = (Result) paramClass.cast(getStAXResult());
        } else if (paramClass == StreamResult.class) {
            this.outputStream = new TbXMLOutputStream();
            result = (Result) paramClass.cast(new StreamResult(this.outputStream));
        } else {
            throw TbError.newSQLException(-90928);
        }
        this.isUsed = true;
        return result;
    }

    public void setString(String paramString) throws SQLException {
        checkXMLClosed();
        checkWriteStatus();
        if (paramString == null)
            throw TbError.newSQLException(-90930);
        Document document = null;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            InputSource inputSource = new InputSource(new StringReader(paramString));
            try {
                document = documentBuilder.parse(inputSource);
            } catch (IOException iOException) {
                throw TbError.newSQLException(-90927, iOException.getMessage());
            }
        } catch (ParserConfigurationException parserConfigurationException) {
            throw TbError.newSQLException(-90926, parserConfigurationException.getMessage());
        } catch (SAXException sAXException) {
            throw TbError.newSQLException(-90926, sAXException.getMessage());
        }
        this.strValue = paramString;
        this.docValue = document;
        this.isUsed = true;
    }

    protected TbSQLXML() {
    }

    public String getSQLTypeName() throws SQLException {
        return "SYS.XMLTYPE";
    }

    public void readSQL(SQLInput paramSQLInput, String paramString) throws SQLException {
        TbClob tbClob = (TbClob) paramSQLInput.readClob();
        paramSQLInput.readString();
        paramSQLInput.readBlob();
        paramSQLInput.readBytes();
        paramSQLInput.readString();
        if (tbClob == null) {
            this.conn = null;
            this.contents = null;
            this.isEmpty = true;
        } else {
            this.conn = tbClob.getConnection();
            this.contents = new TbXMLInputStream(tbClob);
            this.isEmpty = false;
        }
        this.outputStream = null;
        this.docValue = null;
        this.strValue = null;
        this.isUsed = false;
        this.isFreed = false;
    }

    public void writeSQL(SQLOutput paramSQLOutput) throws SQLException {
        TbClob tbClob = this.conn.createTbClob();
        tbClob.setString(1L, this.strValue);
        paramSQLOutput.writeClob(tbClob);
        paramSQLOutput.writeString(null);
        paramSQLOutput.writeBlob(null);
        paramSQLOutput.writeBytes(null);
        paramSQLOutput.writeString(null);
    }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\TbSQLXML.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */