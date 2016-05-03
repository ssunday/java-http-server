package HTTP;

public enum HTTPCode {
    OK("200 OK"),
    NO_CONTENT("204 No Content"),
    PARTIAL_CONTENT("206 Partial Content"),
    FOUND("302 Found"),
    UNAUTHORIZED("401 Unauthorized"),
    NOT_FOUND("404 Not Found"),
    METHOD_NOT_ALLOWED("405 Method Not Allowed"),
    TEAPOT("418 I'm a teapot");

    private final String codeValue;

    private HTTPCode(final String s) { codeValue = s; }

    @Override
    public String toString() { return codeValue; }

}
