package uz.online.speingsecuritynew.domain;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "mohirdev_user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private boolean activated = false;
    
}
