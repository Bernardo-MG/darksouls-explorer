
package com.bernardomg.darksouls.explorer.graph.model;

import java.util.Map;

public interface Link {

    public void addAttribute(final String key, final Object value);

    public Map<String, Object> getAttributes();

    public Long getSource();

    public String getSourceLabel();

    public Long getTarget();

    public String getTargetLabel();

    public String getType();

    public void setSource(final Long value);

    public void setSourceLabel(final String value);

    public void setTarget(final Long value);

    public void setTargetLabel(final String value);

    public void setType(final String type);

}
