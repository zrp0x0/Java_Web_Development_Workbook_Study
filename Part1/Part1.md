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

## Web MVC 방식

### MVC 구조와 서블릿/JSP
서블릿 코드의 경우 자바 코드를 이용할 수 있고, 상속이나 인터페이스의 처리도 가능함. HTTP로 전달된 메시지를 구성하는 HTML을 처리할 때 상당히 많은 양의 코드를 작성해야함. JSP의 경우 HTML 코드를 바로 사용할 수 있으므로 HTTP 메세지 작성에는 적합하지만, 자바 코드를 재사용하는 문제나 자바 코드와 HTML이 혼재하는 것과 같은 여러 문제가 존재함
따라서 다음과 같이 처리함
- Request => Servlet
  - 응답에 필요한 데이터 완성
  - 다른 객체들 연동 협업 처리
  - 상속이나 인터페이스의 활용
  - 코드의 재사용

- Servlet => JSP
  - EL을 이용해서 데이터 출력
  - HTML 코드 활용
  - 브라우저로 전송할 최종 결과 완성

- JSP => Response

#### MVC
- Model - View - Controller
  - Controller: 요청을 받는 부분
  - View: 응답을 보여주는 부분
  - Model 데이터를 처리하는 부분

### MVC 구조로 다시 설계하는 계산기
- 브라우저의 호출은 반드시 컨트롤러 역할을 하는 서블릿을 호출하도록 구성한다
- JSP는 브라우저에서 직접 호출하지 않도록 하고 Controller를 통해서만 JSP에 접근하도록 구성한다

### 컨르롤러에서 뷰 호출
```java
package com.zerock.w1.calc;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "inputController", urlPatterns = "/calc/input")
public class InputController extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {
        System.out.println("InputController...doGet...");

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/calc/input.jsp");

        dispatcher.forward(req, resp);
    }
}
```
- RequestDispatcher를 이용한 요청 배포
  - 서블릿에 전달된 요청을 다른 쪽으로 전달 혹은 배포하는 역할을 하는 객체
  - WEB-INF는 브라우저에서 직접 접근이 불가능한 경로로 상당히 특별한 경로 (브라우저에서 직접 호출 불가능)

### Post 방식을 통한 처리 요청
```java
package com.zerock.w1.calc;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "calcController", urlPatterns = "/calc/makeResult")
public class CalcController extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {

        String num1 = req.getParameter("num1");
        String num2 = req.getParameter("num2");

        System.out.printf(" num1: %s", num1);
        System.out.printf(" num2: %s", num2);
    }
}
```
- doPost(): 브라우저에서 POST 방식으로 호출하는 경우에만 호출이 가능함
- req.getParameter(): 쿼리 스트링으로 전달되는 파라미터를 문자열로 받을 수 있음

```html
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="/calc/makeResult" method="post">
        <input type="number" name="num1">
        <input type="number" name="num2">
        <button type="submit">SEND</button>
    </form>
</body>
</html>
```
- /calc/makeResult로 경로를 수정해주어야함

### sendRedirect()
POST 방식 처리는 가능하면 빨리 다른 페이지를 보도록 브라우저 화면을 이동시키는 것이 좋음
현재 CalcController는 보여줄 결과를 만들어주지 않아, SEND를 계속 호출할 수 있게 됨
이때 사용하는것이 HttpServletResponse의 sendRedirect()
```java
package com.zerock.w1.calc;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "calcController", urlPatterns = "/calc/makeResult")
public class CalcController extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {

        String num1 = req.getParameter("num1");
        String num2 = req.getParameter("num2");

        System.out.printf(" num1: %s", num1);
        System.out.printf(" num2: %s", num2);

        resp.sendRedirect("/index");
    }
}
```
- resp.sendRedirect("/index): /index라는 경로로 GET 호출을 수행
- 현재는 /index에 해당하는 컨트롤러가 존재하지 않기 때문에 404에러가 발생

### PRG 패턴 (POST - Redirect - GET)
- 사용자는 컨트롤러에서 원하는 작업을 POST 방식으로 처리하기를 요청
- POST 방식을 컨트롤러에서 처리하고 브라우저는 다른 경로로 이동(GET)하라는 응답(Redirect)
- 브라우저는 GET 방식으로 이동

---

## HttpServlet
서블릿을 구현할 때 HttpServlet을 상속해서 구현함
- HttpServlet은 GET/POST에 맞게 doGet()/doPost()등을 제공하여 개발자가 상황에 맞게 메소드를 오버라이드하여 GET/POS 방식 처리를 나눠서 할 수 있음
- HttpServlet을 상속받은 클래스 객체는 톰캣과 같은 WAS의 내부에서 자동으로 객체를 생성하고 관리하기 때문에 개발자가 객체 관리를 신경 쓸 필요가 없음
- HttpServlet은 멀티 스레드에 의해서 동시에 실행될 수 있도록 처리되기 때문에 개발자는 동시에 많은 사용자를 어떻게 처리해야 하는지에 대한 고민을 줄일 수 있음

### HttpServlet 생명 주기
- 톰켓은 경로에 맞는 서블릿 클래스를 로딩하고 객체를 생성함. 이때 init()이 딱 한 번 호출됨
- 생성된 서블릿 객체는 요청을 분석해서 GET/POST에 의해서 doGet()/doPost()를 호출함
- 톰켓이 종료될 때 서블릿의 destroy() 메소드를 실행함

### HttpServletRequest의 주요 기능

#### getParameter()
- 키를 통해서 value를 얻을 수 잇음
- 만약 해당 키가 없으면 null을 반환함
- 항상 문자열로 처리되기 때문에 주의해야함

#### getParameterValues()
- getParameter()와 유사하게 동일한 이름의 파라미터가 여러 개 있는 경우에 사용함
- name이라는 파라미터가 여러 개 존재한다면, getParameterValues()를 이용해서 String[] 타입으로 변환됨

#### setAttribute()
- JSP로 전달할 데이터를 추가할 때 사용함
- 키와 값의 형태로 데이터를 저장할 수 있음

#### RequestDispatcher
- getRequestDispatcher()를 이용해서 얻을 수 있음
- 현재의 요청을 다른 서버의 자원(서블릿 혹은 JSP)에게 전달하는 용도로 사용함
  - forward(): 현재까지 모든 응답 내용은 무시하고 JSP가 작성하는 내용만을 브라우저로 전달
  - include(): 지금까지 만들어진 응답내용 + JSP가 만든 내용을 브라우저로 전달
  - 개발에서는 거의 forward()만 사용

### HttpServletResponse의 주요 기능
주로 HttpServletResponse는 JSP에서 주로 처리되기 때문에 서블릿 내에서 직접 사용되는 일은 많지 않고 주로 sendRedirect()를 이용하는 경우가 많음

#### sendRedirect()
- 브라우저에게 다른 곳으로 가라는 응답 메세지를 전달함
- 브라우저는 Location이 있는 응답을 받으면 화면을 처리하는 대신에 주소창에 지정된 주소로 이동하고, 다시 호출하게 됨

### TodoListController 구현
```java
package com.zerock.w1.todo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "todoListController", urlPatterns = "/todo/list")
public class TodoListController extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {

        System.out.println("/todo/list");

        req.getRequestDispatcher("/WEB-INF/todo/list.jsp").forward(req, resp);
    }
}
```

```html
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>List Page</h1>
</body>
</html>
```

### TodoRegisterController의 구현
```java
package com.zerock.w1.todo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "todoRegisterController", urlPatterns = "/todo/register")
public class TodoRegisterController extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {

        System.out.println("입력 화면을 볼 수 있도록 구성");

        req.getRequestDispatcher("/WEB-INF/todo/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {

        System.out.println("입력을 처리하고 목록 페이지로 이동");

        resp.sendRedirect("/todo/list");
    }
}
```

```html
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="/todo/register" method="post">
        <button type="submit">SEND</button>
    </form>
</body>
</html>
```

---

## 모델(Model)

### 모델과 3티어
모델은 컨트롤러에 필요한 기능이나 데이터를 처리해 주는 존재지만
시스템 전체로 보면 컨트롤러와 뷰를 제외한 남은 부분
보통 서비스 계층(로직 처리)와 영속 계층(데이터 처리)으로 구분함

### DTO(Data Transfer Object)
3티어로 분리하게 되면 반드시 계층이나 객체들 간에 데이터 교환이 이루어지게 됨
이 경우 대부분은 한 개 이상의 데이터를 전달할 때가 많기 때문에 여러 개의 데이터를 묶어서 하나의 객체로 전달하는 것을 
DTO라고 함
DTO는 여러 개의 데이터를 묶어서 필요한 곳에 전달하거나 호출을 결과로 받는 방식을 사용하기 때문에
대부분은 Java Beans 형태로 구성하는 경우가 많음
- 생성자가 없거나 반드시 파라미터가 없는 생성자 함수를 가지는 형태
- 속성은 private
- getter/setter를 제공할 것

이외에도 Serializable 인터페이스를 구현해야하는 등의 규칙이 존재하지만 최소한의 규칙은 앞의 3가지 규격을 지키면 됨

#### TodoDTO
```java
package com.zerock.w1.todo.dto;

import java.time.LocalDate;

public class TodoDTO {

    private Long tno;

    private String title;

    private LocalDate dueDate;

    private boolean finished;

    public Long getTno() {
        return tno;
    }

    public void setTno(Long tno) {
        this.tno = tno;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "TodoDTO{" +
                "tno=" + tno +
                ", title='" + title + '\'' +
                ", dueDate=" + dueDate +
                ", finished=" + finished +
                '}';
    }
}
```

### 서비스 객체
DTO는 주로 서비스 객체의 파라미터나 리턴 타입으로 사용됨
서비스 객체는 간단히 말하면 기능(로직)들의 묶음이라고 할 수 있음
서비스 객체는 프로그램이 구현해야하는 기능들의 실제 처리를 담당한다고 생각하면 됨

### TodoService 클래스
```java
package com.zerock.w1.todo.service;

import com.zerock.w1.todo.dto.TodoDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public enum TodoService {
    INSTANCE;

    public void register(TodoDTO todoDTO) {
        System.out.println("DEBUG.............." + todoDTO);
    }

    public List<TodoDTO> getList() {
        List<TodoDTO> todoDTOS = IntStream.range(0, 10).mapToObj(i -> {
            TodoDTO dto = new TodoDTO();
            dto.setTno((long)i);
            dto.setTitle("Todo.."+i);
            dto.setDueDate(LocalDate.now());

            return dto;
        }).collect(Collectors.toList());

        return todoDTOS;
    }
}
```
- INSTANCE: 싱글톤으로 구현할 수 있음
  - TodoService.INSATNACE로 바로 사용할 수 있음

### TodoListController의 처리
```java
package com.zerock.w1.todo;

import com.zerock.w1.todo.dto.TodoDTO;
import com.zerock.w1.todo.service.TodoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "todoListController", urlPatterns = "/todo/list")
public class TodoListController extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {

        System.out.println("/todo/list");

        List<TodoDTO> dtoList = TodoService.INSTANCE.getList();

        req.setAttribute("list", dtoList);

        req.getRequestDispatcher("/WEB-INF/todo/list.jsp").forward(req, resp);
    }
}
```

### JSP - EL(Expression Language)
JSP 코드에서 사용한 ${}는 EL 표현식임
- EL을 사용하면 자바 코드를 몰라도 getter/setter를 호출할 수 있음
```html
${list[0].tno} == ${list[0].getTno()}
```

### JSTL(JavaServer Pages Standard Tag Library)
반목문 / 조건문을 사용할 수 있음
- var: EL에서 사용될 변수 이름
- items: 컬렉션
- begin/end: 반복의 시작/끝

JSTL을 사용하기 위해서는 의존성 설정을 해주어야함
```java
implementation group: 'jstl', name: 'jstl', version: '1.2'
```

```html
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> // 이것도 붙여줘야 JSTL을 사용할 수 있음
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>List Page</h1>

    <ul>
        <c:forEach var="dto" items="${list}">
            <li>${dto}</li>
        </c:forEach>
    </ul>
</body>
</html>
```

### Todo 조회
```java
package com.zerock.w1.todo.service;

import com.zerock.w1.todo.dto.TodoDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public enum TodoService {
    INSTANCE;

    public void register(TodoDTO todoDTO) {
        System.out.println("DEBUG.............." + todoDTO);
    }

    public List<TodoDTO> getList() {
        List<TodoDTO> todoDTOS = IntStream.range(0, 10).mapToObj(i -> {
            TodoDTO dto = new TodoDTO();
            dto.setTno((long)i);
            dto.setTitle("Todo.."+i);
            dto.setDueDate(LocalDate.now());

            return dto;
        }).collect(Collectors.toList());

        return todoDTOS;
    }

    public TodoDTO get(Long tno) {
        TodoDTO dto = new TodoDTO();
        dto.setTno(tno);
        dto.setTitle("Sample Todo");
        dto.setDueDate(LocalDate.now());
        dto.setFinished(true);

        return dto;
    }
}
```

```java
package com.zerock.w1.todo;

import com.zerock.w1.todo.dto.TodoDTO;
import com.zerock.w1.todo.service.TodoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "todoReadController", urlPatterns = "/todo/read")
public class TodoReadController extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {

        System.out.println("/todo/read");

        Long tno = Long.parseLong(req.getParameter("tno"));

        TodoDTO dto = TodoService.INSTANCE.get(tno);

        req.setAttribute("dto", dto);

        req.getRequestDispatcher("/WEB-INF/todo/read.jsp").forward(req, resp);
    }

}
```

```html
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <div>${dto.tno}</div>
    <div>${dto.title}</div>
    <div>${dto.dueDate}</div>
    <div>${dto.finished}</div>
</body>
</html>
```