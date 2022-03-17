
package com.bernardomg.darksouls.explorer.persistence;

public interface Query<I, T, S> {

    public T getOutput(final I record);

    public S getStatement();

}
