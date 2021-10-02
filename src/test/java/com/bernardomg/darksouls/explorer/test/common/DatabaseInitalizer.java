
package com.bernardomg.darksouls.explorer.test.common;

public interface DatabaseInitalizer {

    public void initialize(final String user, final String password,
            final String url, final Iterable<String> files);

}
