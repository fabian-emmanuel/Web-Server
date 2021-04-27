package enums;

public enum ThreadColor {
    RESET(),
    BLACK(),
    RED(),
    GREEN(),
    BLUE(),
    PURPLE(),
    CYAN();

    ThreadColor() {
    }

    @Override
    public String toString() {
        return switch(this){
            case RED -> "\u001B[31m";
            case BLUE -> "\u001B[34m";
            case CYAN -> "\u001B[36m";
            case BLACK -> "\u001B[30m";
            case GREEN -> "\u001B[32m";
            case PURPLE -> "\u001B[35m";
            case RESET -> "\u001B[0m";
        };
    }
}
