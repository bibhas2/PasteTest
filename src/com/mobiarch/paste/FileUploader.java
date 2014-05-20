package com.mobiarch.paste;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FileUploader
 */
@WebServlet("/FileUploader")
public class FileUploader extends HttpServlet {
	private static final long serialVersionUID = 1L;
    Logger logger = Logger.getLogger(getClass().getName());
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Compute the output file name extension based on image MIME type
	    String mimeType = request.getContentType();
	    String extension = null;
		String supportedFormatTable[][] = {
			{"image/png", "png"},
			{"image/jpeg", "jpg"},
			{"image/gif", "gif"}
		};
		
	    logger.info("Paste data of type: " + request.getContentType());
	    
		for (int i = 0; i < supportedFormatTable.length; ++i) {
			if (supportedFormatTable[i][0].equals(mimeType)) {
				extension = supportedFormatTable[i][1];
				
				break;
			}
		}
		
		if (extension == null) {
			throw new IOException("Unsupported data type: " + mimeType);
		}

	    //Read the file contents from the input stream
		File saveTo = new File("image." + extension);
		FileOutputStream os = new FileOutputStream(saveTo);
		InputStream is = request.getInputStream();
	    byte buff[] = new byte[256];
	    int len;

	    while ((len = is.read(buff)) > 0) {
	    	os.write(buff, 0, len);
	    }
	    os.close();
	    
	    //Show the file name in browser
	    PrintWriter out = response.getWriter();
	    
	    out.println("Saved image to: " + saveTo.getAbsolutePath());
	}
}
