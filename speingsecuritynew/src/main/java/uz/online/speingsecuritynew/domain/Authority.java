package uz.online.speingsecuritynew.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "mohirdev_authorty")
public class Authority implements Serializable {

    @Id
    private String name;
}
