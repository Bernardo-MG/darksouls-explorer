
package com.bernardomg.darksouls.explorer.model;

public final class RelationshipResult implements Relationship {

    private String source;

    private Long   sourceId;

    private String target;

    private Long   targetId;

    private String type;

    public RelationshipResult() {
        super();
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
    public String getType() {
        return type;
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

}
