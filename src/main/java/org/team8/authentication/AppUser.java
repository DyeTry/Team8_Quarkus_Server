package org.team8.authentication;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "app_user", schema = "dbo")
public class AppUser extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false, unique = true)
    public String username;

    @Column(name = "passwordHash", nullable = false)
    public String passwordHash;

    @Column(nullable = false)
    public String role;
}
