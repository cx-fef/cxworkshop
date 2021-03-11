package com.waratek;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileUploadServlet extends HttpServlet {
	
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
             throws ServletException, IOException {
				 
		response.sendRedirect(getServletContext().getRealPath("/"));		 
  }
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
             throws ServletException, IOException {
         boolean isMultipart = ServletFileUpload.isMultipartContent(request);
  


         if (isMultipart) {
		  response.getOutputStream().println("<html><body>");
          FileItemFactory factory = new DiskFileItemFactory();
          ServletFileUpload upload = new ServletFileUpload(factory);
  
             try {
              List items = upload.parseRequest(request);
                 Iterator iterator = items.iterator();
                 while (iterator.hasNext()) {
                     FileItem item = (FileItem) iterator.next();
                     if (!item.isFormField()) {
                         String fileName = new File(item.getName()).getName();  
                         String root = getServletContext().getRealPath("/");
                         File path = new File(root + File.separator + "uploads");
                         if (!path.exists()) {
                             boolean status = path.mkdirs();
                         }
  
                         File uploadedFile = new File(path + File.separator + fileName);
                         System.out.println("File uploaded. Path="+uploadedFile.getAbsolutePath());
                         item.write(uploadedFile);

                         response.setHeader("Cache-Control", "private, max-age=1");
                         response.setContentType("text/html; charset=UTF-8");
                         response.getOutputStream().println("File uploaded.");
                         response.getOutputStream().println("<br/><br/>Access the file here: <a href=\"uploads/"+fileName+"\" target=\"_blank\" >"+fileName+"</a>");
						 response.getOutputStream().println("<br/><br/><a href=\""+request.getContextPath()+"\">Upload another file</a>");
                         response.getOutputStream().flush();

                     }
                 }
             } catch (FileUploadException e) {
                 e.printStackTrace();
				 response.sendRedirect(request.getContextPath());
             } catch (Exception e) {
                 e.printStackTrace();
				 response.sendRedirect(request.getContextPath());
             }
			 response.getOutputStream().println("</body></html>");
         }
		 else {
			 response.sendRedirect(request.getContextPath()); 
		 }
     }

}