package lim.seyeon.safe.stay.presentation.DTO;

import jakarta.validation.constraints.NotNull;
import lim.seyeon.safe.stay.domain.House.House;
import lim.seyeon.safe.stay.domain.Neighborhood.Neighborhood;

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

    @NotNull
    private String zipcode;

    @NotNull
    private Double price;

    private String description;

    @NotNull
    private String neighborhood;

    public HouseDTO() {}

    public HouseDTO(String name, String address, String city, String state, String zipcode,
                    Double price, String description, String neighborhood) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.price = price;
        this.description = description;
        this.neighborhood = neighborhood;
    }

    public HouseDTO(Long id, String name, String address, String city, String state, String zipcode,
                    Double price, String description, String neighborhood) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.price = price;
        this.description = description;
        this.neighborhood = neighborhood;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public Double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getNeighborhood() { return neighborhood; }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public static House toEntity(HouseDTO houseDTO, Neighborhood neighborhood) {
        House house = new House(
                houseDTO.getId(),
                houseDTO.getName(),
                houseDTO.getAddress(),
                houseDTO.getCity(),
                houseDTO.getState(),
                houseDTO.getZipcode(),
                houseDTO.getPrice(),
                houseDTO.getDescription(),
                neighborhood
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
                house.getZipcode(),
                house.getPrice(),
                house.getDescription(),
                house.getNeighborhood().getName()
        );
        return houseDTO;
    }
}
