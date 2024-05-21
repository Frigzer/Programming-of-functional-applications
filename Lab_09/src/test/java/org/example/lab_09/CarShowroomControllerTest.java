package org.example.lab_09;

import org.example.lab_09.core.CarShowroom;
import org.example.lab_09.core.ItemCondition;
import org.example.lab_09.core.Rating;
import org.example.lab_09.core.Vehicle;
import org.example.lab_09.repository.CarShowroomRepository;
import org.example.lab_09.repository.VehicleRepository;
import org.example.lab_09.service.CarShowroomService;
import org.example.lab_09.service.RatingService;
import org.example.lab_09.service.VehicleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = CarShowroomController.class)
public class CarShowroomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleService vehicleService;

    @MockBean
    private CarShowroomService showroomService;

    @MockBean
    private RatingService ratingService;

    @MockBean
    private CSVExporter csvExporter;

    @MockBean
    private VehicleRepository vehicleRepository;

    @MockBean
    private CarShowroomRepository carShowroomRepository;
    @Autowired
    private CarShowroomService carShowroomService;

    //post /api/product
    @Test
    public void shouldAddVehicle() throws Exception {
        when(vehicleService.getVehicleRepository()).thenReturn(vehicleRepository);

        CarShowroom showroom = new CarShowroom("Test", 15);
        Vehicle vehicle = new Vehicle("Toyota", "Camry", ItemCondition.NEW, 25000.0, 2020, 0.0, 25000.0, showroom);
        given(vehicleService.addVehicle(any(Vehicle.class))).willReturn(vehicle);

        mockMvc.perform(post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"brand\":\"Toyota\",\"model\":\"Camry\",\"condition\":\"NEW\",\"price\":25000.0,\"yearOfProduction\":2020,\"mileage\":0.0,\"engineCapacity\":25000.0,\"showroom\":{\"id\":1}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand").value("Toyota"));
    }

    //delete /api/product/:id
    //nie znaleziono pojazdu
    @Test
    public void shouldReturnNotFoundIfVehicleDoesNotExist() throws Exception {
        given(vehicleService.findVehicle(anyLong())).willReturn(Optional.empty());

        mockMvc.perform(delete("/api/product/{id}", 1))
                .andExpect(status().isNotFound());
    }

    //zmniejszenie ilości pojazdu
    @Test
    public void shouldDecreaseQuantityIfPossible() throws Exception {
        Vehicle vehicle = new Vehicle("Toyota", "Camry", ItemCondition.NEW, 25000, 2020, 0, 2.5);
        given(vehicleService.findVehicle(anyLong())).willReturn(Optional.of(vehicle));

        mockMvc.perform(delete("/api/product/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Vehicle quantity decreased to 0")));

        verify(vehicleService).addVehicle(vehicle);
        assertEquals(0, vehicle.getQuantity());
    }

    //ilość równa zeru
    @Test
    public void shouldReturnBadRequestIfQuantityWouldDropBelowZero() throws Exception {
        Vehicle vehicle = new Vehicle("Toyota", "Camry", ItemCondition.NEW, 25000, 2020, 0, 2.5);
        vehicle.decreaseQuantity();
        given(vehicleService.findVehicle(anyLong())).willReturn(Optional.of(vehicle));

        mockMvc.perform(delete("/api/product/{id}", 1))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Cannot remove vehicle, quantity would drop below zero")));

        verify(vehicleService, never()).addVehicle(any(Vehicle.class));
        assertEquals(0, vehicle.getQuantity());
    }

    //get /api/product/:id/rating
    @Test
    public void shouldReturnRating() throws Exception {
        given(ratingService.calculateAverageRating(1L)).willReturn(4.5);

        mockMvc.perform(get("/api/product/1/rating"))
                .andExpect(status().isOk())
                .andExpect(content().string("4.5"));
    }

    //brak pojazdu o danym id
    @Test
    public void shouldReturnNotFoundForInvalidId() throws Exception {
        given(ratingService.calculateAverageRating(999L)).willReturn(null);

        mockMvc.perform(get("/api/product/999/rating"))
                .andExpect(status().isNotFound());
    }

    //get /api/product/csv
    @Test
    public void shouldReturnVehiclesInCsvFormat() throws Exception {
        String expectedCsvContent = "Brand,Model,Price\nToyota,Camry,25000\n";
        given(csvExporter.exportToCsv()).willReturn(expectedCsvContent.getBytes());

        mockMvc.perform(get("/api/product/csv"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/csv"))
                .andExpect(content().string("Brand,Model,Price\nToyota,Camry,25000\n"));
    }


    //get /api/fulfillment
    @Test
    public void shouldFetchAllShowrooms() throws Exception {
        given(showroomService.findAllShowrooms()).willReturn(Arrays.asList(new CarShowroom("Test Showroom", 150)));

        mockMvc.perform(get("/api/fulfillment")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].showroomName").value("Test Showroom"));
    }

    //post /api/fulfillment
    @Test
    public void shouldCreateShowroom() throws Exception {
        CarShowroom showroom = new CarShowroom();
        showroom.setShowroomName("Elite Autos");
        showroom.setMaxCapacity(500);

        given(carShowroomService.addShowroom(any(CarShowroom.class))).willReturn(showroom);

        mockMvc.perform(post("/api/fulfillment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"showroomName\":\"Elite Autos\",\"maxCapacity\":500}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.showroomName").value("Elite Autos"))
                .andExpect(jsonPath("$.maxCapacity").value(500));
    }

    //delete /api/fulfillment/:id
    //test jeśli salon istnieje
    @Test
    public void shouldDeleteShowroomWhenExists() throws Exception {
        Long showroomId = 1L;
        when(showroomService.existsShowroom(showroomId)).thenReturn(true);
        doNothing().when(showroomService).deleteShowroom(showroomId);

        mockMvc.perform(delete("/api/fulfillment/{id}", showroomId))
                .andExpect(status().isOk());

        verify(showroomService, times(1)).deleteShowroom(showroomId);
    }

    //test jesli salon nie istnieje
    @Test
    public void shouldReturnNotFoundWhenShowroomDoesNotExist() throws Exception {
        Long showroomId = 1L;
        when(showroomService.existsShowroom(showroomId)).thenReturn(false);

        mockMvc.perform(delete("/api/fulfillment/{id}", showroomId))
                .andExpect(status().isNotFound());

        verify(showroomService, never()).deleteShowroom(showroomId);
    }

    //get /api/fulfillment/:id/products
    @Test
    public void shouldReturnVehiclesInShowroom() throws Exception {
        Long showroomId = 1L;
        List<Vehicle> vehicles = List.of(
                new Vehicle("Toyota", "Corolla", ItemCondition.USED, 15000, 2018, 15000, 1800, null),
                new Vehicle("Honda", "Civic", ItemCondition.NEW, 20000, 2020, 0, 2000, null)
        );

        when(vehicleService.findByShowroomId(showroomId)).thenReturn(vehicles);

        mockMvc.perform(get("/api/fulfillment/{id}/products", showroomId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].brand").value("Toyota"))
                .andExpect(jsonPath("$[1].brand").value("Honda"));

        verify(vehicleService, times(1)).findByShowroomId(showroomId);
    }

    //kiedy salon jest pusty
    @Test
    public void shouldReturnNotFoundWhenNoVehiclesInShowroom() throws Exception {
        Long showroomId = 1L;

        when(vehicleService.findByShowroomId(showroomId)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/fulfillment/{id}/products", showroomId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(vehicleService, times(1)).findByShowroomId(showroomId);
    }

    //get /api/fulfillment/:id/fill
    @Test
    public void shouldReturnFillPercentageWhenShowroomExists() throws Exception {
        Long showroomId = 1L;
        double fillPercentage = 75.0; // Przykładowe zapełnienie salonu w procentach.

        when(showroomService.findFulfillmentPercentageById(showroomId)).thenReturn(fillPercentage);

        mockMvc.perform(get("/api/fulfillment/{id}/fill", showroomId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(fillPercentage) + "%"));

        verify(showroomService, times(1)).findFulfillmentPercentageById(showroomId);
    }

    //post /api/rating
    @Test
    public void shouldAddRatingSuccessfully() throws Exception {
        // Przygotowanie danych
        Vehicle vehicle = new Vehicle("Toyota", "Camry", ItemCondition.NEW, 25000, 2020, 0, 2500);
        vehicle.setId(1L);  // Ustawienie ID pojazdu, które ma zostać użyte w teście

        // Mockowanie zachowania serwisów
        when(vehicleService.findVehicle(1L)).thenReturn(Optional.of(vehicle));  // Zwraca pojazd, gdy poszukiwany jest o ID 1
        when(ratingService.addRating(any(Rating.class))).thenAnswer(i -> i.getArguments()[0]);  // Zwraca dodaną ocenę

        // Wywołanie i asercje
        mockMvc.perform(post("/api/rating")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"vehicleId\":1, \"score\":4.5}"))  // Poprawne dane JSON
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.score").value(4.5))
                .andExpect(jsonPath("$.vehicleId").value(1));
    }

    //gdy ocena poza skalą
    @Test
    public void shouldReturnBadRequestWhenRatingInvalid() throws Exception {
        Long vehicleId = 1L;
        double invalidRating = 6.0;

        mockMvc.perform(post("/api/rating")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"vehicleId\":\"" + vehicleId + "\", \"score\":\"" + invalidRating + "\"}"))
                .andExpect(status().isBadRequest());
    }
}
