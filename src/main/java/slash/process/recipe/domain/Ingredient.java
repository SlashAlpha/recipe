package slash.process.recipe.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"recipes"})
@Entity
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
