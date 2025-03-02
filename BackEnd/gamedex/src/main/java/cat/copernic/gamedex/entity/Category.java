package cat.copernic.gamedex.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "category")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    private String nameCategory;

    private String description;

    @Field(name = "category_photo")
    private byte[] categoryPhoto;
}
