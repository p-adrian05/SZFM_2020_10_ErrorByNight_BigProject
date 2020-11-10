package szfm.errorbynight.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.engine.AttributeNames;
import szfm.errorbynight.model.User;
import szfm.errorbynight.services.UserService;
import szfm.errorbynight.validation.RegistrationForm;

import javax.validation.Valid;
import java.sql.SQLException;

@Controller
@Slf4j
@RequestMapping(Mappings.REGISTRATION)
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("")
    public String registerUser(@Valid @ModelAttribute RegistrationForm form,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(AttributeNames.ERRORS, bindingResult.getAllErrors());
            return ViewNames.REGISTRATION;
        } else {
            try {

                userService.registerUser(new User(form.getUsername(), form.getEmail(), form.getPassword()));
            } catch (SQLException e) {
                model.addAttribute(AttributeNames.SQL_ERROR, e.getMessage());
                log.info(e.getMessage());
                return ViewNames.REGISTRATION;
            }
        }
        redirectAttributes.addFlashAttribute(AttributeNames.REG_SUCCESS, true);
        return Mappings.REDIRECT_REGISTRATION;
    }
}
