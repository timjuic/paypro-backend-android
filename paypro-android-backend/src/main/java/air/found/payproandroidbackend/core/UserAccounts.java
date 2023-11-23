package air.found.payproandroidbackend.core;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "user_accounts", schema = "public", catalog = "air_db")
public class UserAccounts {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id", nullable = false)
    private int userId;
    @Basic
    @Column(name = "email_address", nullable = false, length = 40)
    private String emailAddress;
    @Basic
    @Column(name = "password", nullable = false, length = 15)
    private String password;
    @Basic
    @Column(name = "is_confirmed", nullable = true)
    private Boolean isConfirmed;
    @Basic
    @Column(name = "is_admin", nullable = true)
    private Boolean isAdmin;
    @Basic
    @Column(name = "created_at", nullable = true)
    private Timestamp createdAt;
    @Basic
    @Column(name = "forgot_password_code", nullable = true, length = 6)
    private String forgotPasswordCode;
    @Basic
    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;
    @Basic
    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;
    @OneToMany(mappedBy = "userAccountsByUserUserId")
    private Collection<UserMerchants> userMerchantsByUserId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        isConfirmed = confirmed;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getForgotPasswordCode() {
        return forgotPasswordCode;
    }

    public void setForgotPasswordCode(String forgotPasswordCode) {
        this.forgotPasswordCode = forgotPasswordCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccounts that = (UserAccounts) o;
        return userId == that.userId && Objects.equals(emailAddress, that.emailAddress) && Objects.equals(password, that.password) && Objects.equals(isConfirmed, that.isConfirmed) && Objects.equals(isAdmin, that.isAdmin) && Objects.equals(createdAt, that.createdAt) && Objects.equals(forgotPasswordCode, that.forgotPasswordCode) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, emailAddress, password, isConfirmed, isAdmin, createdAt, forgotPasswordCode, firstName, lastName);
    }

    public Collection<UserMerchants> getUserMerchantsByUserId() {
        return userMerchantsByUserId;
    }

    public void setUserMerchantsByUserId(Collection<UserMerchants> userMerchantsByUserId) {
        this.userMerchantsByUserId = userMerchantsByUserId;
    }
}
