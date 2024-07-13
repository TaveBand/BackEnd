package ys_band.develop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ys_band.develop.domain.Song;
import ys_band.develop.repository.SongRepository;

import java.util.List;

@Service
public class RecommendationService {

    @Autowired
    private SongRepository SongRepository;

    public List<Song> recommendSongs(String range) {
        return SongRepository.findByVocalRange(range);
    }
}
