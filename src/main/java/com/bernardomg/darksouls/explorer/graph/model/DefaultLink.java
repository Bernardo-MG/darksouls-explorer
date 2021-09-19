
package com.bernardomg.darksouls.explorer.graph.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.google.common.base.MoreObjects;

public final class DefaultLink implements Link {

    private Map<String, Object> attributes = new HashMap<>();

    private String source   = "";

    private Long   sourceId = 0l;

    private String target   = "";

    private Long   targetId = 0l;

    private String type     = "";

    public DefaultLink() {
        super();
    }

    @Override
    public final void addAttribute(final String key, final Object value) {
        attributes.put(key, value);
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }

        final DefaultLink other = (DefaultLink) obj;
        return Objects.equals(sourceId, other.sourceId)
                && Objects.equals(targetId, other.targetId)
                && Objects.equals(type, other.type);
    }

    @Override
    public final Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public final String getSource() {
        return source;
    }

    @Override
    public final Long getSourceId() {
        return sourceId;
    }

    @Override
    public final String getTarget() {
        return target;
    }

    @Override
    public final Long getTargetId() {
        return targetId;
    }

    @Override
    public final String getType() {
        return type;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(sourceId, targetId, type);
    }

    @Override
    public final void setSource(final String value) {
        source = value;
    }

    @Override
    public final void setSourceId(final Long value) {
        sourceId = value;
    }

    @Override
    public final void setTarget(final String value) {
        target = value;
    }

    @Override
    public final void setTargetId(final Long value) {
        targetId = value;
    }

    @Override
    public final void setType(final String type) {
        this.type = type;
    }

    @Override
    public final String toString() {
        return MoreObjects.toStringHelper(this).add("source", source)
                .add("sourceId", sourceId).add("target", target)
                .add("targetId", targetId).add("type", type).toString();
    }

}
