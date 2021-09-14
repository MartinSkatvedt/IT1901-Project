package fxutil.doc;

/**
 * An interface with a method for saving a document (domain data container) to a location.
 * This allows various ways of saving or exporting domain data, to different locations and formats.
 *
 * @author hal
 *
 * @param <D> the document type
 * @param <L> the location type
 */
public interface DocumentSaver<D, L> {
  /**
   * Saves the provided document to the provided location.
   *
   * @param document the document
   * @param documentLocation the document location
   * @throws Exception when save failed
   */
  public void saveDocument(D document, L documentLocation) throws Exception;
}
