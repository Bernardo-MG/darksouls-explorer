
package com.bernardomg.darksouls.explorer.problem.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@Entity(name = "DataProblem")
@Table(name = "problems")
public final class PersistentDataProblem implements DataProblem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @NonNull
    private Integer id;

    @Column(name = "name", nullable = false)
    @NonNull
    private String  name;

    @Column(name = "problem", nullable = false)
    @NonNull
    private String  problem;

    @Column(name = "source", nullable = false)
    @NonNull
    private String  source;

}
