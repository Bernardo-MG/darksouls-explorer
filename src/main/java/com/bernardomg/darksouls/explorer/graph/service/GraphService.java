
package com.bernardomg.darksouls.explorer.graph.service;

import com.bernardomg.darksouls.explorer.graph.model.Graph;
import com.bernardomg.darksouls.explorer.graph.model.Info;

public interface GraphService {

    public Graph getGraph(final Iterable<String> relationships);

    public Info getInfo(final Long id);

    public Iterable<String> getLinks();

}
