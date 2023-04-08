package org.example.model;

public class BaseModel implements Comparable<BaseModel> {

    protected Long id;

    public BaseModel(long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int compareTo(BaseModel o) {
        return this.id.compareTo(o.id);
    }
}
