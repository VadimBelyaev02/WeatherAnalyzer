package com.senlatest.weatheranalyzer.unit;

import com.senlatest.weatheranalyzer.client.OpenStreetMapClient;
import com.senlatest.weatheranalyzer.client.model.LocationInfo;
import com.senlatest.weatheranalyzer.exception.DuplicateRecordException;
import com.senlatest.weatheranalyzer.exception.NotFoundException;
import com.senlatest.weatheranalyzer.model.entity.Location;
import com.senlatest.weatheranalyzer.model.mapper.LocationMapper;
import com.senlatest.weatheranalyzer.model.request.LocationRequestDto;
import com.senlatest.weatheranalyzer.model.response.LocationResponseDto;
import com.senlatest.weatheranalyzer.repository.LocationsRepository;
import com.senlatest.weatheranalyzer.service.impl.LocationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Location service unit tests")
@ExtendWith(MockitoExtension.class)
public class LocationServiceUnitTest {

    @Mock
    private LocationsRepository locationsRepository;

    @Mock
    private LocationMapper locationMapper;

    @Mock
    private OpenStreetMapClient openStreetMapClient;

    @InjectMocks
    private LocationServiceImpl locationService;
    private LocationResponseDto locationResponseDto;
    private LocationRequestDto locationRequestDto;
    private Location location;
    private UUID locationId;
    private String city;

    @BeforeEach
    void setup() {
        locationId = UUID.randomUUID();
        city = "Minsk";
        location = Location.builder()
                .id(locationId)
                .longitude(55F)
                .latitude(27F)
                .displayName("Minsk, Belarus")
                .city(city)
                .build();

        locationRequestDto = LocationRequestDto.builder()
                .city(city)
                .build();

        locationResponseDto = LocationResponseDto.builder()
                .city(city)
                .id(locationId)
                .displayName("Minsk, Belarus")
                .build();
    }


    @Nested
    @DisplayName("Retrieving locations test")
    class GetTagTest {
        @Test
        @DisplayName("Get location by id")
        void Given_LocationId_When_LocationWithIdExists_Then_LocationIsReturned() {
            when(locationsRepository.findById(locationId)).thenReturn(Optional.of(location));
            when(locationMapper.toResponse(location)).thenReturn(locationResponseDto);

            assertEquals(locationResponseDto, locationService.getById(locationId));

            verify(locationsRepository, only()).findById(locationId);
            verify(locationMapper, only()).toResponse(location);
        }

        @Test
        @DisplayName("Get location by id that is not found")
        void Given_LocationId_WhenLocationIsNotFound_Then_ThrowsNotFoundException() {
            when(locationsRepository.findById(locationId)).thenReturn(Optional.empty());

            assertThrows(NotFoundException.class, () -> locationService.getById(locationId));

            verify(locationsRepository, only()).findById(locationId);
            verifyNoInteractions(locationMapper);
        }

        @Test
        @DisplayName("Get all locations")
        void Given_Nothing_When_AllLocationsRequested_Then_AllLocationsAreReturned() {
            int listSize = 3;
            List<Location> locations = List.of(location, location, location);
            List<LocationResponseDto> locationResponseDtos = List.of(locationResponseDto, locationResponseDto, locationResponseDto);

            when(locationsRepository.findAll()).thenReturn(locations);
            when(locationMapper.toResponse(any())).thenReturn(locationResponseDto);

            assertEquals(locationResponseDtos, locationService.getAll());

            verify(locationsRepository, only()).findAll();
            verify(locationMapper, times(listSize)).toResponse(location);
            verifyNoMoreInteractions(locationMapper);
        }
    }

    @Nested
    @DisplayName("Saving location tests")
    class SaveTagTest {
        @Test
        @DisplayName("Save location")
        void Given_LocationRequestDto_When_SavingLocation_Then_SavedLocationIsReturned() {
            LocationInfo locationInfo = LocationInfo.builder()
                    .lat(location.getLatitude())
                    .lon(location.getLongitude())
                    .display_name(location.getDisplayName())
                    .build();

            when(locationsRepository.existsByCity(city)).thenReturn(false);
            when(locationMapper.toEntity(locationRequestDto)).thenReturn(location);
            when(locationMapper.toResponse(location)).thenReturn(locationResponseDto);
            when(locationsRepository.save(location)).thenReturn(location);
            when(openStreetMapClient.getLocationByCityName(city)).thenReturn(locationInfo);

            assertEquals(locationResponseDto, locationService.save(locationRequestDto));

            verify(locationsRepository, times(1)).save(location);
            verify(locationsRepository, times(1)).existsByCity(city);
            verify(locationMapper, times(1)).toResponse(location);
            verify(locationMapper, times(1)).toEntity(locationRequestDto);
            verify(openStreetMapClient, only()).getLocationByCityName(city);
            verifyNoMoreInteractions(locationMapper, locationsRepository);
        }

        @Test
        @DisplayName("Save location that already exists by city")
        void Given_LocationRequestDto_When_SavingLocationWithExistingName_Then_DuplicateRecordExceptionIsThrown() {
            when(locationsRepository.existsByCity(city)).thenReturn(true);

            assertThrows(DuplicateRecordException.class, () -> locationService.save(locationRequestDto));

            verify(locationsRepository, only()).existsByCity(city);
            verifyNoInteractions(locationMapper);
            verifyNoMoreInteractions(locationsRepository);
        }
    }

    @Nested
    @DisplayName("Updating location tests")
    class UpdateTagTest {
        @Test
        @DisplayName("Update location")
        void Given_LocationRequestDto_When_UpdatingLocation_Then_UpdatedLocationIsReturned() {
            when(locationsRepository.existsByCity(city)).thenReturn(false);
            when(locationsRepository.findById(locationId)).thenReturn(Optional.of(location));
            when(locationMapper.toResponse(location)).thenReturn(locationResponseDto);
            doNothing().when(locationMapper).updateEntityFromRequestDto(locationRequestDto, location);

            assertEquals(locationResponseDto, locationService.update(locationId, locationRequestDto));

            verify(locationsRepository, times(1)).existsByCity(city);
            verify(locationsRepository, times(1)).findById(locationId);
            verify(locationMapper, times(1)).updateEntityFromRequestDto(locationRequestDto, location);
            verify(locationMapper, times(1)).toResponse(location);
            verifyNoMoreInteractions(locationMapper, locationsRepository);
        }

        @Test
        @DisplayName("Update location that already exists by city")
        void Given_LocationRequestDto_When_UpdatingLocationWithExistedName_Then_DuplicateRecordExceptionIsThrown() {
            when(locationsRepository.existsByCity(city)).thenReturn(true);

            assertThrows(DuplicateRecordException.class, () -> locationService.update(locationId, locationRequestDto));

            verify(locationsRepository, only()).existsByCity(city);
            verifyNoInteractions(locationMapper);
            verifyNoMoreInteractions(locationsRepository);
        }

        @Test
        @DisplayName("Update location that is not found by id")
        void Given_LocationRequestDto_When_UpdatingLocationThatIsNotFoundById_Then_NotFoundExceptionIsThrown() {
            when(locationsRepository.existsByCity(city)).thenReturn(false);
            when(locationsRepository.findById(locationId)).thenReturn(Optional.empty());

            assertThrows(NotFoundException.class, () -> locationService.update(locationId, locationRequestDto));

            verify(locationsRepository, times(1)).existsByCity(city);
            verify(locationsRepository, times(1)).findById(locationId);
            verifyNoMoreInteractions(locationsRepository, locationMapper);
        }
    }

    @Nested
    @DisplayName("Deleting location tests")
    class DeleteTagTest {
        @Test
        @DisplayName("Delete location by id")
        void Given_LocationId_When_DeletingExistingLocation_Then_LocationIsDeleted() {
            when(locationsRepository.existsById(locationId)).thenReturn(true);
            doNothing().when(locationsRepository).deleteById(locationId);

            locationService.deleteById(locationId);

            verify(locationsRepository, times(1)).existsById(locationId);
            verify(locationsRepository, times(1)).deleteById(locationId);
        }

        @Test
        @DisplayName("Delete location that id not found by id")
        void Given_LocationId_When_DeletingNotExistingLocation_Then_NotFoundExceptionIsThrown() {
            when(locationsRepository.existsById(locationId)).thenReturn(false);

            assertThrows(NotFoundException.class, () -> locationService.deleteById(locationId));

            verify(locationsRepository, only()).existsById(locationId);
            verifyNoMoreInteractions(locationsRepository);
        }
    }

}
