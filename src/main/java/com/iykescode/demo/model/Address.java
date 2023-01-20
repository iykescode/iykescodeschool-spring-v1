package com.iykescode.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "address")
public class Address extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private int addressId;

    @Column(name = "address1")
    @NotBlank(message="Address1 must not be blank")
    @Size(min=5, message="Address1 must be at least 5 characters long")
    private String address1;

    @Column(name = "address2")
    private String address2;

    @Column(name = "city")
    @NotBlank(message="City must not be blank")
    @Size(min=5, message="City must be at least 5 characters long")
    private String city;

    @Column(name = "state")
    @NotBlank(message="State must not be blank")
    @Size(min=5, message="State must be at least 5 characters long")
    private String state;

    @Column(name = "zip_code")
    @NotBlank(message="Zip Code must not be blank")
    @Pattern(regexp="(^$|[0-9]{6})",message = "Zip Code must be 6 digits")
    private String zipCode;
}
