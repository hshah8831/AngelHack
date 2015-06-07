package edu.neu.angelhack.manager;

import java.io.*;
import java.util.*;
import java.io.FileOutputStream; 
import java.io.IOException; 
import java.io.InputStream;

import javax.servlet.ServletException; 
import javax.servlet.annotation.MultipartConfig; 
import javax.servlet.annotation.WebServlet; 
import javax.servlet.http.HttpServlet; 
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse; 
import javax.servlet.http.Part;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/fileUpload") 
@MultipartConfig 
public class FileUploadServlet extends HttpServlet { 
  //private static Logger logger = Logger.getLogger(FileUploadServlet.class);

  public FileUploadServlet() { 
    super(); 
  }

  protected void doGet(HttpServletRequest request, 
      HttpServletResponse response) throws ServletException, IOException { 
    for (Part part : request.getParts()) { 
      System.out.println(part.getName()); 
      InputStream is = request.getPart(part.getName()).getInputStream(); 
      int i = is.available(); 
      byte[] b  = new byte[i]; 
      is.read(b); 
      System.out.println("Length : " + b.length); 
      String fileName = getFileName(part); 
      System.out.println("File name : " + fileName); 
      FileOutputStream os = new FileOutputStream("" + fileName); 
      os.write(b); 
      is.close(); 
      DummyClass dc = new DummyClass();
      dc.post1(fileName);
      
    }

  }

  private String getFileName(Part part) { 
    String partHeader = part.getHeader("content-disposition"); 
    //logger.info("Part Header = " + partHeader); 
    for (String cd : part.getHeader("content-disposition").split(";")) { 
      if (cd.trim().startsWith("filename")) { 
        return cd.substring(cd.indexOf('=') + 1).trim() 
            .replace("\"", ""); 
      } 
    } 
    return null;

  }

  protected void doPost(HttpServletRequest request, 
      HttpServletResponse response) throws ServletException, IOException { 
    doGet(request, response); 
  }

}
