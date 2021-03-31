package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("users",userService.listUsers());
        return "users/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {

        //Получить одного человека по id
        model.addAttribute("user", userService.getById(id));
        return "users/getById";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user ){
        return "users/new";
    }

    @PostMapping
    public String create(@ModelAttribute("user") User user){
        userService.add(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id){
        model.addAttribute("user", userService.getById(id) );
        return "users/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") long id){
        user.setId(id);
        userService.update(user);
        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id){
        userService.remove(id);
        return "redirect:/users";
    }
}
