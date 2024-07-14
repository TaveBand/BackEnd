package ys_band.develop.service.Post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ys_band.develop.domain.Board;
import ys_band.develop.domain.User;
import ys_band.develop.domain.Youtube;
import ys_band.develop.dto.youtube.YoutubeGetDTO;
import ys_band.develop.dto.youtube.YoutubePostDTO;
import ys_band.develop.exception.UserException;
import ys_band.develop.repository.BoardRepository;
import ys_band.develop.repository.UserRepository;
import ys_band.develop.repository.YoutubeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class YoutubeLinkService {

    private final YoutubeRepository youtubeRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Autowired
    public YoutubeLinkService(YoutubeRepository youtubeRepository, UserRepository userRepository, BoardRepository boardRepository) {
        this.youtubeRepository = youtubeRepository;
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
    }

    public YoutubePostDTO addYoutubeLink(Long boardId, YoutubeGetDTO youtubeGetDTO) {
        if (youtubeGetDTO == null || youtubeGetDTO.getUserId() == null || youtubeGetDTO.getLink() == null || youtubeGetDTO.getTitle() == null) {
            throw new UserException("Invalid request data");
        }

        User user = userRepository.findById(youtubeGetDTO.getUserId())
                .orElseThrow(() -> new UserException("User not found"));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new UserException("Board not found"));

        Youtube youtube = new Youtube();
        youtube.setTitle(youtubeGetDTO.getTitle());
        youtube.setLink(youtubeGetDTO.getLink());
        youtube.setUser(user);
        youtube.setBoard(board);

        Youtube savedYoutube = youtubeRepository.save(youtube);

        YoutubePostDTO responseDTO = new YoutubePostDTO();
        responseDTO.setYoutubeId(savedYoutube.getYoutubeId());
        return responseDTO;
    }

    public void deleteYoutubeLink(Long youtubeId) {
        Youtube youtube = youtubeRepository.findById(youtubeId)
                .orElseThrow(() -> new UserException("YouTube link not found"));
        youtubeRepository.delete(youtube);
    }

    public List<YoutubeGetDTO> getAllYoutubeLinksByBoard(Long boardId) {
        return youtubeRepository.findAll().stream()
                .filter(youtube -> youtube.getBoard().getBoardId().equals(boardId))
                .map(youtube -> {
                    YoutubeGetDTO responseDTO = new YoutubeGetDTO();
                    responseDTO.setLink(youtube.getLink());
                    responseDTO.setTitle(youtube.getTitle());
                    responseDTO.setUserId(youtube.getUser().getUser_id());
                    return responseDTO;
                })
                .collect(Collectors.toList());
    }
}
