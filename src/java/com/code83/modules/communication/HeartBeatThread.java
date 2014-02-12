package com.code83.modules.communication;

import java.util.Scanner;

import com.code83.ui.gui.commands.CommandRegister;
import com.code83.utils.messages.Message;
import com.code83.utils.messages.MessageFactory;


/**
 * DELETE ME NO LONGER USED
 * 
 * 
 * This class is responsible for generating the heart beat messages that are
 * placed on the network.
 * 
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version $Id: HeartBeatThread.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 */
class HeartBeatThread extends Thread {
    private final Message<?> heartbeat = MessageFactory.createHeartBeat();
    private final Responder responder;
    private static final int WAIT_TIME = 1 * 10 * 1000;
    private boolean beating = true;

    /**
     * 
     * @param res
     *            Reference to the responder object.
     */
    public HeartBeatThread (Responder res) {
        super("K Heart Beat Thread");
        this.responder = res;
    }

    @Override
    public void run () {
        while (true) {
            if (this.beating) {
                // System.out.println("duh dum");
                this.responder.sendToAll(this.heartbeat);
            }

            try {
                Thread.sleep(HeartBeatThread.WAIT_TIME);
            } catch (InterruptedException e) {
                CommandRegister commands = CommandRegister.instance();
                commands.getCommand("no_network").execute();
                e.printStackTrace();
            }
        }
    }

    /**
     * 
     * @return true if heart beats are being placed on network.
     * @since 0.1
     */
    public boolean isBeating () {
        return this.beating;
    }

    /**
     * 
     * @param on
     *            boolean value set to true for beating false otherwise.
     */
    public void setBeating (boolean on) {
        this.beating = on;
    }

    public static void main (String[] args) {
        Responder res = Responder.instance();
        HeartBeatThread hbt = new HeartBeatThread(res);
        hbt.start();

        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.println("HeartBeat: y/n: ");
            String response = s.nextLine();
            if (response.equalsIgnoreCase("y")) {
                hbt.setBeating(true);
            } else if (response.equalsIgnoreCase("n")) {
                hbt.setBeating(false);
            }
            response = "";
        }
    }
}