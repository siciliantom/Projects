package by.bsu.hostel.domain;

/**
 * Created by Kate on 13.02.2016.
 */
public abstract class Entity {
    private Long id;

    protected Entity() {
    }

    protected Entity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "id=" + id +
                '}';
    }
}
