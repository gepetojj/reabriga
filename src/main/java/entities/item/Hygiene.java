package entities.item;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("hygiene")
public class Hygiene extends Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
}
