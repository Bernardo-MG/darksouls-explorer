
package com.bernardomg.darksouls.explorer.model;

public interface Relationship {

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