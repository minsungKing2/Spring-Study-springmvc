package hello.servlet.web.frontcontroller.v1;

import hello.servlet.web.frontcontroller.v1.controller.MemberFormControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberListControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberSaveControllerV1;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//*이 중요. v1/하위가 뭐가 들어와도 무조건 이 서블릿이 호출되도록 함.
@WebServlet(name = "frontControllerServletV1", urlPatterns = "/front-controller/v1/*")
public class FrontControllerServletV1 extends HttpServlet {

    private Map<String, ControllerV1> controllerMap = new HashMap<>(); //key:URL -> key에 해당하는 values의 값을 반환

    public FrontControllerServletV1() {
        controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV1.service"); //sout보단 로고로 찍는게 좋음.

        // /front-controller/v1/members
        String requestURI = request.getRequestURI(); //URL 정보를 받아옴.

        // /front-controller/v1/members -> MemberListControllerV1()
        ControllerV1 controller = controllerMap.get(requestURI);

        //예외처리
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND); //NOT FOUND 404로 정의 된 상수
            return;
        }

        controller.process(request, response);
    }
}
