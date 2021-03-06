package com.bmstu.testingsystem.controller

import com.bmstu.testingsystem.repositiry.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class Index {


    @GetMapping("/")
    fun index(authentication: Authentication?): String {
        return if (authentication == null)
            "redirect:/sign_in"
        else
            "redirect:/main_page"
    }
}