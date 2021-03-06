package com.ezypayinc.ezypay.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmList;
import io.realm.RealmObject;

public class User extends RealmObject implements Parcelable {

    private int id;
    private String name;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
    private String token;
    private int userType;
    private int customerId;
    private String avatar;
    private User employeeBoss;
    private RealmList<Card> cards;
    private Credentials credentials;
    private static final String EMPTY_STRING = " ";

    public User() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public RealmList<Card> getCards() {
        return cards;
    }

    public void setCards(RealmList<Card> cards) {
        this.cards = cards;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public User getEmployeeBoss() {
        return employeeBoss;
    }

    public void setEmployeeBoss(User employeeBoss) {
        this.employeeBoss = employeeBoss;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public String getFullName() {
        if(name != null || lastName != null) {
            StringBuilder builder = new StringBuilder();
            builder.append(name).
                    append(EMPTY_STRING).
                    append(lastName);
            return builder.toString();
        }
        return null;
    }


    public boolean equals(Object x) {
        User user = (User) x;
        String fullName = user.getName() + EMPTY_STRING + user.getLastName();
        if(fullName.equalsIgnoreCase(name + EMPTY_STRING + lastName)) {
            return true;
        }
        return false;
    }

    public User(Parcel in) {
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(lastName);
        parcel.writeString(phoneNumber);
        parcel.writeString(email);
        parcel.writeString(password);
        parcel.writeString(token);
        parcel.writeParcelable(employeeBoss, i);
        parcel.writeTypedList(cards);
    }

    private void readFromParcel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        lastName = in.readString();
        phoneNumber = in.readString();
        email = in.readString();
        password = in.readString();
        token = in.readString();
        employeeBoss = in.readParcelable(User.class.getClassLoader());
        if (cards != null ) {
            in.readTypedList(cards, Card.CREATOR);
        }
    }

    public static Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>(){
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
