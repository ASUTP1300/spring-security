package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import web.model.Role;
import web.model.User;
import web.repository.RoleRepository;
import web.repository.UserRepository;
import web.service.RoleService;
import web.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;

    private RoleService roleService;

    private RoleRepository roleRepository;

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

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
        model.addAttribute("user", userService.getById(id));

        return "admin/getById";
    }

    @GetMapping("/new")
    public String newUser(Model model ){
        User user = new User();
        List<Role> listRoles =  roleRepository.findAll();

        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);
        return "admin/new";
    }

    @PostMapping
    public String create(@ModelAttribute("user") User user,
                         @RequestParam(required=false, name = "listRolesResponse") List<String> roles){
        user.setRoles(roleService.getRoleByName(roles));
        userService.add(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String edit( @PathVariable("id") long id, Model model){
        User user = userService.getById(id);
        model.addAttribute("user", user);
        List<Role> listRoles =  roleRepository.findAll();
        model.addAttribute("listRoles", listRoles);
        return "admin/edit";
    }

    @PostMapping("/{id}")
    public String update( @ModelAttribute("user") User user,
                          @RequestParam(required=false, name = "listRolesResponse") List<String> roles){
        user.setRoles(roleService.getRoleByName(roles));
        userService.update(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id){
        userService.remove(id);
        return "redirect:/admin";
    }
}
