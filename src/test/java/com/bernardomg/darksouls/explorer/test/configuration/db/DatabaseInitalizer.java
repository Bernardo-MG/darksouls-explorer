
package com.bernardomg.darksouls.explorer.test.configuration.db;

public interface DatabaseInitalizer {

    public void initialize(final String user, final String password, final String url, final Iterable<String> files);

}
