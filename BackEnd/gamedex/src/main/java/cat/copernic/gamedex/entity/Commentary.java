@Document(collection = "commentary")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Commentary {

    @Id
    private String idCommentary;

    private StateType state;

    private String description;

    private double rating;

    @DBRef
    private User user;

    @DBRef
    private List<Videogame> videogame;

    }
