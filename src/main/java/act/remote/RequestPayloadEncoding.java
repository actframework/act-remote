package act.remote;

import act.data.RequestBodyParser;

/**
 * Defines the way to serialize structured payload to
 * request query or body.
 *
 * For example, for a given array {1, 2, 3} with param name `foo`, it can be
 * serialized to request in multiple ways:
 *
 * * `foo=1&foo=2&foo=3`
 * * `foo[]=1&foo[]=2&foo[]=3`
 * * `foo[0]=1&foo[1]=2&foo[2]=3`
 * * `foo.0=1&foo.1=2&foo.2=3`
 *
 * For more information please refer to
 * https://github.com/actframework/act-remote/blob/master/doc/payload-encoding.md
 */
public enum RequestPayloadEncoding {

    NAME_ONLY,

    NAME_BRACKET,

    NAME_BRACKET_INDEX,

    NAME_DOT_INDEX,

    JSON,

    /**
     * JQuery encoding is just an alias of
     * {@link #NAME_BRACKET}
     */
    JQUERY(NAME_BRACKET),

    /**
     * Default encoding is {@link #JSON}
     */
    DEFAULT(JSON)

    ;

    private RequestPayloadEncoding delegateEncoding;

    private RequestPayloadEncoding() {this(null);}

    private RequestPayloadEncoding(RequestPayloadEncoding delegate) {
        this.delegateEncoding = delegate;
    }

}
