package com.example.hwh_community.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode( of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    private String title;

    @Lob
    private String content;


    private LocalDate dateTime;

    @JsonIgnore
    @OneToMany(mappedBy = "post",cascade = CascadeType.REMOVE) // cascade = CascadeType.REMOVE 연관 관계 코멘트도 같이 삭제할수 있음
    private List<Comment> commentList = new ArrayList<>();
}
