package me.thesis.master.controllers;

import me.thesis.master.common.controllers.BaseController;
import me.thesis.master.models.views.video.VideoInView;
import me.thesis.master.models.views.video.VideoOutView;
import me.thesis.master.services.ApiKeyService;
import me.thesis.master.services.UserService;
import me.thesis.master.services.VideoService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/videos")
public class VideoController extends BaseController {
    private final VideoService videoService;
    private final UserService userService;
    private final ApiKeyService apiKeyService;

    public VideoController(VideoService videoService, UserService userService, ApiKeyService apiKeyService) {
        this.videoService = videoService;
        this.userService = userService;
        this.apiKeyService = apiKeyService;
    }

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public VideoOutView generate(@RequestHeader("User-Id") UUID userId, @RequestHeader("Api-Key") String apiKey, @RequestPart VideoInView videoIn, @RequestPart MultipartFile videoFile) {
        userService.validateUser(userId);
        apiKeyService.checkApiKeyValidity(userId, apiKey);
        return this.videoService.saveVideo(userId, videoIn, videoFile);
    }

    @DeleteMapping(value = "/remove/{videoId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public VideoOutView remove(@RequestHeader("User-Id") UUID userId, @RequestHeader("Api-Key") String apiKey, @PathVariable UUID videoId) {
        userService.validateUser(userId);
        apiKeyService.checkApiKeyValidity(userId, apiKey);
        return this.videoService.deleteOne(videoId);
    }

    @GetMapping(value = "/all", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<VideoOutView> allVideos(@RequestHeader("User-Id") UUID userId,
                                        @RequestHeader("Api-Key") String apiKey,
                                        @RequestParam("size") int size,
                                        @RequestParam("offset") int offset) {
        userService.validateUser(userId);
        apiKeyService.checkApiKeyValidity(userId, apiKey);
        return this.videoService.getAllVideosFor(userId, size, offset);
    }
}
