<%@ page import="main.Main" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="main.*"%>
<!DOCTYPE html PUBLIC"-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>fileCheck</title>
</head>
 
    fileCheck jsp페이지

    <%    
    String uploadPath = application.getRealPath("/upload");

    
    // post방식에 대한 한글 인코딩 방식 지정 get방식은 서버의 server.xml에서 Connector태그에 URIEncoding="UTF-8" 추가
        request.setCharacterEncoding("UTF-8");
         
        // input type="name" 의 value값을 가져옴
        String name = request.getParameter("name");
        // input type="subject" 의 value값을 가져옴
        String subject = request.getParameter("subject");
        // 중복방지용으로 만들어져 넘겨진 파일명을 가져옴
        String fileName1 = request.getParameter("fileName1");
        // 본래의 파일명을 가져옴
        String originalName1 = request.getParameter("originalName1");
    %>

    <h3>업로드 파일 확인</h3>
    올린 사람 : <%=name %><br/>
    제목 : <%=subject %><br/>
    파일 다운로드 : <a id="downA" href="fileDownload"><%=originalName1%></a>

    
    <script type="text/javascript">
        document.getElementById("downA").addEventListener("click", function(event) {
            event.preventDefault();// a 태그의 기본 동작을 막음
            event.stopPropagation();// 이벤트의 전파를 막음
            // fileName1을 utf-8로 인코딩한다.
            var fName = encodeURIComponent("<%=fileName1%>");
            // 인코딩된 파일이름을 쿼리문자열에 포함시켜 다운로드 페이지로 이동
            window.location.href ="fileDownload.jsp?file_name="+fName;
            //  window.location.href ="fileView.jsp?file_name="+fName;
        });

    </script>
    <%
    String filePath = (uploadPath+"\\" + fileName1).replace("\\", "\\\\");
    Main main = new Main();
    main.go(filePath);

    %>
