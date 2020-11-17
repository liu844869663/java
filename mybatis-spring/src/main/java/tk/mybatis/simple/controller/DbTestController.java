package tk.mybatis.simple.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import tk.mybatis.simple.mapper.DbTestMapper;
import tk.mybatis.simple.model.DbTest;

@Controller
@RequestMapping(value = "/test")
public class DbTestController {

    private static final Logger logger = LogManager.getLogger(DbTestController.class);

    @Autowired
    private DbTestMapper dbTestMapper;

    @GetMapping("/")
    public String index() {
        return "welcome";
    }

    @GetMapping("/query")
    public ModelAndView query(@RequestParam("id") Integer id) {
        DbTest dbTest = dbTestMapper.queryById(id);
        logger.info("入参：{}，查询结果：{}", id, dbTest.toString());
        ModelAndView mv = new ModelAndView();
        mv.setViewName("result");
        mv.addObject("test", dbTest);
        return mv;
    }
}
