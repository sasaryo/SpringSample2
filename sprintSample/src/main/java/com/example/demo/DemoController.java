/**
 *
 */
package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 佐々木亮
 *
 */
@Controller
public class DemoController {
    @GetMapping({"/", "/index"})
    public String index() {
        return "index";
    }

    @PostMapping("/result")
    public ModelAndView result(@RequestParam("demoText")String demoText, ModelAndView mv) {

        mv.setViewName("result");
        mv.addObject("demoText", demoText);

        return mv;
    }

}
