package cat.copernic.gamedex;
/*
import cat.copernic.gamedex.entity.Commentary;
import cat.copernic.gamedex.logic.CommentaryLogic;
import cat.copernic.gamedex.repository.CommentaryRepository;
import java.util.Optional;
import java.util.List;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



public class CommentaryTests {

    @InjectMocks
    private CommentaryLogic commentaryLogic;

    @Mock
    private CommentaryRepository commentaryRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCommentary_Success() {
        Commentary commentary = new Commentary();
        commentary.setIdCommentary("1");

        when(commentaryRepository.findById("1")).thenReturn(Optional.empty());
        when(commentaryRepository.save(commentary)).thenReturn(commentary);

        Commentary result = commentaryLogic.createCommentary(commentary);

        assertNotNull(result);
        assertEquals("1", result.getIdCommentary());
        verify(commentaryRepository, times(1)).findById("1");
        verify(commentaryRepository, times(1)).save(commentary);
    }

    @Test
    public void testCreateCommentary_AlreadyExists() {
        Commentary commentary = new Commentary();
        commentary.setIdCommentary("1");

        when(commentaryRepository.findById("1")).thenReturn(Optional.of(commentary));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            commentaryLogic.createCommentary(commentary);
        });

        assertEquals("Commentary already exists", exception.getMessage());
        verify(commentaryRepository, times(1)).findById("1");
        verify(commentaryRepository, never()).save(commentary);
    }

    @Test
    public void testDeleteCommentary_Success() {
        Commentary commentary = new Commentary();
        commentary.setIdCommentary("1");

        when(commentaryRepository.findById("1")).thenReturn(Optional.of(commentary));

        assertDoesNotThrow(() -> {
            commentaryLogic.deleteCommentary("1");
        });

        verify(commentaryRepository, times(1)).findById("1");
        verify(commentaryRepository, times(1)).deleteById("1");
    }

    @Test
    public void testDeleteCommentary_NotFound() {
        when(commentaryRepository.findById("1")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            commentaryLogic.deleteCommentary("1");
        });

        assertEquals("Commentary not found", exception.getMessage());
        verify(commentaryRepository, times(1)).findById("1");
        verify(commentaryRepository, never()).deleteById("1");
    }

    @Test
    public void testGetAllCommentaries_Success() {
        Commentary commentary1 = new Commentary();
        commentary1.setIdCommentary("1");
        Commentary commentary2 = new Commentary();
        commentary2.setIdCommentary("2");

        when(commentaryRepository.findAllByVideogame()).thenReturn(Arrays.asList(commentary1, commentary2));

        List<Commentary> result = commentaryLogic.getAllCommentaries();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(commentaryRepository, times(1)).findAllByVideogame();
    }
}
*/