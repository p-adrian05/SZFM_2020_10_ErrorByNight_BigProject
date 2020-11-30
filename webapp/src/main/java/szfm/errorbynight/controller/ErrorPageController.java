package szfm.errorbynight.controller;


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.*;

import szfm.errorbynight.util.Mappings;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;


@RestController
public class ErrorPageController implements ErrorController {

    @GetMapping(Mappings.ERROR)
    public String handleError(HttpServletRequest request){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        return status != null ? "error "+status.toString() : "";
    }
    @Override
    public String getErrorPath() {
        return null;
    }

}
