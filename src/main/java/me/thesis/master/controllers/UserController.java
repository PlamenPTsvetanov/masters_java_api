package me.thesis.master.controllers;

import me.thesis.master.common.controllers.BaseController;
import me.thesis.master.models.views.apiKey.ApiKeyOutView;
import me.thesis.master.models.views.user.UserInView;
import me.thesis.master.models.views.user.UserInitialCreationOutView;
import me.thesis.master.models.views.user.UserOutView;
import me.thesis.master.services.ApiKeyService;
import me.thesis.master.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController {
    private final UserService userService;
    private final ApiKeyService apiKeyService;

    public UserController(UserService userService, ApiKeyService apiKeyService) {
        this.userService = userService;
        this.apiKeyService = apiKeyService;
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
}
