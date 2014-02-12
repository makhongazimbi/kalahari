package com.code83.utils;

import java.io.File;

/**
 * An object representing the important parts of a file in the library.
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: LibraryItem.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 */
public class LibraryItem {
    public String id;
    public String path;
    public String dateAdded;
    public String type;

    /**
     * Constructor.
     * @param id Item ID value
     * @param path File path
     * @param dateAdded Date added to library
     * @param type Item type
     */
    public LibraryItem (String id, String path, String dateAdded, String type) {
        this.id = id;
        this.path = path;
        this.dateAdded = dateAdded;
        this.type = type;
    }

    /**
     * String describing library item.
     * @return String representation.
     */
    public String toString () {
        return "ID: [" + this.id + "] Path: [" + this.path + "] Date Added: ["
                +
                this.dateAdded + "] Type: [" + this.type + "]";
    }

    /**
     * Get the item ID value.
     * @return ID value
     */
    public String getId () {
        return this.id;
    }

    /**
     * Get the file path.
     * @return File path
     */
    public String getPath () {
        return this.path;
    }

    /**
     * Get the date the item was added to the library.
     * @return Add date
     */
    public String getDateAdded () {
        return this.dateAdded;
    }

    /**
     * Get the item type.
     * @return Item type
     */
    public String getType () {
        return this.type;
    }

    /**
     * Get file name.
     * @return File name
     */
    public String getFileName () {
        File file = new File(this.path);
        return file.getName();
    }

}
