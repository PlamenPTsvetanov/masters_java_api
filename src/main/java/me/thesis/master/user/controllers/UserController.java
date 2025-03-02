package me.thesis.master.user.controllers;

import lombok.Getter;
import me.thesis.master.common.controllers.BaseController;
import me.thesis.master.user.models.UserInView;
import me.thesis.master.user.models.UserOutView;
import me.thesis.master.user.services.UserService;
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
