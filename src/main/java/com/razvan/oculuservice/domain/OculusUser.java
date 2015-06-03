package com.razvan.oculuservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A OculusUser.
 */
@Entity
@Table(name = "OCULUSUSER")
public class OculusUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OCULUSUSER_SQ")
    @SequenceGenerator(name = "OCULUSUSER_SQ", sequenceName = "SQ_OCULUSUSER", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "family_name", nullable = false)
    private String familyName;

    @NotNull
    @Pattern(regexp = "(M|F)")
    @Column(name = "sex", nullable = false)
    private String sex;

    @NotNull
    @Min(value = 1)
    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "smoking_time")
    private Integer smoking_time;

    @Column(name = "time_since_last_cigarette")
    private Integer time_since_last_cigarette;

    @Column(name = "form_score")
    private Integer form_score;

    @OneToMany(mappedBy = "oculusUser", orphanRemoval = true)
    @JsonIgnore
    private Set<OculusEvent> oculusEvents = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return firstName;
    }

    public void setFirst_name(String first_name) {
        this.firstName = first_name;
    }

    public String getFamily_name() {
        return familyName;
    }

    public void setFamily_name(String family_name) {
        this.familyName = family_name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSmoking_time() {
        return smoking_time;
    }

    public void setSmoking_time(Integer smoking_time) {
        this.smoking_time = smoking_time;
    }

    public Integer getTime_since_last_cigarette() {
        return time_since_last_cigarette;
    }

    public void setTime_since_last_cigarette(Integer time_since_last_cigarette) {
        this.time_since_last_cigarette = time_since_last_cigarette;
    }

    public Integer getForm_score() {
        return form_score;
    }

    public void setForm_score(Integer form_score) {
        this.form_score = form_score;
    }

    public Set<OculusEvent> getOculusEvents() {
        return oculusEvents;
    }

    public void setOculusEvents(Set<OculusEvent> oculusEvents) {
        this.oculusEvents = oculusEvents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OculusUser oculusUser = (OculusUser) o;

        if ( ! Objects.equals(id, oculusUser.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OculusUser{" +
                "id=" + id +
                ", first_name='" + firstName + "'" +
                ", family_name='" + familyName + "'" +
                ", sex='" + sex + "'" +
                ", age='" + age + "'" +
                ", smoking_time='" + smoking_time + "'" +
                ", time_since_last_cigarette='" + time_since_last_cigarette + "'" +
                '}';
    }
}
