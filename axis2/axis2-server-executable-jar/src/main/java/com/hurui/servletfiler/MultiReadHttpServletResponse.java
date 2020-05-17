package com.hurui.servletfiler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class MultiReadHttpServletResponse extends HttpServletResponseWrapper {

    private ServletOutputStream outputStream;
    private PrintWriter writer;
    private CachedServletOutputStream cachedOutputStream;

    public MultiReadHttpServletResponse(HttpServletResponse response) throws IOException {
        super(response);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (writer != null) {
            throw new IllegalStateException("getWriter() has already been called on this response.");
        }

        if (outputStream == null) {
            outputStream = getResponse().getOutputStream();
            cachedOutputStream = new CachedServletOutputStream(outputStream);
        }

        return cachedOutputStream;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (outputStream != null) {
            throw new IllegalStateException("getOutputStream() has already been called on this response.");
        }

        if (writer == null) {
        	cachedOutputStream = new CachedServletOutputStream(outputStream);
            writer = new PrintWriter(new OutputStreamWriter(cachedOutputStream, getResponse().getCharacterEncoding()), true);
        }

        return writer;
    }

    @Override
    public void flushBuffer() throws IOException {
        if (writer != null) {
            writer.flush();
        } else if (outputStream != null) {
        	cachedOutputStream.flush();
        }
    }

    public byte[] getCopy() {
        if (cachedOutputStream != null) {
            return cachedOutputStream.getCopy();
        } else {
            return new byte[0];
        }
    }
    
    private class CachedServletOutputStream extends ServletOutputStream {

        private OutputStream outputStream;
        private ByteArrayOutputStream cachedResponseOutputStream;

        public CachedServletOutputStream (OutputStream outputStream) {
            this.outputStream = outputStream;
            this.cachedResponseOutputStream = new ByteArrayOutputStream(1024);
        }

        @Override
        public void write(int b) throws IOException {
            outputStream.write(b);
            cachedResponseOutputStream.write(b);
        }

        public byte[] getCopy() {
            return cachedResponseOutputStream.toByteArray();
        }

    	@Override
    	public boolean isReady() {
    		return true;
    	}

    	@Override
    	public void setWriteListener(WriteListener listener) {
    		// NOOP	
    	}
    }

}
