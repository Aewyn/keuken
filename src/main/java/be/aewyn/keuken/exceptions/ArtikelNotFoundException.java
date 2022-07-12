package be.aewyn.keuken.exceptions;

public class ArtikelNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public ArtikelNotFoundException() {
    }

    public ArtikelNotFoundException(String message) {
        super(message);
    }
}
