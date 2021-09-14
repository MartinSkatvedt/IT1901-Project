package fxutil.doc;

import java.io.IOException;
import java.util.Collection;

/**
 * An interface with the methods necessary for supporting the standard File menu actions.
 * The class representing the document (domain data container) is implicit in the implementation
 * of this interface.
 * The interface includes methods for getting and setting the location and creating, opening and
 * saving the (current) document.
 *
 * @author hal
 *
 * @param <L> The type of the location, typically java.io.File.
 */
public interface DocumentStorage<L> {
  /**
   * Returns the current location (of the current document).
   *
   * @return the current location
   */
  public L getDocumentLocation();

  /**
   * Sets the current location (of the current document), can be used by a save-as action.
   * Must notify listeners.
   *
   * @param documentLocation the document location
   */
  public void setDocumentLocation(L documentLocation);

  /**
   * Adds an DocumentStorageListener that will be notified when the current location changes.
   *
   * @param documentStorageListener the document storage listener
   */
  public void addDocumentStorageListener(DocumentStorageListener<L> documentStorageListener);

  /**
   * Removes an IDocumentStorageListener.
   *
   * @param documentStorageListener the document storage listener
   */
  public void removeDocumentStorageListener(DocumentStorageListener<L> documentStorageListener);

  /**
   * Creates a new documents and sets it as the current one, can be used by a new action.
   */
  public void newDocument();

  /**
   * Loads a documents from the provided location and sets it as the current one,
   * can be used by an open action.
   *
   * @param documentLocation the document location
   */
  public void openDocument(L documentLocation) throws IOException;

  /**
   * Saves the current document (to the current location), can be used by a save action.
   *
   * @throws IOException when save failed
   */
  public void saveDocument() throws IOException;

  /**
   * Returns the set of IDocumentImporters, can be used by an import action.
   *
   * @return the set of IDocumentImporters
   */
  public Collection<DocumentImporter> getDocumentImporters();
}
