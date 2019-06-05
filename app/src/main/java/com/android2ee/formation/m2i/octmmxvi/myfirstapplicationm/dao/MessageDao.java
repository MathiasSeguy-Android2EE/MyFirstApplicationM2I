/**
 * <ul>
 * <li>MessageDao</li>
 * <li>com.android2ee.formation.m2i.octmmxvi.myfirstapplicationm.dao</li>
 * <li>13/10/2016</li>
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

package com.android2ee.formation.m2i.octmmxvi.myfirstapplicationm.dao;

import android.util.Log;

import com.android2ee.formation.m2i.octmmxvi.myfirstapplicationm.transverse.event.MessageAddedEvent;
import com.android2ee.formation.m2i.octmmxvi.myfirstapplicationm.transverse.model.Message;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * Created by Mathias Seguy - Android2EE on 13/10/2016.
 */
public class MessageDao {
    private static final String TAG = "MessageDao";
    private static MessageDao instance=null;
    public static MessageDao getInstance(){
        if(instance==null){
            instance=new MessageDao();
        }
        return instance;
    }
    private MessageDao(){
        //private
    }
    /***********************************************************
     *  CRUD methods
     **********************************************************/

    /***********************************************************
     *  Deleting
     **********************************************************/

    /**
     * Delete an Event message in an async way
     * @param message
     */
    public void deleteAsync(Message message){
        EventBus.getDefault().register(this);
        EventBus.getDefault().post(new CRUDDaoEvent((message)));
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void delete(CRUDDaoEvent event){
        EventBus.getDefault().unregister(this);
        event.getMessage().delete();
    }

    /**
     * The event specific for delegating deletion to background thread
     */
    public class CRUDDaoEvent {
        Message message;

        public Message getMessage() {
            return message;
        }

        public CRUDDaoEvent(Message messageJustAdded) {
            this.message = messageJustAdded;
        }

        public void setMessage(Message message) {
            this.message = message;
        }
    }
    /***********************************************************
     * Finding them all
     **********************************************************/
    public void findAllAsync(){
        Log.e(TAG, "findAllAsync() called");
        EventBus.getDefault().register(this);
        EventBus.getDefault().post(new FindAllDaoEvent());
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void findAll(FindAllDaoEvent event){
        Log.e(TAG, "findAll() called with: event = [" + event + "]");
        EventBus.getDefault().unregister(this);
        ArrayList<Message> list=(ArrayList<Message>) Message.listAll(Message.class);
        EventBus.getDefault().post(new ElementsLoadedEvent(list));
    }
    /**
     * The event specific for delegating found to background thread
     */
    public class FindAllDaoEvent {
        public FindAllDaoEvent() {

        }
    }
    /**
     * The event specific for delegating found to background thread
     * I's the answer event
     */
    public class ElementsLoadedEvent {
        ArrayList<Message> list;
        public ElementsLoadedEvent(ArrayList<Message> list) {
            this.list=list;
        }

        public ArrayList<Message> getList() {
            return list;
        }

        public void setList(ArrayList<Message> list) {
            this.list = list;
        }
    }
    /***********************************************************
     * Saving
     **********************************************************/
    public void saveOrUpdateAsync(Message message){
        EventBus.getDefault().register(this);
        EventBus.getDefault().post(new SaveDaoEvent(message));
    }
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void saveOrUpdate(SaveDaoEvent event){
        EventBus.getDefault().unregister(this);
        event.getMessage().save();
        EventBus.getDefault().post(new MessageAddedEvent(event.getMessage()));
    }

    /**
     *
     */
    public class SaveDaoEvent extends CRUDDaoEvent{

        public SaveDaoEvent(Message messageJustAdded) {
            super(messageJustAdded);
        }
    }
}
