package fxutil.doc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Incomplete implementation of **DocumentStorage**,
 * to simplify implementing ones for specific document and location types.
 * The main missing methods creating an empty one and loading and saving them.
 *
 * @author hal
 *
 * @param <D> the document type
 * @param <L> the location type
 */
public abstract class AbstractDocumentStorage<D, L> implements DocumentStorage<L>,
    DocumentPersistence<D, L> {

  private L documentLocation;

  @Override
  public L getDocumentLocation() {
    return documentLocation;
  }

  @Override
  public void setDocumentLocation(final L documentLocation) {
    final L oldDocumentLocation = this.documentLocation;
    this.documentLocation = documentLocation;
    fireDocumentLocationChanged(oldDocumentLocation);
  }

  protected void setDocumentAndLocation(final D document, final L documentLocation) {
    setDocument(document);
    setDocumentLocation(documentLocation);
  }

  private D document;

  /**
   * Returns the current document.
   *
   * @return the current document
   */
  public D getDocument() {
    return document;
  }

  /**
   * Sets the current document.
   * Must notify listeners.
   *
   * @param document the new document
   */
  public void setDocument(D document) {
    D oldDocument = getDocument();
    this.document = document;
    fireDocumentChanged(oldDocument);
  }

  //

  private final Collection<DocumentStorageListener<L>> documentListeners =
      new ArrayList<DocumentStorageListener<L>>();

  @Override
  public void addDocumentStorageListener(final DocumentStorageListener<L> documentStorageListener) {
    documentListeners.add(documentStorageListener);
  }

  @Override
  public void removeDocumentStorageListener(final DocumentStorageListener<L>
      documentStorageListener) {
    documentListeners.remove(documentStorageListener);
  }

  protected void fireDocumentLocationChanged(final L oldDocumentLocation) {
    for (final DocumentStorageListener<L> documentStorageListener : documentListeners) {
      documentStorageListener.documentLocationChanged(documentLocation, oldDocumentLocation);
    }
  }

  protected void fireDocumentChanged(final D oldDocument) {
    for (final DocumentStorageListener<L> documentListener : documentListeners) {
      if (documentListener instanceof DocumentListener) {
        ((DocumentListener<D, L>) documentListener).documentChanged(getDocument(), oldDocument);
      }
    }
  }

  /**
   * Creates a new and empty document.
   *
   * @return the new document
   */
  protected abstract D createDocument();

  @Override
  public void newDocument() {
    setDocumentAndLocation(createDocument(), null);
  }

  /**
   * Creates an ImportStream from a location.
   *
   * @param location the location
   * @return the input stream
   * @throws IOException when operation failed
   */
  protected abstract InputStream toInputStream(L location) throws IOException;

  protected InputStream toFileInputStream(final File location) throws IOException {
    return new FileInputStream(location);
  }

  protected InputStream toUrlInputStream(final URL location) throws IOException {
    return location.openStream();
  }

  protected InputStream toUrlInputStream(final URI location) throws IOException {
    return toUrlInputStream(location.toURL());
  }

  @Override
  public void openDocument(final L storage) throws IOException {
    try (InputStream input = toInputStream(storage)) {
      setDocumentAndLocation(loadDocument(input), storage);
    } catch (final Exception e) {
      throw new IOException(e);
    }
  }

  @Override
  public void saveDocument() throws IOException {
    try {
      saveDocument(getDocument(), getDocumentLocation());
    } catch (final Exception e) {
      throw new IOException(e);
    }
  }

  /**
   * Save document in specified location.
   *
   * @param documentLocation the document location
   */
  public void saveDocumentAs(final L documentLocation) throws IOException {
    final L oldDocumentLocation = getDocumentLocation();
    setDocumentLocation(documentLocation);
    try {
      saveDocument();
    } catch (final IOException e) {
      setDocumentLocation(oldDocumentLocation);
      throw e;
    }
  }

  public void saveCopyAs(final L documentLocation) throws Exception {
    saveDocument(getDocument(), documentLocation);
  }

  //

  @Override
  public Collection<DocumentImporter> getDocumentImporters() {
    return Collections.emptyList();
  }
}
