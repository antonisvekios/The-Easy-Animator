import cs3500.animator.model.Description;
import java.awt.Color;
import org.junit.Test;


import static org.junit.Assert.assertEquals;

/**
 * Tests for all public methods of {@link cs3500.animator.model.Description}.
 */
public class DescriptionTest {

  Description d1 = new Description("motion",0,1,2,100,200,0,0,0,
      100,100,200,200,100,255,0,0);
  Description d2 = new Description("motion", 100, 100,200,200,100,255,0,0,
      300,300,400,1,1,255,255,255);


  @Test(expected = IllegalArgumentException.class)
  //Testing the Description constructor for a negative start time
  public void testInvalidStartTime() {
    d1 = new Description("motion",-1,1,2,100,200,0,0,0,
        100,100,200,200,100,255,0,0);
  }

  @Test(expected = IllegalArgumentException.class)
  //Testing the Description constructor for an invalid end time
  public void testInvalidEndTime1() {
    d2 = new Description("motion", 100, 100,200,200,100,255,0,0,
        50,300,400,1,1,255,255,255);
  }

  @Test(expected = IllegalArgumentException.class)
  //Testing the Description constructor for a negative end time
  public void testInvalidEndTime2() {
    d1 = new Description("motion",0,1,2,100,200,0,0,0,
        -50,100,200,200,100,255,0,0);
  }

  @Test(expected = IllegalArgumentException.class)
  //Testing the Description constructor for a negative start width
  public void testInvalidStartWidth() {
    d1 = new Description("motion",0,1,2,-100,200,0,0,0,
        100,100,200,200,100,255,0,0);
  }

  @Test(expected = IllegalArgumentException.class)
  //Testing the Description constructor for a negative start height
  public void testInvalidStartHeight() {
    d1 = new Description("motion",0,1,2,100,-200,0,0,0,
        100,100,200,200,100,255,0,0);
  }

  @Test(expected = IllegalArgumentException.class)
  //Testing the Description constructor for a negative end width
  public void testInvalidEndWidth() {
    d1 = new Description("motion",0,1,2,100,200,0,0,0,
        100,100,200,-200,100,255,0,0);
  }

  @Test(expected = IllegalArgumentException.class)
  //Testing the Description constructor for a negative end height
  public void testInvalidEndHeight() {
    d1 = new Description("motion",0,1,2,100,200,0,0,0,
        100,100,200,200,-100,255,0,0);
  }

  @Test
  //The test for check getStartTime method
  public void getStartTimeTest() {
    assertEquals(d1.getStartTime(), 0);
    assertEquals(d2.getStartTime(), 100);
  }

  @Test
  //The test for check getEndTime method
  public void getEndTimeTest() {
    assertEquals(d1.getEndTime(), 100);
    assertEquals(d2.getEndTime(), 300);
  }

  @Test
  //The test for check getStartWidth method
  public void getStartWidthTest() {
    assertEquals(d1.getStartWidth(), 100);
    assertEquals(d2.getStartWidth(), 200);
  }

  @Test
  //The test for check getStartHeight method
  public void getStartHeightTest() {
    assertEquals(d1.getStartHeight(), 200);
    assertEquals(d2.getStartHeight(), 100);
  }

  @Test
  //The test for check getEndWidth method
  public void getEndWidthTest() {
    assertEquals(d1.getEndWidth(), 200);
    assertEquals(d2.getEndWidth(), 1);
  }

  @Test
  //The test for check getEndHeight method
  public void getEndHeightTest() {
    assertEquals(d1.getEndHeight(), 100);
    assertEquals(d2.getEndHeight(), 1);
  }

  @Test
  //The test for check getStartColor method
  public void getStartColorTest() {
    assertEquals(d1.getStartColor(), new Color(0, 0, 0));
    assertEquals(d2.getStartColor(), new Color(255, 0, 0));
  }

  @Test
  //The test for check getEndColor method
  public void getEndColorTest() {
    assertEquals(d1.getEndColor(), new Color(255, 0, 0));
    assertEquals(d2.getEndColor(), new Color(255, 255, 255));
  }

  @Test
  //The test for check getStartPosition method
  public void getStartPositionTest() {
    assertEquals(d1.getStartX(), 1);
    assertEquals(d1.getStartY(), 2);
    assertEquals(d2.getStartX(), 100);
    assertEquals(d2.getStartY(), 200);
  }

  @Test
  //The test for check getEndPosition method
  public void getEndPositionTest() {
    assertEquals(d1.getEndX(), 100);
    assertEquals(d1.getEndY(), 200);
    assertEquals(d2.getEndX(), 300);
    assertEquals(d2.getEndY(), 400);
  }

  @Test
  //testing the starting and ending times of the description to see if they reflect changes from the
  //extend method.
  public void extendTest() {
    assertEquals(d1.getStartTime(), 0);
    assertEquals(d1.getEndTime(), 100);
    this.d1.extend(0);
    assertEquals(d1.getStartTime(), 0);
    assertEquals(d1.getEndTime(), 100);
    this.d1.extend(10);
    assertEquals(d1.getStartTime(), 0);
    assertEquals(d1.getEndTime(), 110);
  }

  @Test (expected = IllegalArgumentException.class)
  //testing the extend method for negative numbers.
  public void extendTestInvalid() {
    assertEquals(d1.getStartTime(), 0);
    assertEquals(d1.getEndTime(), 100);
    this.d1.extend(-10);
  }
}
