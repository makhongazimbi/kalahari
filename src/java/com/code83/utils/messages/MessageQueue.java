package com.code83.utils.messages;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * 
 *   MessageQueue.java 735 2010-03-08 06:44:28Z mngazimb $
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: MessageQueue.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 *
 */
public class MessageQueue implements Queue<Message<?>> {

	private static MessageQueue instance = null;
	private PriorityBlockingQueue<Message<?>> queue;
	private static final int INITIAL_Q_SIZE = 11;

	private MessageQueue () {
		queue = new PriorityBlockingQueue<Message<?>>(INITIAL_Q_SIZE, 
				new MessageComparator());
	}
	
	public static MessageQueue instance () {
		if (instance==null) {
			instance = new MessageQueue();
		}
		return instance;
	}
	
	public boolean add (Message<?> e) {
		return queue.add(e);
	}

	public Message<?> element () {
		return queue.element();
	}

	public boolean offer (Message<?> e) {
		return queue.offer(e);
	}

	public Message<?> peek () {
		return queue.peek();
	}

	public Message<?> poll () {
		return queue.poll();
	}

	public Message<?> remove () {
		return queue.remove();
	}

	public boolean addAll (Collection<? extends Message<?>> c) {
		return queue.addAll(c);
	}

	public void clear () {
		queue.clear();
	}

	public boolean contains (Object o) {
		return queue.contains(o);
	}

	public boolean containsAll (Collection<?> c) {
		return queue.containsAll(c);
	}

	public boolean isEmpty () {
		return queue.isEmpty();
	}

	public Iterator<Message<?>> iterator () {
		return queue.iterator();
	}

	public boolean remove (Object o) {
		return queue.remove(o);
	}

	public boolean removeAll (Collection<?> c) {
		return queue.removeAll(c);
	}

	public boolean retainAll (Collection<?> c) {
		return queue.retainAll(c);
	}

	public int size () {
		return queue.size();
	}

	public Object[] toArray () {
		return queue.toArray();
	}

	public <T> T[] toArray (T[] a) {
		return queue.toArray(a);
	}
	
	private class MessageComparator implements Comparator<Message<?>> {

		public int compare (Message<?> o1, Message<?> o2) {
			return 0;
		}
		
	}

}
