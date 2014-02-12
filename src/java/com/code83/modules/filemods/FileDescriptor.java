package com.code83.modules.filemods;

import java.io.Serializable;

/**
 * A class that describes a file.
 * FIXME This class is trying to address far too many attributes most
 * of which will likely never be used. Perhahps refactor to store only
 * the top 5 most important things. Perhaps:
 *    1. filename
 *    2. Md5sum
 *    3. Size in bytes
 *    4. ...
 * Also search term should probably not be stored here and maybe in
 * ReplyStatus object as a Search object.
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version 0.1 SVN: $Id: FileDescriptor.java 890 2012-09-13 17:11:27Z mngazimb $
 * @since 0.1
 */
public class FileDescriptor implements Serializable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 5557886864847256696L;
    /**
     * Title.
     */
    private String title;
    /**
     * Artist.
     */
    private String artist;
    /**
     * Album.
     */
    private String album;
    /**
     * Hash string of the file.
     */
    private String hash;
    /**
     * Genre.
     */
    private Genre genre;
    /**
     * Type of file.
     */
    private FileType fileType;
    private int track;
    private int year;
    private int bitrate;
    /**
     * Type of file.
     */
    private String type;
    /**
     * Search ID.
     */
    private int searchId;
    /**
     * Search string.
     */
    private String searchString;

    /**
     * Explicit default constructor.
     */
    public FileDescriptor () {
    }

    /**
     * Constructor.
     * @param search Search string
     * @param type Type of file for which to search
     */
    public FileDescriptor (String search, String type) {
        this.searchString = search;
        this.type = type;
    }

    public String getTitle () {
        return this.title;
    }

    public String getArtist () {
        return this.artist;
    }

    public String getAlbum () {
        return this.album;
    }

    public String getGenre () {
        return this.genre.toString();
    }

    public int getTrack () {
        return this.track;
    }

    public int getYear () {
        return this.year;
    }

    public int getBitRate () {
        return this.bitrate;
    }

    public String getFileType () {
        return this.fileType.toString();
    }

    public String getHash () {
        return this.hash;
    }

    public void setTitle (String t) {
        this.title = t;
    }

    public void setArtist (String art) {
        this.artist = art;
    }

    public void setAlbum (String alb) {
        this.album = alb;
    }

    public void setGenre (Genre gen) {
        this.genre = gen;
    }

    public void setTrack (int trk) {
        this.track = trk;
    }

    public void setYear (int yr) {
        this.year = yr;
    }

    public void setBitRate (int rate) {
        this.bitrate = rate;
    }

    public void setFileType (FileType ft) {
        this.fileType = ft;
    }

    public void setSearchType (String type) {
        this.type = type;
    }

    public String getSearchType () {
        return this.type;
    }

    public void setHash (String h) {
        this.hash = h;
    }

    public enum Genre {
        BLUES, CLASSIC_ROCK, COUNTRY;
    }

    public enum FileType {
        AUDIO, VIDEO, IMAGE, DOCUMENT, PROGRAM;
    }

    public String toString () {
        return this.searchString;
    }

    /**
     * Get the search ID.
     * @return ID
     */
    public int getId () {
        return this.searchId;
    }

    /**
     * Set the search ID.
     * @param id Search ID
     */
    public void setId (int id) {
        this.searchId = id;
    }

    /**
     * Set the search string.
     * @param search Search string.
     */
    public void setSearchString (String search) {
        this.searchString = search;
    }

    /**
     * Get the search string.
     * @return Search string
     */
    public String getSearchString () {
        return this.searchString;
    }

}
