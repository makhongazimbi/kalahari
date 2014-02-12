package com.code83.modules.status;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configuration class. Loads system properties from a file and stores them in
 * memory.
 *
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: Configure.java 881 2012-05-21 21:02:12Z mngazimb $
 * @since 0.1
 */
public class Configure extends Properties {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 9061848149034788050L;

    /**
     * This configure object.
     */
    private static Configure config = null;

    /**
     * Configuration file path relative to Kalahari jar.
     */
    private static final String configPath = "config/conf.properties";

    /**
     * Default constructor. Reads configuration file.
     *
     * @throws IOException
     */
    private Configure () throws IOException {
        File configFile = new File(Configure.configPath);
        InputStream is = new FileInputStream(configFile);
        this.load(is);
    }

    /**
     * Create an instance of this class, instantiating it if it does not exist.
     *
     * @return Configuration object
     */
    public static Configure instance () {
        if (Configure.config == null) {
            try {
                Configure.config = new Configure();
            } catch (IOException e) {
                Logger logger = LoggerFactory
                        .getLogger("modules.status.Configure");
                logger.error("Unable to read config file at relative path ["
                        + Configure.configPath + "]");
                throw new RuntimeException(e);
            }
        }
        return Configure.config;
    }

    /**
     * Get the value of a named parameter.
     *
     * @param param
     *            Parameter name
     * @return Parameter value
     */
    public String get (String param) {
        return this.getProperty(param);
    }

}
