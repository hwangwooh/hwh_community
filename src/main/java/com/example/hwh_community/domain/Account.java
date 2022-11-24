package com.example.hwh_community.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode( of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue
    @Column(name = "account_id")
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String nickname;

    private String password;

    private boolean emailVerified;


    private LocalDateTime joinedAt;

    private String bio;

    private String url;

    private String occupation;

    private String occupation_map;

    private String location;

    @Lob @Basic(fetch =  FetchType.EAGER)
    private String profileImage;


    @Enumerated(EnumType.STRING)
    private ROLE role;

    @JsonIgnore
    @OneToMany
    private Set<Post> postList = new HashSet<>();

    @JsonIgnore
    @OneToMany
    private Set<Raid> raid_account = new HashSet<>();

    @JsonIgnore
    @OneToMany
    private Set<Comment> comments = new HashSet<>();

    @JsonIgnore
    @ManyToMany
    private Set<Raid> raid_memders = new HashSet<>();


    public void addpostList(Post post) {
        post.setAccount(this);
        this.postList.add(post);
    }

    public void addraid_account(Raid raid) {
        raid.setAccount(this);
        this.raid_account.add(raid);
    }
    public void addcomments(Comment comment) {
        comment.setAccount(this);
        this.comments.add(comment);
    }

    public void addraid_memders(Raid raid) {
        raid.setAccount(this);
        this.raid_memders.add(raid);
    }



}
