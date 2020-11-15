package szfm.errorbynight.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import szfm.errorbynight.model.User;
import szfm.errorbynight.services.UserService;
import szfm.errorbynight.util.AttributeNames;
import szfm.errorbynight.util.Mappings;
import szfm.errorbynight.util.ViewNames;
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

    @GetMapping("")
    public String registration(Model model, @ModelAttribute(AttributeNames.REG_SUCCESS) String registrationSuccess) {
        model.addAttribute(AttributeNames.REG_FORM, new RegistrationForm());
        return ViewNames.REGISTRATION;
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

    @GetMapping(Mappings.ACTIVATION + "/{code}")
    public String activation(@PathVariable("code") String code, RedirectAttributes redirectAttributes) {
        boolean result = userService.activateUser(code);
        redirectAttributes.addFlashAttribute(AttributeNames.ACTIVATION_RESULT, result);
        return Mappings.REDIRECT_LOGIN;
    }
}
