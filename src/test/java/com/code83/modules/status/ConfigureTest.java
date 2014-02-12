package com.code83.modules.status;

import org.junit.Assert;
import org.junit.Test;

import com.code83.modules.status.Configure;

/**
 * Test configuration class.
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: ConfigureTest.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 */
public class ConfigureTest {

    @Test
    public void testGetConfigParameters () {
        Configure conf = Configure.instance();
        String version = conf.get("version");
        Assert.assertEquals("0.1", version);
    }

}
