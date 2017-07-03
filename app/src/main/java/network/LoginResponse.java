package network;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("serial")
public class LoginResponse extends ServerResponse {
    private Data data;

    public class Data {
        @SerializedName("X-API-AUTHID")
        private String authId;

        @SerializedName("X-API-AUTHKEY")
        private String authKey;

        @SerializedName("user_data")
        private UserData userData;


        public class UserData {
            @SerializedName("first_name")
            private String firstName;
            @SerializedName("last_name")
            private String lastName;
            @SerializedName("allow_sending")
            private int allowSending;

            public String getFirstName() {
                return firstName;
            }

            public String getLastName() {
                return lastName;
            }

            public int getAllowSending() {
                return allowSending;
            }

        }

        public String getAuthId() {
            return authId;
        }

        public String getAuthKey() {
            return authKey;
        }

        public UserData getUserData() {
            return userData;
        }

    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
