package com.vml.jersey.models;

import com.vml.jersey.validator.Email;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true)
    private Long id;

    @NotNull(message = "{employee.name.notnull}")
    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @NotNull(message = "{employee.dob.notnull}")
    //@JsonFormat(pattern = "")
    @Column(name = "dob", nullable = false)
    private Date dob;

    @Email(message = "employee.email.format")
    @Column(name = "email", length = 255)
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(final Date dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }
}
