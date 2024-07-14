package exceptions;

public class InventoryDoesNotExistException extends RuntimeException {
    public InventoryDoesNotExistException(String message) {
        super(message);
    }
}
