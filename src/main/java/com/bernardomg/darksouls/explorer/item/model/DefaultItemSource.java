
package com.bernardomg.darksouls.explorer.item.model;

public class DefaultItemSource implements ItemSource {

    private String item;

    private String relationship;

    private String source;

    public DefaultItemSource() {
        super();
    }

    @Override
    public String getItem() {
        return item;
    }

    @Override
    public String getRelationship() {
        return relationship;
    }

    @Override
    public String getSource() {
        return source;
    }

    @Override
    public void setItem(final String item) {
        this.item = item;
    }

    @Override
    public void setRelationship(final String relationship) {
        this.relationship = relationship;
    }

    @Override
    public void setSource(final String source) {
        this.source = source;
    }

}
