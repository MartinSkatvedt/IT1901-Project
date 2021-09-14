package fxutil.doc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;

/**
 * Controller for the file menu.
 */
public class FileMenuController {

  private DocumentStorage<File> documentStorage;

  /**
   * Sets the document storage.
   *
   * @param documentStorage the document storage
   */
  public void setDocumentStorage(final DocumentStorage<File> documentStorage) {
    this.documentStorage = documentStorage;
    if (importMenu != null) {
      importMenu.setDisable(documentStorage.getDocumentImporters().isEmpty());
    }
  }

  @FXML
  public void handleNewAction() {
    documentStorage.newDocument();
  }

  private final List<File> recentFiles = new ArrayList<File>();

  public Collection<File> getRecentFiles() {
    return Collections.unmodifiableCollection(recentFiles);
  }

  public void addRecentFiles(File... files) {
    recentFiles.addAll(List.of(files));
    updateRecentMenu();
  }

  public void clearRecentFiles() {
    recentFiles.clear();
    updateRecentMenu();
  }

  @FXML
  private Menu recentMenu;

  protected void updateRecentFiles(final File file) {
    recentFiles.remove(file);
    recentFiles.add(0, file);
    recentMenu.getItems().clear();
    updateRecentMenu();
  }

  private Map<String, String> prefixReplacements =
      new HashMap<>(Map.of(System.getProperty("user.home"), "~"));

  public void setPrefixReplacement(String prefix, String replacement) {
    prefixReplacements.put(prefix, replacement);
  }

  public void removePrefixReplacement(String prefix) {
    prefixReplacements.remove(prefix);
  }

  private String replacePrefix(String s) {
    for (Map.Entry<String, String> entry : prefixReplacements.entrySet()) {
      if (s.startsWith(entry.getKey())) {
        return entry.getValue() + s.substring(entry.getKey().length());
      }
    }
    return s;
  }

  protected void updateRecentMenu() {
    recentMenu.getItems().clear();
    for (final File recentFile : recentFiles) {
      final MenuItem menuItem = new MenuItem();
      String fileString = recentFile.toString();
      String menuItemString = replacePrefix(fileString);
      menuItem.setText(menuItemString);
      menuItem.setOnAction(this::handleOpenAction);
      recentMenu.getItems().add(menuItem);
    }
  }

  private FileChooser fileChooser;

  FileChooser getFileChooser() {
    if (fileChooser == null) {
      fileChooser = new FileChooser();
    }
    return fileChooser;
  }

  /**
   * Handles the File > Open menu action.
   *
   * @param event the source event
   */
  @FXML
  public void handleOpenAction(final ActionEvent event) {
    File selection = null;
    if (event.getSource() instanceof MenuItem menuItem) {
      final File file = new File(menuItem.getText());
      if (file.exists()) {
        selection = file;
      }
    }
    if (selection == null) {
      final FileChooser fileChooser = getFileChooser();
      selection = fileChooser.showOpenDialog(null);
    }
    if (selection != null) {
      handleOpenAction(selection);
    }
  }

  void handleOpenAction(final File selection) {
    try {
      documentStorage.openDocument(selection);
      updateRecentFiles(selection);
    } catch (final IOException e) {
      showExceptionDialog("Oops, problem when opening " + selection, e);
    }
  }

  /**
   * Opens the most recent file.
   *
   * @return true of the file was opened, false otherwise
   */
  public boolean openMostRecentFile() {
    if (recentFiles.size() >= 1) {
      try {
        documentStorage.openDocument(recentFiles.get(0));
        return true;
      } catch (IOException e) {
        return false;
      }
    }
    return false;
  }

  private void showExceptionDialog(final String message) {
    final Alert alert = new Alert(AlertType.ERROR, message, ButtonType.CLOSE);
    alert.showAndWait();
  }

  private void showExceptionDialog(final String message, final Exception e) {
    showExceptionDialog(message + ": " + e.getLocalizedMessage());
  }

  private void showSaveExceptionDialog(final File location, final Exception e) {
    showExceptionDialog("Oops, problem saving to " + location, e);
  }

  /**
   * Handles the File > Save menu action.
   */
  @FXML
  public void handleSaveAction() {
    if (documentStorage.getDocumentLocation() == null) {
      handleSaveAsAction();
    } else {
      try {
        documentStorage.saveDocument();
      } catch (final IOException e) {
        showSaveExceptionDialog(documentStorage.getDocumentLocation(), e);
      }
    }
  }

  /**
   * Handles the File > Save as... menu action
   */
  @FXML
  public void handleSaveAsAction() {
    final FileChooser fileChooser = getFileChooser();
    final File selection = fileChooser.showSaveDialog(null);
    handleSaveAsAction(selection);
  }

  void handleSaveAsAction(final File selection) {
    final File oldStorage = documentStorage.getDocumentLocation();
    try {
      documentStorage.setDocumentLocation(selection);
      documentStorage.saveDocument();
      updateRecentFiles(selection);
    } catch (final IOException e) {
      showSaveExceptionDialog(documentStorage.getDocumentLocation(), e);
      documentStorage.setDocumentLocation(oldStorage);
    }
  }

  /**
   * Handles the File > Save Copy As... menu action
   */
  @FXML
  public void handleSaveCopyAsAction() {
    final FileChooser fileChooser = getFileChooser();
    final File selection = fileChooser.showSaveDialog(null);
    handleSaveCopyAsAction(selection);
  }

  void handleSaveCopyAsAction(final File selection) {
    final File oldStorage = documentStorage.getDocumentLocation();
    try {
      documentStorage.setDocumentLocation(selection);
      documentStorage.saveDocument();
    } catch (final IOException e) {
      showSaveExceptionDialog(selection, e);
    } finally {
      documentStorage.setDocumentLocation(oldStorage);
    }
  }

  @FXML
  private Menu importMenu;

  /**
   * Handles the File > Import... menu action
   */
  @FXML
  public void handleFileImportAction() {
    final FileChooser fileChooser = getFileChooser();
    final File selection = fileChooser.showOpenDialog(null);
    //    String path = selection.getPath();
    //    int pos = path.lastIndexOf('.');
    //    String ext = (pos > 0 ? path.substring(pos + 1) : null);
    handleFileImportAction(selection);
  }

  void handleFileImportAction(final File selection) {
    for (final DocumentImporter importer : documentStorage.getDocumentImporters()) {
      try (InputStream input = new FileInputStream(selection)) {
        importer.importDocument(input);
        break;
      } catch (final Exception e) {
        throw new RuntimeException(e);
      }
    }
  }

  private TextInputDialog inputDialog;

  /**
   * Handles the File > URL Import... menu action
   */
  @FXML
  public void handleUrlImportAction() {
    if (inputDialog == null) {
      inputDialog = new TextInputDialog();
    }
    inputDialog.setTitle("Import from URL");
    inputDialog.setHeaderText("Enter URL to import from");
    inputDialog.setContentText("Enter URL: ");
    URL url = null;
    while (true) {
      final Optional<String> result = inputDialog.showAndWait();
      if (! result.isPresent()) {
        return;
      }
      try {
        url = new URL(result.get());
        if (handleUrlImportAction(url)) {
          return;
        }
        url = null;
        inputDialog.setHeaderText("Problems reading it...");
        inputDialog.setContentText("Enter another URL: ");
      } catch (final MalformedURLException e1) {
        inputDialog.setContentText("Enter a valid URL: ");
      }
    }
  }

  boolean handleUrlImportAction(final URL url) {
    for (final DocumentImporter importer : documentStorage.getDocumentImporters()) {
      try (InputStream input = url.openStream()) {
        importer.importDocument(input);
        return true;
      } catch (final Exception e) {
        System.err.println(e.getMessage());
      }
    }
    return false;
  }
}
