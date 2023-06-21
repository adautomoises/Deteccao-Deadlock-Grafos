import java.util.Objects;

public class Vertex<T> {
    private final String name;
    private final boolean isResource;
    private final T item;


    Vertex(String name, boolean isResource, T item) {
        this.name = name;
        this.isResource = isResource;
        this.item = item;
    }
    String getName() {
        return name;
    }

    boolean isResource() {
        return isResource;
    }

    public T getItem() {
        return item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vertex<?> vertex = (Vertex<?>) o;
        return name.equals(vertex.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
