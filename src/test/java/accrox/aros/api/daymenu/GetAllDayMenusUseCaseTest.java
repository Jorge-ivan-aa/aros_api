package accrox.aros.api.daymenu;

import accrox.aros.api.application.usecases.daymenu.GetAllDayMenusUseCase;
import accrox.aros.api.domain.model.Daymenu;
import accrox.aros.api.domain.repository.DaymenuRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetAllDayMenusUseCaseTest {

    @Mock
    private DaymenuRepository repository;

    @InjectMocks
    private GetAllDayMenusUseCase useCase;

    @Test
    void whenDayMenusExist_thenReturnAll() {
        Daymenu d1 = new Daymenu();
        d1.setId(1L);
        d1.setName("Menu A");
        d1.setCreation(LocalDate.now());

        Daymenu d2 = new Daymenu();
        d2.setId(2L);
        d2.setName("Menu B");
        d2.setCreation(LocalDate.now());

        List<Daymenu> list = new java.util.ArrayList<>();
        list.add(d1);
        list.add(d2);

        when(repository.findAll()).thenReturn(list);

        List<Daymenu> result = useCase.execute();

        assertEquals(2, result.size());
        assertEquals("Menu A", result.get(0).getName());
        assertEquals("Menu B", result.get(1).getName());
    }

}
