package ys_band.develop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ys_band.develop.dto.youtube.YoutubeGetDTO;
import ys_band.develop.dto.youtube.YoutubePostDTO;
import ys_band.develop.service.YoutubeLinkService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/dailband/boards")
public class YoutubeLinkController {

    private final YoutubeLinkService youtubeLinkService;

    @Autowired
    public YoutubeLinkController(YoutubeLinkService youtubeLinkService) {
        this.youtubeLinkService = youtubeLinkService;
    }

    @PostMapping("/{board_id}/youtube")
    public ResponseEntity<YoutubePostDTO> addYoutubeLink(@PathVariable("board_id") Long boardId,
                                                         @RequestBody YoutubeGetDTO youtubeGetDTO) {
        YoutubePostDTO responseDTO = youtubeLinkService.addYoutubeLink(boardId, youtubeGetDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{board_id}/{youtube_id}")
    public ResponseEntity<Map<String, Object>> deleteYoutubeLink(@PathVariable("board_id") Long boardId,
                                                                 @PathVariable("youtube_id") Long youtubeId) {
        youtubeLinkService.deleteYoutubeLink(youtubeId);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "YouTube link deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
