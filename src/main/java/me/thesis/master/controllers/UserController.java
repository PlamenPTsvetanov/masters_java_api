package me.thesis.master.controllers;

import me.thesis.master.common.controllers.BaseController;
import me.thesis.master.models.views.user.UserInView;
import me.thesis.master.models.views.user.UserOutView;
import me.thesis.master.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/ok")
    public String testOk() {
        return "Ok";
    }

    @PostMapping(path = "")
    public UserOutView postUser(@RequestBody final UserInView userInView) {
        return this.userService.postOne(userInView);
    }

    @PutMapping(path = "/{id}")
    public UserOutView putUser(@PathVariable("id") UUID id, @RequestBody final UserInView userInView) {
        return this.userService.putOne(userInView, id);
    }

    @DeleteMapping(path = "/{id}")
    public UserOutView deleteUser(@PathVariable("id") UUID id) {
        return this.userService.deleteOne(id);
    }

    @GetMapping(path = "/{id}")
    public UserOutView getUser(@PathVariable("id") UUID id) {
        return this.userService.getOne(id);
    }
}
