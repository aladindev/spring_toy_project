package com.aladin.project.Controller;

import com.aladin.project.CommonUtils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired(required = false)
    CommonUtils commonUtils;

    @GetMapping("/")
    public ModelAndView login() {
        ModelAndView mv = new ModelAndView("login");

        return mv;
    }

    @GetMapping("/join")
    public ModelAndView join() {
        ModelAndView mv = new ModelAndView("join");
        return mv;
    }

    @PostMapping("/join") /* 회원가입 */
    public ModelAndView userInfoInsert(HttpServletRequest request) throws Exception {
        String id = request.getParameter("id");
        String pwd = request.getParameter("password");
        System.out.println(id + " / " + pwd);
        String encPwd = commonUtils.encrypt(pwd);
        System.out.println(encPwd);
        return null;
    }
}
