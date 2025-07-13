package im.fooding.core.global.infra.slack;

public final class SlackConstant {
    public static class ERROR {
        public static final String TITLE = "[Fooding] Error*";
        public static final String COLOR = "#ff0000";
        public static final String STATUS = "Status";
        public static final String CODE = "Error Code";
        public static final String MESSAGE = "Error Message";
        public static final String STACK_TRACE = "Stack Trace";
        public static final String TIME = "Timestamp";
        public static final String URL = "URL";
    }

    public static class NOTIFICATION {
        public static final String TITLE = "[Fooding]*";
        public static final String COLOR = "#36a64f";
        public static final String TIME = "Timestamp";
        public static final String MESSAGE = "Message";
    }
}
