package ys_band.develop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ys_band.develop.domain.Song;
import ys_band.develop.service.AudioConversionService;
import ys_band.develop.service.PitchService;
import ys_band.develop.service.RecommendationService;
import ys_band.develop.service.SpotifyService;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dailband")
public class SongController {

    @Autowired
    private PitchService pitchService;

    @Autowired
    private SpotifyService spotifyService;

    @Autowired
    private AudioConversionService audioConversionService;

    @Autowired
    private RecommendationService recommendationService;

    @PostMapping("/song")
    public ResponseEntity<?> analyzePitch(@RequestParam("file") MultipartFile file) {
        try {
            File tempFile = Files.createTempFile("audio", ".tmp").toFile();
            file.transferTo(tempFile);

            File wavFile = audioConversionService.convertToWav(tempFile);

            List<Float> pitches = pitchService.extractPitches(wavFile.getAbsolutePath());
            float[] minMaxPitches = pitchService.getMinMaxPitches(pitches);
            String range = pitchService.getRangeFromPitches(minMaxPitches[0], minMaxPitches[1]);


            List<Song> recommendedSongs = recommendationService.recommendSongs(range);


            List<Map<String, String>> trackDetails = spotifyService.getTracksDetails(recommendedSongs);

            return ResponseEntity.ok(trackDetails);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }
}
