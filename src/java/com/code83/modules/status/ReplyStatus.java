package com.code83.modules.status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Set;

import com.code83.utils.messages.Reply;


/**
 * Search reply status. Each reply is associated with a search request.
 * Each search request has a search ID associated with it.
 *
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: ReplyStatus.java 893 2012-09-13 21:32:34Z mngazimb $
 * @since 0.1
 */
public class ReplyStatus {

    /**
     * Search replies received.
     */
    private final Map<Integer, Replies> searches =
            new HashMap<Integer, Replies>();

    /**
     * Default constructor.
     */
    protected ReplyStatus () {
    }

    /**
     * Add a search reply.
     * @param reply Search reply
     */
    public void addReply (Reply reply) {
        if (this.searches.containsKey(reply.getSearchId())) {
            Replies replies = this.searches.get(reply.getSearchId());
            replies.add(reply);
        } else {
            Replies replies = new Replies();
            replies.add(reply);
            this.searches.put(reply.getSearchId(), replies);
        }
    }

    /**
     * Get search replies.
     * @param searchId Get search ID
     * @return Set of search replies
     */
    public Replies getReplies (int searchId) {
        return this.searches.get(searchId);
    }

    /**
     * Get total number of search replies.
     * @return Number of replies
     */
    public int getTotalReplyCount () {
        return this.searches.size();
    }

    /**
     * Get the collection of reply IDs
     *
     * @return Set of reply IDs
     */
    public Set<Integer> getSearchIds () {
        return this.searches.keySet();
    }

    /**
     * Clear all requests from a particular search Id
     *
     * @param id
     */
    public void clearReplyIds (int id) {
        this.searches.remove(id);
    }

    /**
     * Get a string representing the search results received.
     * @return Search replies received.
     */
    public String toString () {
      String output = "";
      for (Integer key : this.searches.keySet()) {
          output += "Search ID: " + key + "\n" +
                  this.searches.get(key) + "\n";
      }
      if (output.length() == 0) {
          output = "No search results";
      }
      return output;
    }

}

/**
 * An object that stores the replies.
 */
class Replies {

  /**
   * This can eventually become a priority queue that rearranges the
   * order of the replies as they are added according to whatever
   * criteria. Also I would like to be able to see duplicate files as
   * they are received. In which case as simple list would no longer
   * work. Maybe some kind of 2-d structure that would store the file
   * hashsum as well as the count of many Nomads can provide that file.
   */
  List<Reply> replies = new ArrayList<Reply>();

  /**
   * Default constructor.
   */
  public Replies () {
  }

  /**
   * Add a new reply to the list.
   * @param reply Reply to be added
   */
  public void add (Reply reply) {
    this.replies.add(reply);
  }

  /**
   * String representation of the replies.
   * @return String of the replies
   */
  public String toString () {
    String output = "";
    // FIXME magic number. Placeholder in config file.
    int max = this.replies.size() >= 5 ? 5 : this.replies.size();
    for (int index = 0; index < max; index++){
      Reply reply = this.replies.get(index);
      output += reply.getPayload() + "\n";
    }
    return output;
  }

}
