
package com.bernardomg.darksouls.explorer.graph.model;

public interface Link {

    public String getSource();

    public Long getSourceId();

    public String getTarget();

    public Long getTargetId();

    public String getType();

    public void setSource(final String value);

    public void setSourceId(final Long value);

    public void setTarget(final String value);

    public void setTargetId(final Long value);

    public void setType(final String type);

}
