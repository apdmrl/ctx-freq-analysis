package com.cfa.ctxfreqanalysis.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "contents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "stats", nullable = false)
    private String stats;

    @Column(name = "statistics", nullable = false)
    private String statistics;

    @Column(name ="language", nullable = false)
    private Language language;

    private Long contextId;
}
