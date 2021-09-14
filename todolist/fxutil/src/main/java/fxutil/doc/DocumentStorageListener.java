package fxutil.doc;

/**
 * Listener interface for the (current) location of the (current) document of an IDocumentStorage,
 * e.g. when a **save-as** action is performed.
 *
 * @author hal
 *
 * @param <L> the location type
 */
public interface DocumentStorageListener<L> {
  /**
   * Notifies that the current document location has changed.
   *
   * @param documentLocation the new document location
   * @param oldDocumentLocation the previous document location
   */
  public void documentLocationChanged(L documentLocation, L oldDocumentLocation);
}
