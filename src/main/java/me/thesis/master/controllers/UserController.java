package me.thesis.master.controllers;

import me.thesis.master.common.controllers.BaseController;
import me.thesis.master.models.views.apiKey.ApiKeyOutView;
import me.thesis.master.models.views.user.UserInView;
import me.thesis.master.models.views.user.UserInitialCreationOutView;
import me.thesis.master.models.views.user.UserOutView;
import me.thesis.master.services.ApiKeyService;
import me.thesis.master.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController {
    private final UserService userService;
    private final ApiKeyService apiKeyService;

    public UserController(UserService userService, ApiKeyService apiKeyService) {
        this.userService = userService;
        this.apiKeyService = apiKeyService;
    }

    @GetMapping(path = "/ok")
    public String testOk() {
        return "Ok";
    }

    @PostMapping(path = "")
    public UserInitialCreationOutView postUser(@RequestBody final UserInView userInView) {
        UserOutView userOutView = this.userService.postOne(userInView);
        ApiKeyOutView apiKeyOutView = this.apiKeyService.generateApiKey(userOutView.getId());

        UserInitialCreationOutView out = new UserInitialCreationOutView();

        out.setId(userOutView.getId());
        out.setFirstName(userOutView.getFirstName());
        out.setLastName(userOutView.getLastName());
        out.setApiKey(apiKeyOutView);

        return out;
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
