package ua.kpi.nc.controller.student;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.kpi.nc.persistence.dto.UserDto;
import ua.kpi.nc.persistence.model.EmailTemplate;
import ua.kpi.nc.persistence.model.Role;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.persistence.model.enums.RoleEnum;
import ua.kpi.nc.persistence.model.impl.real.UserImpl;
import ua.kpi.nc.service.EmailTemplateService;
import ua.kpi.nc.service.RoleService;
import ua.kpi.nc.service.ServiceFactory;
import ua.kpi.nc.service.UserService;
import ua.kpi.nc.service.util.PasswordEncoderGeneratorService;
import ua.kpi.nc.service.util.SenderService;
import ua.kpi.nc.service.util.SenderServiceImpl;

import javax.mail.MessagingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/registrationStudent")
public class RegistrationController {

    private UserService userService;
    private RoleService roleService;
    private EmailTemplateService emailTemplateService;
    private SenderService senderService;
    private PasswordEncoderGeneratorService passwordEncoderGeneratorService;


    public RegistrationController() {
        userService = ServiceFactory.getUserService();
        roleService = ServiceFactory.getRoleService();
        emailTemplateService = ServiceFactory.getEmailTemplateService();
        senderService = SenderServiceImpl.getInstance();
        passwordEncoderGeneratorService = PasswordEncoderGeneratorService.getInstance();

    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UserDto> registerNewStudent(@RequestBody UserDto user) throws MessagingException {
        if (userService.isExist(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } else {
            Role role = roleService.getRoleByTitle(RoleEnum.valueOf(RoleEnum.ROLE_STUDENT));
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            String token = RandomStringUtils.randomAlphabetic(50);
            userService.insertUser(
                    new UserImpl(
                            user.getEmail(),
                            user.getFirstName(),
                            user.getSecondName(),
                            user.getLastName(),
                            passwordEncoderGeneratorService.encode(user.getPassword()),
                            false,
                            new Timestamp(System.currentTimeMillis()),
                            token
                    ),
                    new ArrayList<>(roles));
            String url = "http://91.207.104.206:8020/registrationStudent/" + token;

            EmailTemplate emailTemplate = emailTemplateService.getById(2L);

            String text = emailTemplate.getTitle() +
                    url + " " + emailTemplate.getText();
            String subject = "Please confirm your account NC KPI";

            senderService.send(user.getEmail(), subject, text);

            return ResponseEntity.ok(new UserDto(user.getEmail(), user.getFirstName()));
        }
    }

    @RequestMapping(value = "{token}", method = RequestMethod.GET)
    public ResponseEntity<String> registrationConfirm(@PathVariable("token") String token) {
        User user = userService.getUserByToken(token);
        if (null == user){
            //TODO error page. URL has been expired
            return ResponseEntity.status(410).body("URL expired");
        }
        user.setActive(true);
        userService.updateUser(user);
        //TODO redirect on some URL
        return ResponseEntity.ok("");
    }
}
