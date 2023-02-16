<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.io.*"%>

<%@ page trimDirectiveWhitespaces="true"%>
<%@page import="main.*"%>
<%
 
String fileName = request.getParameter("file_name");
System.out.println("filename = " + fileName);
String savePath = "/upload";

    ServletContext context = pageContext.getServletContext() ;
    String sDownloadPath = context.getRealPath(savePath);

    String sFilePath = sDownloadPath + "/" +fileName;
    System.out.println(sFilePath);
    byte b[] = new byte[4096];

    String sMimeType = context.getMimeType(sFilePath);
    System.out.println("sMimeType>>>>>>" + sMimeType );

    if(sMimeType == null){
        sMimeType = "application/octet-stream";
    }
    response.setContentType(sMimeType);
 
    String sEncoding = new String (fileName.getBytes("euc-kr"), "ISO-8859-1");
    System.out.println(sEncoding);
   
    response.setHeader("Content-Disposition", "attachmen t : filename = " + sEncoding);
   
    try(

        BufferedOutputStream out2 = new BufferedOutputStream(response.getOutputStream());

        BufferedInputStream in = new BufferedInputStream(new FileInputStream(sFilePath));
    ){
        int numRead;
        
        while((numRead = in.read(b,0,b.length))!= -1){

            out2.write(b,0,numRead);
        }
    }catch(Exception e ){
        e.printStackTrace();
    }
        
%>