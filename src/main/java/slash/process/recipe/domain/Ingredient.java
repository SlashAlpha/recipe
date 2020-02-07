package slash.process.recipe.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
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
