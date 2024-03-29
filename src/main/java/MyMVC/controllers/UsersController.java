package MyMVC.controllers;

import MyMVC.model.Role;
import MyMVC.model.User;
import MyMVC.service.RoleService;
import MyMVC.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Controller

public class UsersController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UsersController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String index (Model model){
        model.addAttribute("users", userService.listUsers());
        return "admin/index";
    }

    @GetMapping ("/admin/new")
    public String newUser (Model model){
        model.addAttribute("user", new User());
        model.addAttribute("roles",roleService.listRole());
        return "admin/new";
    }

    @PostMapping("/admin")
    public String create (@ModelAttribute("user") User user, @RequestParam(value = "checkBoxRoles") String [] checkBoxRoles){
        Set<Role> roles = new HashSet<>();

        for (String role: checkBoxRoles
             ) {
            roles.add(roleService.getRoleByName(role));
        }

        user.setRoles(roles);
        userService.add(user);

        return "redirect:admin/index";
    }

    @GetMapping("user/")
    public String show (@AuthenticationPrincipal User user,  Model model){
        model.addAttribute("user", user);
        model.addAttribute("roles",user.getRoles());
        return "user/show";
    }

    @GetMapping("admin/{id}")
    public String showSomebody (@PathVariable ("id") long id, Model model){
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        model.addAttribute("roles",user.getRoles());
        return "user/show";
    }

    @GetMapping("admin/{id}/edit")
    public String edit (@PathVariable("id") long id, Model model){
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        model.addAttribute("roles",roleService.listRole());
        return "admin/edit";
    }

    @PatchMapping ("admin/{id}")
    public String update (@ModelAttribute("user") User user, @RequestParam(value = "checkBoxRoles") String [] checkBoxRoles){
        Set<Role> roles = new HashSet<>();

        for (String role: checkBoxRoles
             ) {
            roles.add(roleService.getRoleByName(role));
        }

        user.setRoles(roles);
        userService.update(user);
        return "redirect:/admin/index";
    }

    @DeleteMapping ("admin/{id}/remove")
    public String remove (@PathVariable("id") long id){
        userService.remove(id);
        return "redirect:/admin/index";
    }
}
