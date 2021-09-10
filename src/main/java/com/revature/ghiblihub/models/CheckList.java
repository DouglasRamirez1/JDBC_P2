package com.revature.ghiblihub.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;



@Entity
@Table(name = "check_list")
@Getter
@Setter
@NoArgsConstructor
public class CheckList {

    @Id
    @Column(name = "check_list_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int checkListId;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    @ManyToOne(targetEntity = GhibliFilm.class)
    @JoinColumn(name = "film_id", nullable = false)
    private GhibliFilm filmId;

    @Column(name = "favorite")
    private boolean favorite;
}
