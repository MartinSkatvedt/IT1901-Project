package fxutil.doc;

/**
 * Interface for the documents that can be loaded and saved,
 * so they can support the operations of a file menu.
 *
 * @param <D> The type of the document content
 * @param <L> The type of the location
 */
public interface DocumentPersistence<D, L> extends DocumentLoader<D>, DocumentSaver<D, L> {
}
