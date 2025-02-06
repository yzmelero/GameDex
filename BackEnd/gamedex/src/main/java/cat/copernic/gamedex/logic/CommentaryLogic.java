package cat.copernic.gamedex.logic;

import cat.copernic.gamedex.entity.Commentary;
import cat.copernic.gamedex.repository.CommentaryRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentaryLogic {

    @Autowired
    private CommentaryRepository commentaryRepository;

    public Commentary createCommentary(Commentary commentary) {

        try {
            Optional<Commentary> oldCommentary = commentaryRepository.findById(commentary.getIdCommentary());
            if (oldCommentary.isPresent()) {
                throw new RuntimeException("Commentary already exists");
            }
            return commentaryRepository.save(commentary);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error while creating commentary");
        }
    }
}

