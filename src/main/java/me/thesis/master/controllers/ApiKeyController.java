package me.thesis.master.controllers;

import me.thesis.master.common.controllers.BaseController;
import me.thesis.master.models.views.apiKey.ApiKeyOutView;
import me.thesis.master.services.ApiKeyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api-keys")
public class ApiKeyController extends BaseController {
    private final ApiKeyService apiKeyService;

    public ApiKeyController(ApiKeyService apiKeyService) {
        this.apiKeyService = apiKeyService;
    }

    @GetMapping("/all")
    public List<ApiKeyOutView> getAll(@RequestHeader("User-Id") UUID userId) {
        return this.apiKeyService.getAllForUser(userId, false);
    }

    @GetMapping("/active")
    public List<ApiKeyOutView> getAllActive(@RequestHeader("User-Id") UUID userId) {
        return this.apiKeyService.getAllForUser(userId, true);
    }

    @PostMapping("/generate")
    public ApiKeyOutView generate(@RequestHeader("User-Id") UUID userId) {
        return this.apiKeyService.generateApiKey(userId);
    }

    @DeleteMapping("/disable/{keyId}")
    public ApiKeyOutView disable(@RequestHeader("User-Id") UUID userId, @PathVariable UUID keyId) {
        return this.apiKeyService.disableApiKey(userId, keyId);
    }
}
