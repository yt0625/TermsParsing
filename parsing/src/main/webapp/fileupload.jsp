<%@page import="java.io.File"%>
<%@page import="java.util.Enumeration"%>
<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="main.*"%>
<!DOCTYPE html PUBLIC"-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>fileUpload</title>
</head>
 
<%
	//파일 저장 위치
    String uploadPath = application.getRealPath("/upload");
    out.println("절대경로 : " + uploadPath +"<br/>");
     
    int maxSize =1024 *1024 *10;// 한번에 올릴 수 있는 파일 용량 : 10M로 제한
     
    String name ="";
    String subject ="";
     
    String fileName1 ="";// 중복처리된 이름
    String originalName1 ="";// 중복 처리전 실제 원본 이름
    long fileSize =0;// 파일 사이즈
    String fileType ="";// 파일 타입
     
    MultipartRequest multi =null;
     
    try{
     
        multi =new MultipartRequest(request,uploadPath,maxSize,"utf-8",new DefaultFileRenamePolicy());
         
     
        name = multi.getParameter("name");
    
        subject = multi.getParameter("subject");
         
       
       Enumeration<?> files = multi.getFileNames();
         
        while(files.hasMoreElements()){
           
            String file1 = (String)files.nextElement();
           
            originalName1 = multi.getOriginalFileName(file1);
           
            fileName1 = multi.getFilesystemName(file1);
          
            fileType = multi.getContentType(file1);
        
            File file = multi.getFile(file1);
          
            fileSize = file.length();
        }
    }catch(Exception e){
        e.printStackTrace();
    }
%>

<form action="fileCheck.jsp" method="post" name="fileCheckFormName">
    <input type="hidden" value="<%=name%>" name="name" />
    <input type="hidden" value="<%=subject%>" name="subject" />
    <input type="hidden" value="<%=fileName1%>" name="fileName1" />
    <input type="hidden" value="<%=originalName1%>" name="originalName1" />
</form>
 
<a href="#" onclick="javascript:document.fileCheckFormName.submit()">업로드 파일 확인하기 :<%=fileName1 %></a>
