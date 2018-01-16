package network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by usman on 7/3/17.
 */

public class WeatherRequest {

    @SerializedName("user")
    @Expose
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public class User {
        @SerializedName("device_token")
        @Expose
        private String device_token;
        @SerializedName("user_id")
        @Expose
        private int user_id;

        @SerializedName("after_four_hours")
        @Expose
        private boolean after_four_hours;

        @SerializedName("daily")
        @Expose
        private boolean daily;

        @SerializedName("weekly")
        @Expose
        private boolean weekly;

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public boolean isAfter_four_hours() {
            return after_four_hours;
        }

        public void setAfter_four_hours(boolean after_four_hours) {
            this.after_four_hours = after_four_hours;
        }

        public boolean isDaily() {
            return daily;
        }

        public void setDaily(boolean daily) {
            this.daily = daily;
        }

        public boolean isWeekly() {
            return weekly;
        }

        public void setWeekly(boolean weekly) {
            this.weekly = weekly;
        }

        public String getDevice_token() {
            return device_token;
        }

        public void setDevice_token(String device_token) {
            this.device_token = device_token;
        }
    }
}