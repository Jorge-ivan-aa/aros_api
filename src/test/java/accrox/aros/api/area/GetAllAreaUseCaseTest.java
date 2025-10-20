package accrox.aros.api.area;

import accrox.aros.api.application.dto.area.GetAreaOutput;
import accrox.aros.api.application.usecases.area.GetAllAreaUseCase;
import accrox.aros.api.domain.model.Area;
import accrox.aros.api.domain.repository.AreaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetAllAreaUseCaseTest {

    @Mock
    private AreaRepository repository;

    @InjectMocks
    private GetAllAreaUseCase useCase;

    @Test
    void whenAreasExist_thenGetAreasSuccessfully(){

        Area area1 = new Area();
        area1.setId(1L);
        area1.setName("Cocina");

        Area area2 = new Area();
        area2.setId(2L);
        area2.setName("Bebidas");

        Area area3 = new Area();
        area3.setId(3L);
        area3.setName("Postres");

        List<Area> areas = new java.util.ArrayList<>();

        areas.add(area1);
        areas.add(area2);
        areas.add(area3);


        when(repository.getAreas()).thenReturn(areas);


        List<GetAreaOutput> result = useCase.execute();

        assertEquals(3, result.size());
        assertEquals("Cocina", result.get(0).name());
        assertEquals("Bebidas", result.get(1).name());
        assertEquals("Postres", result.get(2).name());

        verify(repository, times(1)).getAreas();

    }

}
