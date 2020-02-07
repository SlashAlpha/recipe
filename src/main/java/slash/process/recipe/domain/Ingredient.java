package slash.process.recipe.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(exclude = {"recipes"})
@Entity
public class Ingredient {

    @OneToOne(fetch = FetchType.EAGER)
    UnitOfMeasure unitOfMeasure;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descriptions;
    private BigDecimal amount;
    @ManyToOne
    private Recipe recipe;

    public Ingredient(UnitOfMeasure unitOfMeasure, String descriptions, BigDecimal amount) {
        this.unitOfMeasure = unitOfMeasure;
        this.descriptions = descriptions;
        this.amount = amount;

    }


}
