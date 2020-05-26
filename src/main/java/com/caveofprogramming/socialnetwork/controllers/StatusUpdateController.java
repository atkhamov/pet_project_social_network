package com.caveofprogramming.socialnetwork.controllers;

import com.caveofprogramming.socialnetwork.model.StatusUpdate;
import com.caveofprogramming.socialnetwork.service.StatusUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class StatusUpdateController {

    @Autowired
    private StatusUpdateService statusUpdateService;

    @RequestMapping(value = "/editstatus", method = RequestMethod.GET)
    ModelAndView editStatus(ModelAndView modelAndView, @RequestParam(name = "id") Long id){
        Optional<StatusUpdate> statusUpdate = statusUpdateService.get(id);
        modelAndView.getModel().put("statusUpdate", statusUpdate);
        modelAndView.setViewName("app.editStatus");
        return modelAndView;
    }

    @RequestMapping(value = "/editstatus", method = RequestMethod.POST)
    ModelAndView editStatus(ModelAndView modelAndView, @Valid StatusUpdate statusUpdate, BindingResult result){

        modelAndView.setViewName("app.editStatus");
        if(!result.hasErrors()){
            statusUpdateService.save(statusUpdate);
            modelAndView.setViewName("redirect:/viewstatus");
        }
        return modelAndView;
    }


    @RequestMapping(value = "/deletestatus", method = RequestMethod.GET)
    ModelAndView deleteStatus(ModelAndView modelAndView, @RequestParam(name = "id") Long id){
        statusUpdateService.delete(id);
        modelAndView.setViewName("redirect:/viewstatus");

        return modelAndView;
    }


    /** 'p' argument in the 'viewStatus()' method stands for 'page'
     *  'defaultValue' stands for the page which should be displayed by default*/
    @RequestMapping(value = "/viewstatus", method = RequestMethod.GET)
    ModelAndView viewStatus(ModelAndView modelAndView, @RequestParam(name = "p", defaultValue = "1") int pageNumber){
        Page<StatusUpdate> page = statusUpdateService.getPage(pageNumber);
        modelAndView.getModel().put("page", page);
        modelAndView.setViewName("app.viewStatus");
        return modelAndView;
    }

    /**With the help of the method below we can return modelAndView
     * and then return some values.
     *
     * With the help conventional method with the type of String
     * we could NOT return some values*/
    @RequestMapping(value = "/addstatus", method = RequestMethod.GET)
    ModelAndView addStatus(ModelAndView modelAndView, @ModelAttribute("statusUpdate") StatusUpdate statusUpdate){
        modelAndView.setViewName("app.addStatus");

        StatusUpdate latestStatusUpdate = statusUpdateService.getLatest();
        modelAndView.getModel().put("latestStatusUpdate", latestStatusUpdate);
        return modelAndView;
    }

    //'@Valid' annotation helps us to validate the text typed into the text area
    //and if the validation is successful, then the value will be transformed to
    //'result' with is 'BindingResult'
    @RequestMapping(value = "/addstatus", method = RequestMethod.POST)
    ModelAndView addStatus(ModelAndView modelAndView, @Valid StatusUpdate statusUpdate, BindingResult result){
        modelAndView.setViewName("app.addStatus");

        if(!result.hasErrors()){
            statusUpdateService.save(statusUpdate);
            modelAndView.getModel().put("statusUpdate", new StatusUpdate());
            modelAndView.setViewName("redirect:/viewstatus");
        }

        //The following 2 lines of code will help us to retrieve the data from DB
        //and display on out page
        StatusUpdate latestStatusUpdate = statusUpdateService.getLatest();
        modelAndView.getModel().put("latestStatusUpdate", latestStatusUpdate);

        return modelAndView;
    }
}
