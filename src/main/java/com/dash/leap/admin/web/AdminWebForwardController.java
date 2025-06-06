package com.dash.leap.admin.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminWebForwardController {

    @GetMapping(value = { "/leap/admin/**" })
    public String forwardAdminWeb() {
        return "forward:/leap/index.html";
    }
}
