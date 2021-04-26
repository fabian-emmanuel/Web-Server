package enums;

public enum ContentType {
    HTML(),
    JSON();

    ContentType() {
    }

    @Override
    public String toString() {
        return switch (this) {
            case HTML -> "Content-Type: text/html";
            case JSON -> "Content-Type: application/json";
        };
    }
}
