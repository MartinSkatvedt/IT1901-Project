package fxutil.doc;

/**
 * Listener interface for the (contents of) the (current) document of an IDocumentStorage, e.g.
 * when an **open** action is performed.
 *
 * @author hal
 *
 * @param <D> the document type
 * @param <L> the location type
 */
public interface DocumentListener<D, L> extends DocumentStorageListener<L> {
  /**
   * Notifies that the current document has changed.
   *
   * @param document the new document
   * @param oldDocument the previous document
   */
  public void documentChanged(D document, D oldDocument);
}
