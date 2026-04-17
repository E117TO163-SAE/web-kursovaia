package com.example.farm.service;

import com.example.farm.DTO.MappingUtils;
import com.example.farm.DTO.PlaceDTO;
import com.example.farm.entity.Place;
import com.example.farm.entity.PlaceName;
import com.example.farm.repository.PlaceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class PlaceServiceTest {
    @Mock
    private PlaceRepository placeRepository;
    @Mock
    private MappingUtils mappingUtils;

    @InjectMocks
    private PlaceService placeService;


    @Test
    public void findAllTest(){
        //PlaceRepository placeRepository = mock(PlaceRepository.class);
        //MappingUtils mappingUtils = mock(MappingUtils.class);

        Place place = new Place();

        when(placeRepository.findAll()).thenReturn(List.of(place, place));

        when(mappingUtils.placeToDto(place)).thenReturn(new PlaceDTO());

        //PlaceService placeService = new PlaceService(mappingUtils, placeRepository);
        List<PlaceDTO> places = placeService.findAll();

        assertEquals(2, places.size());
    }

    /*@Test
    public void updateTest(){
        Long placeId = 1L;


        PlaceDTO placeDto = new PlaceDTO();
        placeDto.setId(placeId);
        placeDto.setTemperature(10L);
        placeDto.setHumidity(85L);

        Place place = new Place();
        place.setPlaceId(placeId);
        place.setTemperature(10L);
        place.setHumidity(85L);


        PlaceDTO newParam = new PlaceDTO();
        newParam.setTemperature(25L);
        newParam.setHumidity(60L);


        PlaceDTO updatePlaceDto = new PlaceDTO();
        updatePlaceDto.setId(placeId);
        updatePlaceDto.setTemperature(25L);
        updatePlaceDto.setHumidity(60L);

        Place updatedPlace = new Place();
        updatedPlace.setPlaceId(placeId);
        updatedPlace.setTemperature(25L);
        updatedPlace.setHumidity(60L);


        when(placeService.findById(placeId)).thenReturn(Optional.of(placeDto));
        when(mappingUtils.placeToDto(updatedPlace)).thenReturn(updatePlaceDto);
        when(placeRepository.save(updatedPlace)).thenReturn(updatedPlace);
        when(mappingUtils.placeToEntity(updatePlaceDto)).thenReturn(updatedPlace);

        when(placeRepository.findById(placeId)).thenReturn(Optional.of(place));
        when(mappingUtils.placeToDto(place)).thenReturn(placeDto);
        when(mappingUtils.placeToEntity(placeDto)).thenReturn(updatedPlace);
        when(placeRepository.save(updatedPlace)).thenReturn(updatedPlace);
        when(mappingUtils.placeToDto(updatedPlace)).thenReturn(placeDto);

        placeService.update(placeId,newParam);

        assertEquals(25L, placeDto.getTemperature());
        assertEquals(60L, placeDto.getHumidity());

    }*/
    @Test
    void update_shouldModifyAndSaveEntity() {
        Long id = 1L;

        Place existingEntity = new Place();
        existingEntity.setPlaceId(id);
        existingEntity.setTemperature(10L);
        existingEntity.setHumidity(30L);

        PlaceDTO existingDto = new PlaceDTO();
        existingDto.setId(id);
        existingDto.setTemperature(10L);
        existingDto.setHumidity(30L);

        PlaceDTO newParams = new PlaceDTO();
        newParams.setTemperature(25L);
        newParams.setHumidity(60L);

        Place updatedEntity = new Place();

        when(placeRepository.findById(id)).thenReturn(Optional.of(existingEntity));
        when(mappingUtils.placeToDto(existingEntity)).thenReturn(existingDto);
        when(mappingUtils.placeToEntity(existingDto)).thenReturn(updatedEntity);
        when(placeRepository.save(updatedEntity)).thenReturn(updatedEntity);
        when(mappingUtils.placeToDto(updatedEntity)).thenReturn(existingDto);

        placeService.update(id, newParams);

        assertEquals(25L, existingDto.getTemperature());
        assertEquals(60L, existingDto.getHumidity());

        verify(placeRepository).findById(id);
        verify(placeRepository).save(updatedEntity);
    }

    @Test
    void findById_shouldReturnPlaceDTO_whenPlaceExists() {
        Long id = 1L;

        Place place = new Place();
        place.setPlaceId(id);

        PlaceDTO dto = new PlaceDTO();
        dto.setId(id);

        when(placeRepository.findById(id)).thenReturn(Optional.of(place));
        when(mappingUtils.placeToDto(place)).thenReturn(dto);

        Optional<PlaceDTO> result = placeService.findById(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());

        verify(placeRepository).findById(id);
        verify(mappingUtils).placeToDto(place);
    }

    @Test
    void create_shouldSaveAndReturnDTO() {
        PlaceDTO inputDto = new PlaceDTO();
        inputDto.setTemperature(20L);
        inputDto.setHumidity(50L);

        Place entity = new Place();
        Place savedEntity = new Place();
        PlaceDTO outputDto = new PlaceDTO();

        when(mappingUtils.placeToEntity(inputDto)).thenReturn(entity);
        when(placeRepository.save(entity)).thenReturn(savedEntity);
        when(mappingUtils.placeToDto(savedEntity)).thenReturn(outputDto);

        PlaceDTO result = placeService.create(inputDto);

        assertNotNull(result);

        verify(mappingUtils).placeToEntity(inputDto);
        verify(placeRepository).save(entity);
        verify(mappingUtils).placeToDto(savedEntity);
    }
}
