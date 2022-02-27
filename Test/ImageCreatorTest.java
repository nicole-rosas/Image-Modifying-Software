import model.images.image.ImageType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import model.images.image.InterfaceImages;
import model.images.image.ImageCreator;

/**
 * Tests the methods for {@code ImageCreator} class.
 */
public class ImageCreatorTest {

  InterfaceImages tdMatrix;

  @Before
  public void setup() {
    tdMatrix = ImageCreator.create(ImageType.MATRIX2D);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullImageType() {
    ImageCreator.create(null);
  }

  @Test
  public void tDMatrixTypeCreate() {
    assertEquals(tdMatrix.getClass(), ImageCreator.create(ImageType.MATRIX2D).getClass());
  }

}
