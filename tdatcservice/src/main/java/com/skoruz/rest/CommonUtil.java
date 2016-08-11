package com.skoruz.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;

import com.skoruz.common.ApplicationConstants;

@Path("common")
public class CommonUtil {

@GET
@Path("/image/{fileName}")
public void imgageDisplay(@PathParam("fileName") String  fileName,@Context HttpServletRequest request,@Context HttpServletResponse response) throws IOException {
	 try {
	        response.setContentType("text/html;charset=UTF-8");
	        if(fileName == null || fileName.trim().equals("")){
	           fileName = "ImageNotFound.png";
	        }
	        String fileType = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
	        InputStream in = null;
	        try {
	   if ("jpeg".equalsIgnoreCase(fileType)
	     || "jpg".equalsIgnoreCase(fileType)) {
	    response.setContentType("image/jpg");
	   } else if ("png".equalsIgnoreCase(fileType)) {
	    response.setContentType("image/png");
	   } else if ("gif".equalsIgnoreCase(fileType)) {
	    response.setContentType("image/gif");
	   }
	   response.setHeader("Content-Disposition", "inline; filename="+ fileName);
	   String prefixFilePath=ApplicationConstants.DIRECTORY+ApplicationConstants.PHOTO;
	            System.out.println("File Path => "+prefixFilePath + fileName);
	            if(prefixFilePath!=null && (prefixFilePath.contains("http://") || prefixFilePath.contains("https://"))) {
	                URL url = new URL (prefixFilePath + fileName);
	                in = url.openStream();
	            } else {
	                File f = new File(prefixFilePath + fileName);
	                if(!f.exists() || !f.canRead()){
	                    f = new File(prefixFilePath + "ImageNotFound.png");
	                }
	                if(f.exists())
	                 in = new FileInputStream(f);
	            }
	            if(in!=null){
	                byte[] buff = new byte[5242880];
	                int bytesRead;
	                while (-1 != (bytesRead = in.read(buff, 0, buff.length))) {
	                    response.getOutputStream().write(buff, 0, bytesRead);
	                }
	            }
	        } catch (Exception e) {
	            System.err.println("Exception {}  "+e.getMessage());
	        } finally {
	            if (in != null) {
	                in.close();
	            }
	        }
	        response.getOutputStream().flush();
	    } finally {
	        response.getOutputStream().close();
	    }
	}



@GET
@Path("/fileviewer/{fileName}")
public void fileViewer(@PathParam("fileName") String  fileName,@Context HttpServletRequest request,@Context HttpServletResponse response) throws IOException {
  try {
         response.setContentType("application/pdf");
         if(fileName == null || fileName.trim().equals("")){
            fileName = "pdfNotfound";
         }
         String fileType = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
         InputStream in = null;
         try {
       
             response.setHeader("Content-Disposition", "attachment; filename="+ fileName);
             String prefixFilePath=ApplicationConstants.DIRECTORY+ApplicationConstants.FILE;
             System.out.println("File Path => "+prefixFilePath + fileName);
             if(prefixFilePath!=null && (prefixFilePath.contains("http://") || prefixFilePath.contains("https://"))) {
                 URL url = new URL (prefixFilePath + fileName);
                 in = url.openStream();
             } else {
                 File f = new File(prefixFilePath + fileName);
                 if(!f.exists() || !f.canRead()){
                     f = new File(prefixFilePath + "pdfNotfound");
                 }
                 if(f.exists())
                  in = new FileInputStream(f);
             }
             if(in!=null){
                 byte[] buff = new byte[1024];
                 int bytesRead;
                 while (-1 != (bytesRead = in.read(buff, 0, buff.length))) {
                     response.getOutputStream().write(buff, 0, bytesRead);
                 }
             }
         } catch (Exception e) {
             System.err.println("Exception {}  "+e.getMessage());
         } finally {
             if (in != null) {
              
              in.close();
             }
         }
         response.getOutputStream().flush();
     } finally {
         response.getOutputStream().close();
     }

}

}
