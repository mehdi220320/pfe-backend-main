package com.fsb.PFE.authentication.entity;


import com.fsb.PFE.shared.notification.Notification;
import com.fsb.PFE.shared.publication.Publication;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {
    @Id
    @Column(length = 50)
    private String userName;
    private String name;
    private String lastName;
    private String userPassword;
    private String profileImg;
    private String profileId = UUID.randomUUID().toString().substring(0, 20);

    @ManyToMany(fetch = FetchType.EAGER/*,cascade = CascadeType.DETACH*/)
    @JoinTable(name = "USER_ROLE",
            joinColumns = {@JoinColumn(name = "USER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID")})
    private Set<Role> roles= new HashSet<>();
    @OneToMany(mappedBy = "owner")
    private Set<Publication> publications = new HashSet<>();
    @OneToMany
    private List<Notification> notifications = new ArrayList<>();
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_following",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id")
    )
    private List<User> following = new ArrayList<>();
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_follower",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    private List<User> follower = new ArrayList<>();


}
