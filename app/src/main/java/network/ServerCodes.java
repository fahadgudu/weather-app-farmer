package network;

public class ServerCodes {

    public static final class ServerRequestCodes {
        public static final int LOGIN_REQUEST_CODE = 10000;
        public static final int MSG_DELETE_REQUEST_CODE = 10001;
        public static final int MSG_INBOX_REQUEST_CODE = 10002;

        private ServerRequestCodes() {
        }
    }

    public static final class ServerStatusCodes {
        public static final String SERVER_CALL_SUCCESS = "success";
        public static final String SERVER_CALL_TRUE = "true";
        public static final String SERVER_CALL_ERROR = "error";

        private ServerStatusCodes() {
        }
    }

    public enum ServerResultTypes {
        Success(1), Failure(2), Empty(3), Warning(4), Information(5), ValidationError(
                6), Exception(7), CodeWord(8), Redirect(9), Function(10), Code(
                11), Duplicate(12), AccessRights(13), DeActivated(14);

        private int resultTypeValue;

        public int value() {
            return this.resultTypeValue;
        }

        ServerResultTypes(final int value) {
            this.resultTypeValue = value;
        }

        public static ServerResultTypes getResultType(final int ordinal) {
            return ServerResultTypes.values()[ordinal];
        }
    }
}
