
package com.bernardomg.darksouls.explorer.problem.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bernardomg.darksouls.explorer.problem.model.PersistentDataProblem;

public interface DataProblemRepository
        extends JpaRepository<PersistentDataProblem, Integer> {

}
