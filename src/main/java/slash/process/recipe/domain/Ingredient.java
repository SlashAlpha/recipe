package slash.process.recipe.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(exclude = {"recipes"})
@Entity
@Setter
public class Ingredient {

    @OneToOne(fetch = FetchType.EAGER)
    UnitOfMeasure uom;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private BigDecimal amount;

    @ManyToOne
    private Recipe recipe;

    public Ingredient(UnitOfMeasure uom, String description, BigDecimal amount) {
        this.uom = uom;
        this.description = description;
        this.amount = amount;

    }

    public Ingredient() {
    }
}
