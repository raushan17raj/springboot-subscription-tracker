package com.raushan.apps.subsentry.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString// Lombok: Adds getters, setters, toString, equals, hashCode
@NoArgsConstructor // Lombok: Adds a no-argument constructor
@AllArgsConstructor // Lombok: Adds a constructor with all arguments
@Entity // JPA: Marks this class as a database entity
@Table(name = "users") // Specifies the table name (avoiding SQL keyword "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrementing ID
    private Long id;

    @Column(nullable = false, unique = true) // DB constraint: must exist and be unique
    private String email;

    @Column(nullable = false) // DB constraint: must exist
    private String password; // This will store the *hashed* password

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = true)
    private Long mobileNumber;

    /**
     * A user can have many subscriptions.
     * 'mappedBy = "user"' tells JPA that the 'user' field in the Subscription class
     * manages this relationship.
     * 'cascade = CascadeType.ALL' means if a user is deleted, all their subscriptions
     * are deleted too.
     * 'fetch = FetchType.LAZY' is a performance optimization.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Subscription> subscriptions;

    /**
     * Users can have multiple roles.
     * FetchType.EAGER: Load roles immediately when the user is loaded.
     * CascadeType.ALL: Operations on User might affect Roles (use carefully, often MERGE/PERSIST is better).
     * JoinTable defines the intermediate table ("user_roles") to link users and roles.
     */
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

    // This provides stable equals/hashCode methods.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        // Check if id is null for new entities, otherwise compare id
        return id != null ? id.equals(user.id) : super.equals(o);
    }

    @Override
    public int hashCode() {
        // Use a prime number, or simply the id's hashcode if not null
        return id != null ? id.hashCode() : Objects.hash(super.hashCode());
    }
}