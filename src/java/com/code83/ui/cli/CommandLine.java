package com.code83.ui.cli;

import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.code83.modules.filemods.FileDescriptor;
import com.code83.modules.status.Configure;
import com.code83.modules.status.NomadStatus;
import com.code83.modules.status.RequestStatus;
import com.code83.modules.status.Status;
import com.code83.ui.cli.Parser.ParserException;
import com.code83.ui.gui.commands.Command;
import com.code83.ui.gui.commands.FileSearch;
import com.code83.ui.gui.commands.Invisible;
import com.code83.ui.gui.commands.PingNomad;
import com.code83.ui.gui.commands.Visible;
import com.code83.utils.messages.HeartBeat;


/**
 * The main class in the command line interface to Kalahari.
 *
 * @author Makho Ngazimbi <makho.ngazimbi@gmail.com>
 * @version 0.1 SVN: $Id: CommandLine.java 885 2012-09-11 23:34:15Z mngazimb $
 * @since 0.1
 * @see Gui
 */
public class CommandLine {

    /**
     * Command line prompt string.
     */
    private static final String PROMPT = "%> ";

    /**
     * Input scanner.
     */
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Logger.
     */
    private final Logger logger = LoggerFactory.getLogger(CommandLine.class);

    /**
     * Command line parser.
     */
    private final Parser parser;

    /**
     * Default constructor.
     */
    public CommandLine () {
        this.parser = new Parser();
        try {
            this.parser.addCommand("help", 'h');

            this.parser.addCommand("invisible", 'i');

            this.parser.addCommand("network", 'n');

            Parser.Command ping = this.parser.addCommand("ping", 'p');
            ping.addSwitch('i', "id");

            Parser.Command get = this.parser.addCommand("get", 'g');
            get.addSwitch('i', "id");

            Parser.Command replies = this.parser.addCommand("replies", 'r');
            replies.addSwitch('i', "id");

            Parser.Command search = this.parser.addCommand("search", 's');
            search.addSwitch('f', "file");
            search.addSwitch('t', "type");

            this.parser.addCommand("quit", 'q');
            this.parser.addCommand("exit", 'x');

            this.parser.addCommand("status", 't');

            this.parser.addCommand("version", 'v');

            this.parser.addCommand("visible", 'V');
        } catch (Parser.ParserException pe) {
            this.logger.error("Unable to initialize command line parser", pe);
            throw new RuntimeException(pe);
        }
    }

    /**
     * Start the command line user interface.
     */
    public void start () {
        System.out.println("\nKALAHARI NOMAD\n");

        boolean run = true;

        while (run) {
            System.out.print(CommandLine.PROMPT);
            String input = this.scanner.nextLine();
            try {
                run = this.parseInput(input);
            } catch (Parser.UnknownCommandException uce) {
                System.err.println("Command not found: " + input.trim()
                        + ". For help type 'help'.");
            } catch (Parser.ParserException pe) {
                System.err.println(pe.getMessage());
            }
        }

        // some shutdown code can go here.
        System.exit(0);
    }

    /**
     * This method parses the user input and performs the desired action.
     *
     * @param input
     *            The user command string to be executed.
     * @return true unless a quit command is issued.
     * @throws ParserException
     */
    public boolean parseInput (String input) throws Parser.ParserException {

        // Quick check to prevent parsing non-empty input.
        if (input.trim().isEmpty()) {
            return true;
        }

        this.parser.parse(input);

        if (this.parser.isCommand("quit") || this.parser.isCommand("exit")) {
            return false;
        } else if (this.parser.isCommand("network")) {
            Status status = Status.getInstance();
            NomadStatus nomadStatus = status.getNomadStatus();
            Map<UUID, HeartBeat> nomads = nomadStatus.getNomads();
            for (UUID id : nomads.keySet()) {
                System.out.println("UUID [" + id + "] URN ["
                        + nomads.get(id).getUnicastUrn() + "]");
            }
        } else if (this.parser.isCommand("ping")) {
            Command ping;
            String id = this.parser.getSwitchValue("id");
            if (null != id) {
                Status status = Status.getInstance();
                NomadStatus nomadStatus = status.getNomadStatus();
                Map<UUID, HeartBeat> nomads = nomadStatus.getNomads();
                HeartBeat heartBeat = nomads.get(UUID.fromString(id));
                String urn = heartBeat.getUnicastUrn();
                ping = new PingNomad(urn);
            } else {
                ping = new PingNomad();
            }
            ping.execute();
        } else if (this.parser.isCommand("visible")) {
            Command visible = new Visible();
            visible.execute();
            System.out.println("Visible mode set");
        } else if (this.parser.isCommand("invisible")) {
            Command invisible = new Invisible();
            invisible.execute();
            System.out.println("Invisible mode set");
        } else if (this.parser.isCommand("search")) {
            String searchTerm = this.parser.getSwitchValue("file");
            // String type = this.parser.getSwitchValue("type");
            FileDescriptor file = new FileDescriptor();
            file.setSearchString(searchTerm);
            Command search = new FileSearch(file);
            search.execute();
        } else if (this.parser.isCommand("send")) {
            System.out.println("Send request sent...");
        } else if (this.parser.isCommand("status")) {
            int replies = Status.getInstance().getReplyStatus()
                    .getTotalReplyCount();
            if (replies > 0) {
                System.out.println("Search Replies: " + replies);
            }
            if (Status.getInstance().getNetworkStatus().isNetworkAvailable()) {
                System.out.println("Network: Connected");
            } else {
                System.out.println("Network: Offline");
            }
            /*
             * if (Listener.instance().isListening()) {
             * System.out.println("Listening: Yes"); } else {
             * System.out.println("Listening: No"); }
             */
            System.out.println("Transfers: 0"); // TODO
            System.out.println("Connections: "
                    + Status.getInstance().getNomadStatus()
                            .getNomadConnections());
            System.out.println("IP Address: "
                    + Status.getInstance().getNetworkStatus().getNomadIp()
                            .toString().replaceFirst("/", ""));
            System.out.println("Nomad ID: "
                    + Status.getInstance().getNomadStatus().getNomadId());
            System.out.println("URN: "
                    + Status.getInstance().getNomadStatus().getUnicastUrn());
        } else if (this.parser.isCommand("version")) {
            Configure conf = Configure.instance();
            String version = conf.get("version.verbose");
            System.out.println(version);
        } else if (this.parser.isCommand("help")) {
            this.printUsage();
        } else if (this.parser.isCommand("replies")) {
            Status status = Status.getInstance();
            RequestStatus reqStatus = status.getRequestStatus();
            // ReplyStatus replyStatus = status.getReplyStatus();
            // Set<Integer> searchIds = replyStatus.getSearchIds();
            System.out.println("Total searches: "
                    + reqStatus.getRequestIds().size());
            // System.out.println(searchIds);
            for (Integer id : reqStatus.getRequestIds()) {
                String print = reqStatus.getRequest(id).getPrettyPrint();
                System.out.println(print);
            }

            /*
             * if (null != this.parser.getSwitchValue("id")) { int id =
             * this.parser.getSwitchValueAsInt("id");
             * System.out.println(replyStatus.getReplies(id)); }
             */
        }

        return true;
    }

    /**
     * Print usage commands.
     */
    public void printUsage () {
        System.out.println("COMMANDS\n");

        // Get file command
        System.out.println("\tget, g\n\t\tGet a specified file.");
        System.out.println("\t\tSynopsis: get -i <file id> ");
        System.out.println("\t\t\tArguments:");
        System.out.println("\t\t\t[-i|--id] <file ID>");
        System.out.println("\t\t\t\tSpecify file ID to get.");

        // Help
        System.out.println("\thelp, h\n\t\tDisplay this help page\n");

        // Invisible
        System.out.println("\tinvisible, i\n\t\tSet invisible mode\n");

        // Netowrk view
        System.out.println("\tnetwork, n\n\t\tView nomads in network\n");

        // Ping
        System.out.println("\tping, p\n\t\tPing a specified nomad.");
        System.out.println("\t\tSynopsis: ping -i <Nomad ID>");
        System.out.println("\t\t\tArguments:");
        System.out.println("\t\t\t[-i|--id] <Nomad ID>");

        // Quit
        System.out.println("\tquit, q\n\t\tTerminate nomad and cancel "
                + "all transfers\n");

        // Replies
        System.out.println("\treplies, r\n\t\tGet list of search replies.");
        System.out.println("\t\tSynopsis: replies [-i <reply ID>]");
        System.out.println("\t\t\tArguments:");
        System.out.println("\t\t\t[-i|--id] <reply ID>");
        System.out.println("\t\t\t\tGet replies to a particular search ID.");

        // Search
        System.out.println("\tsearch, s\n\t\tSearch for files.");
        System.out.println("\t\tSynopsis: search -f <file name> "
                + "[-t <option>]");
        System.out.println("\t\t\tArguments:");
        System.out.println("\t\t\t[-f|--file] <file name>");
        System.out.println("\t\t\t\tSpecify file name to search by.");
        System.out
                .println("\t\t\t[-t|--type] [a(udio)|v(ideo)|i(mage)|d(ocument)"
                        + "|p(rogram)]");
        System.out.println("\t\t\t\tSpecify file type. If not used all "
                + "file types will be searched.\n");

        // Status
        System.out.println("\tstatus, t\n\t\tShow nomad status\n");

        // Version
        System.out
                .println("\tversion, v\n\t\tDisplay nomad version" + "\t\t\n");

        // Visible
        System.out.println("\tvisible, V\n\t\tSet visible mode\n");
    }

}
