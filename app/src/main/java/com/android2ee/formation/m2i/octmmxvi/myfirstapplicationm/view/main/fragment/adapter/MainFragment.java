/**
 * <ul>
 * <li>MainFragment</li>
 * <li>com.android2ee.formation.m2i.octmmxvi.myfirstapplicationm.view.main.fragment.adapter</li>
 * <li>14/10/2016</li>
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

package com.android2ee.formation.m2i.octmmxvi.myfirstapplicationm.view.main.fragment.adapter;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android2ee.formation.m2i.octmmxvi.myfirstapplicationm.R;
import com.android2ee.formation.m2i.octmmxvi.myfirstapplicationm.dao.MessageDao;
import com.android2ee.formation.m2i.octmmxvi.myfirstapplicationm.transverse.event.MessageAddedEvent;
import com.android2ee.formation.m2i.octmmxvi.myfirstapplicationm.transverse.model.Message;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * Created by Mathias Seguy - Android2EE on 14/10/2016.
 */
public class MainFragment extends Fragment implements MessageRecyclerViewParentIntf{
    private static final String TAG = "MainFragment";
    public static final String EDT_MESSAGE = "EDT_MESSAGE";
    public static final String RESULT = "RESULT";
    public static final String ALERT_DIALOG_FRAGMENT = "AlertDialogFragment";
    /***********************************************************
     * Attributes
     **********************************************************/
    /**
     * The button to add an element
     */
    Button btnAdd;
    /**
     * The EditText
     */
    EditText edtMessage;
    /**
     * The Result Area
     */
    RecyclerView rcvResult;
    /**
     * The messages displayed
     */
    ArrayList<Message> messages;
    /**
     * The arrayadapter of the lsvresult
     */
    MessageRecyclerAdapter arrayAdapter;
    /**
     * Animation when pushing the button btnAdd
     */
    AnimatorSet btnAddAnim;
    /**
     * The AlertDialog as a fragment
     */
    Fragment alertDialogFragment;
    /***********************************************************
    *  Managing LifeCycle
    **********************************************************/
    public MainFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //instanciate the view
        View view=inflater.inflate(R.layout.fragment_main,container,false);
        //instantiate graphical components
        btnAdd = (Button) view.findViewById(R.id.btnAdd);
        edtMessage = (EditText) view.findViewById(R.id.edtMessage);
        rcvResult = (RecyclerView) view.findViewById(R.id.rcvResult);
        if(getResources().getBoolean(R.bool.postICS)) {
            btnAddAnim = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.bump);
            btnAddAnim.setTarget(btnAdd);
        }
        messages=new ArrayList<>();

        arrayAdapter=new MessageRecyclerAdapter(getActivity(),messages,this);
        rcvResult.setAdapter(arrayAdapter);
        rcvResult.setLayoutManager(new LinearLayoutManager(getActivity()));
        //add listeners
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMessageInResult();
            }
        });
        if(savedInstanceState!=null){
            edtMessage.setText(savedInstanceState.getString(EDT_MESSAGE));
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        //initialize elements of the list view
        MessageDao.getInstance().findAllAsync();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EDT_MESSAGE,edtMessage.getText().toString());
        outState.putParcelableArrayList(RESULT,messages);
    }


    /***********************************************************
     *  Receiving events
     **********************************************************/

    /**
     * When an element is added from service layer itself
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageAddedEvent event){
        //
        if(waitingForAddedEvent){
            waitingForAddedEvent=false;
        }else{
            arrayAdapter.addWithoutSavingInDB(event.getMessageJustAdded());
        }
    }

    /**
     * When the list of the elements are finally loaded from DB
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageDao.ElementsLoadedEvent event){
        Log.e(TAG, "onEvent() called with: event = [" + event + "]");
        //
        arrayAdapter.updateDataSet(event.getList());
    }

    /***********************************************************
     *  Managing the AlertDialog
     **********************************************************/

    @SuppressLint("ValidFragment")
    public class AlertDialogFragment extends DialogFragment{
        public AlertDialogFragment(){}

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
            builder
                    .setMessage(getString(R.string.alertdialog_ad,messageTemp))
                    .setTitle("Should avoid title and hard coded string")
                    .setPositiveButton(getString(R.string.alertdialog_ad_ok),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    copyItemInEdtMessage();
                                }
                            })
                    .setNegativeButton(getString(R.string.alertdialog_ad_nok),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    doNOTCOPY();
                                }
                            })
            ;
            return builder.create();
        }

        @Override
        public void onResume() {
            ((AlertDialog)getDialog()).setMessage(getString(R.string.alertdialog_ad,messageTemp));
            super.onResume();
        }
    }
    /***********************************************************
     *  Managing Menu
     **********************************************************/

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.ic_action_delete:
                //do your stuff
                deleteThemAll();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /***********************************************************
     *  Business Methods
     **********************************************************/
    /**
     * Temp var for the business methods
     */
    private String messageTemp,messageForUndo;
    boolean waitingForAddedEvent=false;

    /**
     * Copy the content of the edittext in the Result area
     */
    @SuppressLint("NewApi")
    private void addMessageInResult(){
        //launch animation
        if(getResources().getBoolean(R.bool.postICS)) {
            btnAddAnim.start();
        }
        //copy the value of the edtMessage in the result area
        messageTemp=edtMessage.getText().toString();
//        messages.add(message);
//        arrayAdapter.notifyDataSetChanged();
        waitingForAddedEvent=true;
        arrayAdapter.add(new Message(messageTemp,messages.size()));
        edtMessage.setText("");
    }

    /**
     * Copy the item selected in the edtMessaqe
     */
    private void copyItemInEdtMessage(){
        messageForUndo=edtMessage.getText().toString();
        edtMessage.setText(messageTemp);
        Snackbar.make(edtMessage,"Message copied in EdtText",Snackbar.LENGTH_SHORT)
                .setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        undoCopyPaste();
                    }
                })
                .show();
    }

    private void undoCopyPaste(){
        edtMessage.setText(messageForUndo);
    }

    private void doNOTCOPY(){
        //do a stuff
        Toast.makeText(getActivity(),
                getString(R.string.toast_doNotCopy),
                Toast.LENGTH_SHORT)
                .show();
    }


    private void deleteThemAll(){

        arrayAdapter.deleteThemAll();

    }
    /**
     * Ok dude an item has been selected do a stuff
     */
    @Override
    public void itemSelectedAt(int posiiton) {
        messageTemp=arrayAdapter.getItemAt(posiiton).getMessage();
        FragmentManager fManager=getFragmentManager();
        alertDialogFragment=fManager.findFragmentByTag(ALERT_DIALOG_FRAGMENT);
        if(alertDialogFragment==null){
            alertDialogFragment=new AlertDialogFragment();
        }
        fManager.beginTransaction().show(alertDialogFragment).commit();

    }
}
