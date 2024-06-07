package lim.seyeon.safe.stay.presentation;

import jakarta.validation.constraints.NotNull;
import lim.seyeon.safe.stay.domain.House;

import java.time.LocalDateTime;

public class HouseDTO {
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String address;

    @NotNull
    private String city;

    @NotNull
    private String state;

    private String zip_code;

    @NotNull
    private Double price;

    private String description;

    private LocalDateTime created_at;

    public HouseDTO() {}

    public HouseDTO(String name, String address, String city, String state, String zip_code, Double price, String description, LocalDateTime created_at) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip_code = zip_code;
        this.price = price;
        this.description = description;
        this.created_at = created_at;
    }

    public HouseDTO(Long id, String name, String address, String city, String state, String zip_code, Double price, String description, LocalDateTime created_at) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip_code = zip_code;
        this.price = price;
        this.description = description;
        this.created_at = created_at;
    }

    public Long getId() {
        return id;
    }

    public @NotNull String getName() {
        return name;
    }

    public @NotNull String getAddress() {
        return address;
    }

    public @NotNull String getCity() {
        return city;
    }

    public @NotNull String getState() {
        return state;
    }

    public String getZipCode() {
        return zip_code;
    }

    public @NotNull Double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return created_at;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public void setAddress(@NotNull String address) {
        this.address = address;
    }

    public void setCity(@NotNull String city) {
        this.city = city;
    }

    public void setState(@NotNull String state) {
        this.state = state;
    }

    public void setZipCode(String zip_code) {
        this.zip_code = zip_code;
    }

    public void setPrice(@NotNull Double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedAt(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public static House toEntity(HouseDTO houseDTO) {
        House house = new House(
                houseDTO.getId(),
                houseDTO.getName(),
                houseDTO.getAddress(),
                houseDTO.getCity(),
                houseDTO.getState(),
                houseDTO.getZipCode(),
                houseDTO.getPrice(),
                houseDTO.getDescription(),
                houseDTO.getCreatedAt()
        );
        return house;
    }

    public static HouseDTO toDTO(House house) {
        HouseDTO houseDTO = new HouseDTO (
                house.getId(),
                house.getName(),
                house.getAddress(),
                house.getCity(),
                house.getState(),
                house.getZipCode(),
                house.getPrice(),
                house.getDescription(),
                house.getCreatedAt()
        );
        return houseDTO;
    }
}
