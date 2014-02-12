package com.code83;

import junit.framework.*;

/**
 * Unit testing example class.
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 */
public class TestExample extends TestCase {

  public void testSum () {
    Example example = new Example();
    int sum = example.sum(2, 3);
    assertEquals(sum, 5);
  }

}
