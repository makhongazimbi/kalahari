package com.code83.modules.auxilliary;

/**
 * An interface for classes that need to perform shutdown activites prior to the
 * virtual machine terminating.
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: Shutdown.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 */
public interface Shutdown {

    /**
     * The shutdown method.
     */
    void shutdown ();

}
