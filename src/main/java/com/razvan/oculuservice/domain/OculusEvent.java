package com.razvan.oculuservice.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.razvan.oculuservice.domain.util.CustomDateTimeDeserializer;
import com.razvan.oculuservice.domain.util.CustomDateTimeSerializer;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A OculusEvent.
 */
@Entity
@Table(name = "OCULUSEVENT")
public class OculusEvent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OCULUSEVENT_SQ")
    @SequenceGenerator(name = "OCULUSEVENT_SQ", sequenceName = "SQ_OCULUSEVENT", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    @NotNull
    @Column(name = "location", nullable = false)
    private String location;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "entered_time", nullable = false)
    private DateTime entered_time;

    @Column(name = "duration")
    private Integer duration;

    @ManyToOne
    private OculusUser oculusUser;

    public Long getId() {
        return id;
    }

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "exit_time", nullable = false)
    private DateTime exit_time;

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public DateTime getEntered_time() {
        return entered_time;
    }

    public void setEntered_time(DateTime entered_time) {
        this.entered_time = entered_time;
    }

    public DateTime getExit_time() {
        return exit_time;
    }

    public void setExit_time(DateTime exit_time) {
        this.exit_time = exit_time;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public OculusUser getOculusUser() {
        return oculusUser;
    }

    public void setOculusUser(OculusUser oculusUser) {
        this.oculusUser = oculusUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OculusEvent oculusEvent = (OculusEvent) o;

        if ( ! Objects.equals(id, oculusEvent.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OculusEvent{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", type='" + type + "'" +
                ", location='" + location + "'" +
                ", entered_time='" + entered_time + "'" +
                ", exit_time='" + exit_time + "'" +
                ", duration='" + duration + "'" +
                '}';
    }
}
