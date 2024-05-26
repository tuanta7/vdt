package com.vdt.fosho.entity;


import jakarta.persistence.*;
import org.hibernate.validator.constraints.Range;
import org.locationtech.jts.geom.Point;


@Entity
@Table(name = "restaurants")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "phone")
    private String phone;

    @Column(name = "rating")
    @Range(min = 0, max = 5)
    private int rating;

    @Column(name = "address")
    private String address;

    @Column(name = "coordinates", columnDefinition = "Point")
    private Point coordinates;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = true)
    private User owner;

    public Restaurant() {
    }

    public Restaurant(String name, String address, String phone, Point coordinates, String logoUrl, User owner) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.coordinates = coordinates;
        this.logoUrl = logoUrl;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
       return "Restaurant{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", address='" + address + '\'' +
               ", phone='" + phone + '\'' +
               ", coordinates=" + coordinates +
               ", logoUrl='" + logoUrl + '\'' +
               ", owner=" + owner +
               '}';
    }
}
