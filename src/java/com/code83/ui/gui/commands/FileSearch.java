package com.code83.ui.gui.commands;

import com.code83.modules.communication.Responder;
import com.code83.modules.filemods.FileDescriptor;
import com.code83.modules.status.RequestStatus;
import com.code83.modules.status.Status;
import com.code83.utils.messages.Message;
import com.code83.utils.messages.MessageFactory;
import com.code83.utils.messages.Request;


/**
 * A class that encompasses the logic required to search for a file.
 *
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: FileSearch.java 883 2012-09-11 22:31:48Z mngazimb $
 * @since 0.1
 * @see Command
 */
public class FileSearch implements Command {

    /**
     * File descriptor
     */
    private final FileDescriptor file;

    /**
     * Constructor that accepts a file descriptor
     *
     * @param f
     */
    public FileSearch (FileDescriptor f) {
        this.file = f;
    }

    /**
     * Execute file search command.
     */
    public void execute () {
        Message<FileDescriptor> message = MessageFactory.createRequest();
        message.setPayload(this.file);
        RequestStatus reqStatus = Status.getInstance().getRequestStatus();
        reqStatus.addRequest((Request) message);
        Responder responder = Responder.instance();
        responder.sendToAll(message);
    }

}
