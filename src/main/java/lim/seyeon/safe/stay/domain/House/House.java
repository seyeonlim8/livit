package lim.seyeon.safe.stay.domain.House;

import jakarta.persistence.*;
import lim.seyeon.safe.stay.domain.Neighborhood.Neighborhood;
import lim.seyeon.safe.stay.domain.Review.Review;

import java.util.Set;

@Entity
@Table(name = "houses")
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "city", nullable = false, length = 50)
    private String city;

    @Column(name = "state", nullable = false, length = 50)
    private String state;

    @Column(name = "zipcode", nullable = false, length = 10)
    private String zipcode;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "neighborhood", nullable = false)
    private Neighborhood neighborhood;

    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL)
    private Set<Review> reviews;

    public Boolean sameId(Long id) {
        return this.id.equals(id);
    }

    public House() {}

    public House(Long id, String name, String address, String city, String state, String zipcode,
                 Double price, String description, Neighborhood neighborhood) {
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

    public Boolean containsName(String name) {
        return this.name.contains(name);
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

    public Neighborhood getNeighborhood() { return neighborhood; }

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

    public void setZipcode(String zipcode) { this.zipcode = zipcode; }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNeighborhood(Neighborhood neighborhood) {
        this.neighborhood = neighborhood;
    }
}
