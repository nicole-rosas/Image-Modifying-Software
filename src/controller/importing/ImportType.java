package controller.importing;

/**
 * enum of import file types:
 *  PPM : simplest image file format.
 *  PNG - portable network graphics (another image file type)
 *  JPEG - joint photographic experts group (another image file type)
 */
public enum ImportType {
    // Added support for JPEG, PNG
    PPM, JPEG, PNG;
}
