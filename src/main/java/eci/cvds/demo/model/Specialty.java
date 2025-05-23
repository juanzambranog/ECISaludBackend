package eci.cvds.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "specialties")
public class Specialty {
    @Id
    private String id;
    private String name;
    private String description;
    private String doctor;
    private String location;
    private String imageUrl;
}
