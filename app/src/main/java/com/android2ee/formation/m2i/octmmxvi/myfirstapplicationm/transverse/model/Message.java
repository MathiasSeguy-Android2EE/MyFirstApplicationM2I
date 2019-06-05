/**
 * <ul>
 * <li>Message</li>
 * <li>com.android2ee.formation.m2i.octmmxvi.myfirstapplicationm.transverse.model</li>
 * <li>11/10/2016</li>
 * <p>
 * <li>======================================================</li>
 * <p>
 * <li>Projet : Mathias Seguy Project</li>
 * <li>Produit par MSE.</li>
 * <p>
 * /**
 * <ul>
 * Android Tutorial, An <strong>Android2EE</strong>'s project.</br>
 * Produced by <strong>Dr. Mathias SEGUY</strong>.</br>
 * Delivered by <strong>http://android2ee.com/</strong></br>
 * Belongs to <strong>Mathias Seguy</strong></br>
 * ***************************************************************************************************************</br>
 * This code is free for any usage but can't be distribute.</br>
 * The distribution is reserved to the site <strong>http://android2ee.com</strong>.</br>
 * The intelectual property belongs to <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * <p>
 * *****************************************************************************************************************</br>
 * Ce code est libre de toute utilisation mais n'est pas distribuable.</br>
 * Sa distribution est reservée au site <strong>http://android2ee.com</strong>.</br>
 * Sa propriété intellectuelle appartient à <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * *****************************************************************************************************************</br>
 */

package com.android2ee.formation.m2i.octmmxvi.myfirstapplicationm.transverse.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.android2ee.formation.m2i.octmmxvi.myfirstapplicationm.R;
import com.orm.SugarRecord;

/**
 * Created by Mathias Seguy - Android2EE on 11/10/2016.
 */

public class Message extends SugarRecord implements Parcelable {
    public static final String TOTO = "Toto";
    /***********************************************************
     *  Attributes
     **********************************************************/
    /**
     * The name
     */
    String name;
    /**
     * The tel
     */
    String tel;
    /**
     * The message
     */
    String message;
    /**
     * The picture of the human
     */
    int pictureId;
    /***********************************************************
     *  Constructors
     **********************************************************/
    public Message(String message,int level) {
        this.message = message;
            name= TOTO;
            tel="0561495410";
            pictureId= R.drawable.ic_human_even;
    }

    /**
     * Default constructor
     * @param message
     * @param name
     * @param tel
     */
    public Message(String message, String name, String tel) {
        this.message = message;
        this.name = name;
        this.tel = tel;
    }

    public  Message(){

    }
    /***********************************************************
     *  Getters/Setters
     **********************************************************/
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPictureId() {
        return pictureId;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    protected Message(Parcel in) {
        name = in.readString();
        tel = in.readString();
        message = in.readString();
        pictureId = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(tel);
        dest.writeString(message);
        dest.writeInt(pictureId);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}
