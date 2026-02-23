# Part1. 웹 프로그래밍 시작

## 자바 웹 개발 환경 만들기

### 웹 프로젝트의 기본구조

#### 웹 서버(Web Server) 혹은 WAS(Web Application Server)
- 이미지와 같은 고정된 데이터를 제공하거나(웹 서버) 동적으로 매번 새로운 데이터를 만들어 낼 수 있는 WAS(실제 운영 환경에서는 보통 웹 서버와 WAS를 분리해서 운영하지만, 대부분의 WAS는 웹 서버 기능도 겸하고 있으므로 실습 시에는 WAS만으로 구성 가능)
- 관련 기술: Servlet/JSP, 각종 프레임워크들

### 톰캣(Tomcat) 설정과 웹 프로젝트 생성

#### 주의사항
- 작성일 기준(2026년 02월 23일) Java EE => Jakarta EE
- Java EE 8 / Tomcat 9 버전 사용
- template: Web Application 체크
- Language: Java 11
- Build System: Gradle

#### 한글 설정
- Tomcat Edit Configuration
  - VM 옵션: -Defile.encoding=UTF-8 -Dconsole.encoding=UTF-8 추가

#### 톰캣 세부 설정
- Tomcat Edit Configuration
  - 배포 - Deploy at the server startup - exploded로 설정
    - .war: 압축 파일 상태: 변경 사항이 생기면 톰캣 서버를 재실행해야함
    - .war (exploded): 압축 해제 상태: 재시작 필요없이 반영 가능

#### 서블릿 코드 작성하기
```java
package com.zerock.w1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "myServlet", urlPatterns = "/my")
public class MyServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {

        resp.setContentType("text/html");

        PrintWriter out = resp.getWriter();
        out.println("<html><body>");
        out.println("<h1>MyServlet</h1>");
        out.println("</body></html>");
    }
}
```
- @WebServlet: 브라우저의 경로와 해당 서블릿을 연결하는 설정
  - name: 톰캣 서버가 내부적으로 이 서블릿 객체를 관리하고 구별할 때 쓰는 **'이름표'**
  - urlPatterns: 사용자가 브라우저 주소창에 어떤 경로(URL)를 입력해야 이 코드가 실행될 수 있는지에 대한 것
  - value: urlPatterns랑 동일함
- PrintWriter: 웹 브라우저 화면에 출력하기
- 브라우저 입력: localhost:8080/my

#### JSP 코드 작성하기
```html
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>Test JSP PAGE</h1>
</body>
</html>
```
- 브라우저 입력: localhost:8080/test.jsp

---

## 웹 기본 동작 방식 이해하기

### Request(요청) / Response(응답)
- GET 방식: 주소창에 직접 원하는 데이터를 적거나 링크를 클릭해서 호출
  - 웹의 주소에서 호출할 때 필요한 데이터를 ?와 &, =를 이용해서 같이 전송하는 방식
- POST 방식: 입력 화면에서 필요한 내용을 작성한 후에 전송과 같은 버튼 등을 클릭해서 호출
  - 주소와 데이터를 따로 보내는 방식

#### Request
브라우저에서 서버에 데이터를 요구하는 것

#### Response
서버에서 브라우저로 데이터를 보내주는 것
  - 정적(static) 데이터: 항상 동일하게 고정된 데이터를 전송하는 방식으로 주로 파일로 고정된 HTML, CSS, 이미지 파일 등의 데이터
  - 동적(dynamic) 데이터: 매번 필요할 때마다 다른 데이터를 동적으로 구성해서 전송하는 방식. 서버에서 데이터를 만들어서 보내는 방식을 Server-Side-Rendering이라고 함

### HTTP라는 약속
브라우저의 요청과 서버의 응답 사이에는 중요한 약속을 통해서 처리되는데, 이러한 약속을 프로토콜이라고 하고 웹에서는 HTTP라는 방식으로 데이터를 주고 받음

#### HTTP 메세지 구성
- 헤더(Header)
- 바디(Body)
- 브라우저에서 특정한 URL을 호출하면 요청과 응답이 하나의 쌍으로 묶여서 처리됨
  - Request Header / Response Header라는 항목이 같이 보여지게 처리됨

#### 비연결성(Connectionless / Stateless)
웹은 하나의 요청과 응답을 처리한 후 연결을 종료함. 새로고침을 해야 갱신된 페이지를 볼 수 있던 이유.ㅇ

### 자바 서버 사이드 프로그래밍
- 서버 쪽에서 데이터를 처리할 수 있도록 구성하는 것을 의미하는데, 다음을 고려해야함
  - 동시에 여러 요청 발생 시 처리 방법
  - 서버에서 문제가 생기면 이를 어떻게 처리해야하는가
  - 어떤 방법으로 데이터 전송을 최적화할 수 있을까
  - 분산 환경이나 분산 처리와 같은 문제들은

자바의 경우 JavaEE라는 기술 스펙으로 정리해두었고, Servlet / JSP는 여러 기술 중에 하나임

#### Servlet 기술
- 쉽게 말해서 서버에서 동적으로 요청과 응답을 처리할 수 있는 API들을 정의한 것
- JSP는 근복적으로 Servlet과 같은 기술이지만, 서블릿으로 코드를 이용한 처리, JSP로는 화면 개발과 같이 역할을 분담해서 개발하는 것이 일반적임
- 톰캣과 같이 Servlet Container에서 Servlet을 실행 가능
  - 객체를 생성하거나 호출하는 주체는 사용자가 아닌 서블릿 컨테이너가 하게 됨
  - 서블릿 클래스에서 생성하는 관리 자체가 서블릿 컨테이너에 의해서 관리됨
  - 서블릿 / JSP의 코드 개발은 기본적인 자바 API와 더불어 서블릿 API도 같이 사용해야함

#### JSP 기술
- Java Server Pages의 약자로 서블릿 기술과 동일하게 서버에서 동적으로 데이터를 구성하는 기술
- 서블릿이 있는데도 불구하고 동일한 목적으로 JSP가 제공되는 이유는 목적 자체가 조금 다름
- 핵심은 JSP도 컴파일해서 .class파일로 만들어짐 => Servlet으로 만든거나 다름없음

#### Servlet vs JSP
- Servlet은 자바 코드 안에 HTML을 추가하는 방식
- JSP는 HTML안에 자바 코드를 추가하는 방식

### JSP를 이용해서 GET/POST 처리하기

#### GET 방식은 입력과 조회
```html
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form>
        <input type="number" name="num1">
        <input type="number" name="num1">
        <button type="submit">SEND</button>
    </form>
</body>
</html>
```
- SEND를 누르게 되면
  - http://localhost:8080/calc/input.jsp?num1=&num1=
  - 쿼리 스트링으로 날아가는 것을 볼 수 있음

#### POST 방식은 처리를 위한 용도
```html
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
</head>
<body>
<form action="calcResult.jsp" method="post">
  <input type="number" name="num1">
  <input type="number" name="num2">
  <button type="submit">SEND</button>
</form>
</body>
</html>
```
- action: calcResult.jsp로 전송 (이때 전송되는 url은 현재 경로 기준으로 보냄)
- method: 이때 전송방식은 post로 변경 (default: get)

#### GET vs POST
- 조회 | 등록/수정/삭제
- URL 뒤의 ?와 쿼리 스트링 혹은 Path Variable | URL 전달 후 HTTP Body에 **쿼리 스트링**

#### calcResult.jsp로 데이터 받기
```html
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>NUM1 ${param.num1}</h1>
    <h1>NUM2 ${param.num2}</h1>
</body>
</html>
```
- ${}: EL(Expression Language)
- name 속성으로 전송된 데이터를 param이라는 지정된 객체를 이용해서 추출할 수 있음

#### 웹의 파라미터는 모두 문자열
- 웹의 파라미터로 입력되는 모든 데이터는 문자열
- 따라서 숫자나 특정 값으로 받고 싶으면 Parsing을 해야함

#### 올바른 JSP 사용법
- JSP에서 쿼리 스트링이나 파라미터를 처리하지 않는다 - JSP 대신 서블릿을 통해서 처리
- JSP는 입력 화면을 구성하거나 처리 결과를 보여주는 용도로만 사용한다
- 브라우저는 직접 JSP 경로를 호출하지 않고 서블릿 경로를 통해서 JSP를 보는 방식으로 사용
이러한 문제를 해결하기위해 나온 방식 **Web MVC**

---
