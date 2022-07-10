package com.aladin.project.Controller;

import com.aladin.project.CommonUtils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Value("${aes.key}")
    private String key; /*복호화 키 값*/
    @Value("${aes.iv}")
    private String iv; /*복호화 벡터 값*/
    @Value("${aes.alg}")
    private String alg; /*복호화 방식*/

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
        CommonUtils commonUtils = new CommonUtils(); //공통모듈
        String id = request.getParameter("id");
        String pwd = request.getParameter("password");

        //패스워드 암호화
        String encPwd = commonUtils.encrypt(key, iv, alg, pwd);

        System.out.println("test ::: " + encPwd);
        return null;
    }
}
