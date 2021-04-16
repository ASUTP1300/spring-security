package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import web.model.Role;
import web.model.User;
import web.repository.RoleRepository;
import web.repository.UserRepository;
import web.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("users",userService.listUsers());
        return "admin/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {

        //Получить одного человека по id
        model.addAttribute("user", userService.getById(id));
        return "admin/getById";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user ){
        return "admin/new";
    }

    @PostMapping
    public String create(@ModelAttribute("user") User user,
                         @ModelAttribute("roles") String[] stringRole){

            System.out.println(stringRole[0]);


        user.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));
        userService.add(user);
        return "redirect:/admin";
    }

   //@GetMapping("/{id}/edit")
   //public String edit(Model model, @PathVariable("id") long id){
   //    User user = userService.getById(id);
   //    model.addAttribute("user", userService.getById(id) );
   //    return "admin/edit";
   //}


    @GetMapping("/{id}/edit")
    public ModelAndView edit(Model model, @PathVariable("id") long id){
        User user = userService.getById(id);
        ModelAndView modelAndView = new ModelAndView("admin/edit");
        modelAndView.addObject("user", user);

        List<Role> allRoles = roleRepository.findAll();
        String[] strings = {"ROLE_ADMIN", "ROLE_USER"};
        List<String>  list = new ArrayList<>();
        modelAndView.addObject ( "allRoles", allRoles );
        modelAndView.addObject("user_roles", user.getRoles());
        modelAndView.addObject ("rlz",strings);
        modelAndView.addObject ("confirm",list);
        return modelAndView;
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("user") User user,
                         @ModelAttribute()
                          @PathVariable("id") long id,
                         @ModelAttribute("confirm") String roles){
            System.out.println(roles);

        user.setId(id);
        userService.update(user);

        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id){
        userService.remove(id);
        return "redirect:/admin";
    }

}
