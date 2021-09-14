package fxutil.doc;

import java.io.InputStream;

/**
 * An interface with a method for loading and returning a document (domain data container)
 * from an InputStream.
 * This allows various ways of loading or importing domain data, with different sources and formats.
 *
 * @author hal
 *
 * @param <D> the document type
 */
public interface DocumentLoader<D> {
  /**
   * Loads and returns a new document from an InputStream.
   *
   * @param inputStream the input stream
   * @return the loaded document
   * @throws Exception when loading failed
   */
  public D loadDocument(InputStream inputStream) throws Exception;
}
