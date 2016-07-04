package act.remote;

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
 */
public enum RequestPayloadEncoding {

}
