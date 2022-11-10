package com.example.hwh_community.domain;


import com.example.hwh_community.raid.Gametype;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Raid {

    @Id @GeneratedValue
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Account> members = new HashSet<>();

    @Column(unique = true)
    private String path;

    private String title;

    private String shortDescription;

    @Lob @Basic(fetch = FetchType.EAGER)
    private String fullDescription;

    private LocalDateTime publishedDateTime;

    private LocalDateTime closedDateTime;

    private LocalDateTime recruitingUpdatedDateTime;

    private boolean recruiting = true;

    private boolean published = true;

    private boolean closed = false;

    private boolean useBanner;

    private String tag;

    private Long maximum;

    @Enumerated(EnumType.STRING)
    private Gametype gametype;

    public void inMemeber(Account account) {
        this.members.add(account);
    }

    public void removeMember(Account account) {
        this.getMembers().remove(account);
    }


}
