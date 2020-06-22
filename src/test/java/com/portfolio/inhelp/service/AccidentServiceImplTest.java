package com.portfolio.inhelp.service;

import com.portfolio.inhelp.command.AccidentCommand;
import com.portfolio.inhelp.dto.AccidentDto;
import com.portfolio.inhelp.model.Accident;
import com.portfolio.inhelp.model.User;
import com.portfolio.inhelp.repository.AccidentRepository;
import com.portfolio.inhelp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccidentServiceImplTest {

    @Mock
    AccidentRepository accidentRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    AccidentServiceImpl accidentService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getOne() {
        Accident accident = Accident.builder().id(1L).build();
        when(accidentRepository.findById(anyLong())).thenReturn(Optional.of(accident));

        AccidentDto accidentDto = accidentService.getOne(1L);

        assertNotNull(accidentDto);
        assertEquals(1L, accidentDto.getId());
        verify(accidentRepository, times(1)).findById(anyLong());
    }

    @Test
    void getOneByUserId() {
        Accident accident = Accident.builder().id(1L).build();
        User user = User.builder().id(1L).accidents(new HashSet<>()).build();
        user.addAccident(accident);

        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        AccidentDto accidentDto = accidentService.getOneByUserId(1L, 1L);

        assertNotNull(accidentDto);
        assertEquals(1L, accidentDto.getId());
        verify(userRepository, times(1)).findById(any());
    }

    @Test
    void getAll() {
        Accident accident1 = Accident.builder().id(1L).build();
        Accident accident2 = Accident.builder().id(2L).build();
        List<Accident> accidents = new ArrayList<>();
        accidents.add(accident1);
        accidents.add(accident2);
        when(accidentRepository.findAll()).thenReturn(accidents);

        List<AccidentDto> accidentDtoList = accidentService.getAll();

        assertEquals(2, accidentDtoList.size());
        verify(accidentRepository, times(1)).findAll();
    }

    @Test
    void getAllByUserId() {
        Accident accident1 = Accident.builder().id(1L).build();
        Accident accident2 = Accident.builder().id(2L).build();
        List<Accident> accidents = new ArrayList<>();
        accidents.add(accident1);
        accidents.add(accident2);
        when(accidentRepository.findAllByUserId(anyLong())).thenReturn(accidents);

        List<AccidentDto> accidentDtoList = accidentService.getAllByUserId(1L);

        assertEquals(2, accidentDtoList.size());
        verify(accidentRepository, times(1)).findAllByUserId(anyLong());
    }

    @Test
    void create() {
        Accident accident = Accident.builder().id(1L).build();
        User user = User.builder().id(1L).accidents(new HashSet<>()).build();
        when(accidentRepository.save(any())).thenReturn(accident);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        AccidentDto accidentDto = accidentService.create(AccidentCommand.builder().id(1L).build(), 1L);

        assertNotNull(accidentDto);
        assertEquals(1L, accidentDto.getId());
        assertEquals(1,user.getAccidents().size());
    }

    @Test
    void update() {
        Accident accident = Accident.builder().id(1L).title("test").build();
        Accident accidentUpdate = Accident.builder().id(1L).title("test2").build();
        User user = User.builder().id(1L).accidents(new HashSet<>()).build();
        user.addAccident(accident);
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(accidentRepository.save(any())).thenReturn(accidentUpdate);

        AccidentDto accidentDto = accidentService.update(AccidentCommand.builder().id(1L).title("test2").build() , 1L);

        assertNotNull(accidentDto);
        assertEquals("test2", accident.getTitle());
        verify(userRepository,times(1)).findById(any());
        verify(accidentRepository,times(1)).save(any());
    }

    @Test
    void delete() {
        Accident accident = Accident.builder().id(1L).build();
        User user = User.builder().id(1L).accidents(new HashSet<>()).build();
        user.addAccident(accident);
        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        accidentService.delete(1L, 1L);

        assertEquals(0, user.getAccidents().size());
        verify(accidentRepository,times(1)).delete(any());
    }
}