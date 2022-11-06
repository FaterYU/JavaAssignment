package abdn.scnu.cs;

public class IOException extends RuntimeException {
    public IOException() {
        super();
    }

    public IOException(String e) {
        super(e);
        System.out.println(e);
    }
}
