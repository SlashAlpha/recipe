package slash.process.recipe.services;

import org.springframework.stereotype.Service;
import slash.process.recipe.commands.UnitOfMeasureCommand;
import slash.process.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import slash.process.recipe.repositories.UnitOfMeasureRepository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    UnitOfMeasureRepository unitOfMeasureRepository;
    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;


    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository, UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    @Override
    public Set<UnitOfMeasureCommand> listAllUoms() {

        return StreamSupport.stream(unitOfMeasureRepository.findAll().spliterator(), false)
                .map(unitOfMeasureToUnitOfMeasureCommand::convert).collect(Collectors.toSet());

//       Set<UnitOfMeasure> unitOfMeasures=new HashSet<>();
//       unitOfMeasureRepository.findAll().forEach(unitOfMeasures::add);
//       return unitOfMeasures;
    }
}
