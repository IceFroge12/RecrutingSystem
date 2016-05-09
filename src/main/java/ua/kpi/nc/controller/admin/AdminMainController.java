package ua.kpi.nc.controller.admin;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ua.kpi.nc.controller.auth.UserAuthentication;
import ua.kpi.nc.persistence.model.Recruitment;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.service.RecruitmentService;
import ua.kpi.nc.service.ServiceFactory;

/**
 * Created by dima on 23.04.16.
 */
@RestController
@RequestMapping("/admin")
public class AdminMainController {

    private RecruitmentService recruitmentService = ServiceFactory.getRecruitmentService();

    @RequestMapping(value = "recruitment", method = RequestMethod.POST)
    public Recruitment getRecruitmentData() {
        Recruitment recruitment = recruitmentService.getCurrentRecruitmnet();
        System.out.println(recruitment.toString());
        return recruitment;
    }
}
