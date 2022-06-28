package task06;

import java.io.IOException;
import java.util.logging.*;

public class EventLogger extends Logger {
    Formatter frm = new Formatter() {
        @Override public String format(LogRecord r) {
            return r.getMessage() + "\n";
        }
    };

    public EventLogger(String name, String resourceBundleName) {
        super(name, resourceBundleName);
        setUseParentHandlers(false);

        Handler console = new ConsoleHandler();
        console.setFormatter(frm);
        addHandler(console);

        try {
            Handler file = new FileHandler("log.log");
            file.setFormatter(frm);
            addHandler(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void closeHandlers() { for (Handler h : getHandlers()) h.close(); }
}