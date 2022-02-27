package controller.export;

/**
 * enum of the different image files to export:
 *  PPM - simplified image file format.
 *  PNG - portable network graphics (another image file type)
 *  JPEG - joint photographic experts group (another image file type)
 */
public enum ExportType {
  // Added support for PNG and JPEG
  PPM, PNG, JPEG;
}
