package network;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class LoginResponse2 extends ServerResponse {
    private UserData Data;

    public UserData getData() {
        return Data;
    }

    public void setData(UserData data) {
        this.Data = data;
    }

    public class UserData {

        private Integer UserId;
        private Integer AgentId;
        private Integer AgencyId;
        private String UserName;
        private String Email;
        private String CreateDate;
        private String Updatedate;
        private String SFID;
        private Boolean IsActive;
        private Boolean IsDisabled;
        private Boolean IsLockedOut;
        private String LastLoginDate;
        private String LastPasswordChangedDate;
        private String LastLockoutDate;
        private String UserGuid;
        private String SocialId;
        private String Token;
        //        private Integer UserRoleIds;
        private List<Integer> UserRoleIds = new ArrayList<Integer>();
        private String FistName;
        private String LastName;
        private String ProfileImage;
        private String PhoneNumber;
        private String MobileNumber;
        private String PostCode;
        private String Username;

        private Droid Droid;

        public Droid getDroid() {
            return Droid;
        }

        public void setDroid(Droid droid) {
            this.Droid = droid;
        }

        public class Droid {
            private String droid_public_app_version;
            private boolean droid_public_force_update;
            private String UpdateMessage;
            private String droid_public_vdo_version;
            private boolean droid_public_vdo_show;
            private String droid_public_vdo_url;

            public String getDroid_public_app_version() {
                return droid_public_app_version;
            }

            public void setDroid_public_app_version(String droid_public_app_version) {
                this.droid_public_app_version = droid_public_app_version;
            }

            public boolean isDroid_public_force_update() {
                return droid_public_force_update;
            }

            public void setDroid_public_force_update(boolean droid_public_force_update) {
                this.droid_public_force_update = droid_public_force_update;
            }

            public String getUpdateMessage() {
                return UpdateMessage;
            }

            public void setUpdateMessage(String updateMessage) {
                UpdateMessage = updateMessage;
            }

            public String getDroid_public_vdo_version() {
                return droid_public_vdo_version;
            }

            public void setDroid_public_vdo_version(String droid_public_vdo_version) {
                this.droid_public_vdo_version = droid_public_vdo_version;
            }

            public boolean isDroid_public_vdo_show() {
                return droid_public_vdo_show;
            }

            public void setDroid_public_vdo_show(boolean droid_public_vdo_show) {
                this.droid_public_vdo_show = droid_public_vdo_show;
            }

            public String getDroid_public_vdo_url() {
                return droid_public_vdo_url;
            }

            public void setDroid_public_vdo_url(String droid_public_vdo_url) {
                this.droid_public_vdo_url = droid_public_vdo_url;
            }
        }


        public Integer getUserId() {
            return UserId;
        }

        public void setUserId(Integer UserId) {
            this.UserId = UserId;
        }

        public Integer getAgentId() {
            return AgentId;
        }

        public void setAgentId(Integer AgentId) {
            this.AgentId = AgentId;
        }

        public Integer getAgencyId() {
            if (AgencyId == null) {
                AgencyId = 0;
            }
            return AgencyId;
        }

        public void setAgencyId(Integer AgencyId) {
            this.AgencyId = AgencyId;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public String getEmail() {
            return Email;
        }

        public void setEmail(String Email) {
            this.Email = Email;
        }

        public String getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(String CreateDate) {
            this.CreateDate = CreateDate;
        }

        public String getUpdatedate() {
            return Updatedate;
        }

        public void setUpdatedate(String Updatedate) {
            this.Updatedate = Updatedate;
        }

        public String getSFID() {
            return SFID;
        }

        public void setSFID(String SFID) {
            this.SFID = SFID;
        }

        public Boolean getIsActive() {
            return IsActive;
        }

        public void setIsActive(Boolean IsActive) {
            this.IsActive = IsActive;
        }

        public Boolean getIsDisabled() {
            return IsDisabled;
        }

        public void setIsDisabled(Boolean IsDisabled) {
            this.IsDisabled = IsDisabled;
        }

        public Boolean getIsLockedOut() {
            return IsLockedOut;
        }

        public void setIsLockedOut(Boolean IsLockedOut) {
            this.IsLockedOut = IsLockedOut;
        }

        public String getLastLoginDate() {
            return LastLoginDate;
        }

        public void setLastLoginDate(String LastLoginDate) {
            this.LastLoginDate = LastLoginDate;
        }

        public String getLastPasswordChangedDate() {
            return LastPasswordChangedDate;
        }

        public void setLastPasswordChangedDate(String LastPasswordChangedDate) {
            this.LastPasswordChangedDate = LastPasswordChangedDate;
        }

        public String getLastLockoutDate() {
            return LastLockoutDate;
        }

        public void setLastLockoutDate(String LastLockoutDate) {
            this.LastLockoutDate = LastLockoutDate;
        }

        public String getUserGuid() {
            return UserGuid;
        }

        public void setUserGuid(String UserGuid) {
            this.UserGuid = UserGuid;
        }

        public String getSocialId() {
            return SocialId;
        }

        public void setSocialId(String SocialId) {
            this.SocialId = SocialId;
        }

        public String getToken() {
            return Token;
        }

        public void setToken(String Token) {
            this.Token = Token;
        }

        public List<Integer> getUserRoleIds() {
            return UserRoleIds;
        }

        public void setUserRoleIds(List<Integer> userRoleIds) {
            UserRoleIds = userRoleIds;
        }

        public String getFistName() {
            return FistName;
        }

        public void setFistName(String FistName) {
            this.FistName = FistName;
        }

        public String getLastName() {
            return LastName;
        }

        public void setLastName(String LastName) {
            this.LastName = LastName;
        }

        public String getProfileImage() {
            return ProfileImage;
        }

        public void setProfileImage(String ProfileImage) {
            this.ProfileImage = ProfileImage;
        }

        public String getPhoneNumber() {
            return PhoneNumber;
        }

        public void setPhoneNumber(String PhoneNumber) {
            this.PhoneNumber = PhoneNumber;
        }

        public String getMobileNumber() {
            return MobileNumber;
        }

        public void setMobileNumber(String MobileNumber) {
            this.MobileNumber = MobileNumber;
        }

        public String getPostCode() {
            return PostCode;
        }

        public void setPostCode(String PostCode) {
            this.PostCode = PostCode;
        }

        public String getUsername() {
            return Username;
        }

        public void setUsername(String Username) {
            this.Username = Username;
        }

    }
}
