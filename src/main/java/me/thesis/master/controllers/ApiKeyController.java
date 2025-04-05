package me.thesis.master.controllers;

import me.thesis.master.common.controllers.BaseController;
import me.thesis.master.models.views.apiKey.ApiKeyOutView;
import me.thesis.master.services.ApiKeyService;
import me.thesis.master.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api-keys")
public class ApiKeyController extends BaseController {
    private final ApiKeyService apiKeyService;
    private final UserService userService;

    public ApiKeyController(ApiKeyService apiKeyService, UserService userService) {
        this.apiKeyService = apiKeyService;
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<ApiKeyOutView> getAll(@RequestHeader("User-Id") UUID userId,
                                      @RequestParam(value = "validTill", required = false) Instant instant) {
        userService.validateUser(userId);
        return this.apiKeyService.getAllForUser(userId, false, instant);
    }

    @GetMapping("/active")
    public List<ApiKeyOutView> getAllActive(@RequestHeader("User-Id") UUID userId,
                                            @RequestParam(value = "validTill", required = false) Instant instant) {
        return this.apiKeyService.getAllForUser(userId, true, instant);
    }

    @PostMapping("/generate")
    public ApiKeyOutView generate(@RequestHeader("User-Id") UUID userId) {
        userService.validateUser(userId);
        return this.apiKeyService.generateApiKey(userId);
    }

    @DeleteMapping("/disable/{keyValue}")
    public ApiKeyOutView disable(@RequestHeader("User-Id") UUID userId, @PathVariable String keyValue) {
        userService.validateUser(userId);
        apiKeyService.checkApiKeyValidity(userId, keyValue);
        return this.apiKeyService.disableApiKey(userId, keyValue);
    }
}
