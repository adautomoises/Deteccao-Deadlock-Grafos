public class Vertex {
    private final String name;
    private final boolean isResource;

    Vertex(String name, boolean isResource) {
        this.name = name;
        this.isResource = isResource;
    }
    String getName() {
        return name;
    }

    boolean isResource() {
        return isResource;
    }
}
