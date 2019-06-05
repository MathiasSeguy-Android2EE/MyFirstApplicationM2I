/**
 * <ul>
 * <li>MessageArrayAdapter</li>
 * <li>com.android2ee.formation.m2i.octmmxvi.myfirstapplicationm.view.main.fragment.adapter</li>
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

package com.android2ee.formation.m2i.octmmxvi.myfirstapplicationm.view.main.fragment.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android2ee.formation.m2i.octmmxvi.myfirstapplicationm.R;
import com.android2ee.formation.m2i.octmmxvi.myfirstapplicationm.transverse.model.Message;

import java.util.ArrayList;

/**
 * Created by Mathias Seguy - Android2EE on 11/10/2016.
 */
public class MessageArrayAdapter extends ArrayAdapter<Message> {
    private static final int ITEM_ODD=1;
    private static final int ITEM_EVEN=0;
    /**
     * Inflater used to inflate the view in getView
     */
    LayoutInflater inflater;
    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     */
    public MessageArrayAdapter(Context context, ArrayList<Message> dataset) {
        super(context, R.layout.item_even,dataset);
        inflater=LayoutInflater.from(context);
    }

    /***********************************************************
     * GetView management
     **********************************************************/
    /**Avoid declaring object in getView*/
    View rowView;
    ViewHolder viewHolder;
    Message message;
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //creating the view if needed
        rowView=convertView;
        if(rowView==null){
            if(getItemViewType(position)==ITEM_EVEN){
                rowView=inflater.inflate(R.layout.item_even,parent,false);
            }else{
                rowView=inflater.inflate(R.layout.item_odd,parent,false);
            }
            viewHolder=new ViewHolder(rowView);
            rowView.setTag(viewHolder);
        }
        //and in all cases, update the view:
        message=getItem(position);
        viewHolder= (ViewHolder) rowView.getTag();
        viewHolder.getTxvName().setText(message.getName());
        viewHolder.getTxvMessage().setText(message.getMessage());
        viewHolder.getTxvTelephone().setText(message.getTel());
        viewHolder.getImvPicture().setBackgroundResource(message.getPictureId());
        viewHolder.setCurrentPosition(position);
        return rowView;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getName().equals(Message.TOTO)?ITEM_EVEN:ITEM_ODD;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    /**
     * Delete item at the specified position
     * @param position
     */
    public void deleteItemAt(int position){
        remove(getItem(position));
    }

    /***********************************************************
     *  ViewHolder's pattern
     **********************************************************/
    public class ViewHolder{
        TextView txvName;
        TextView txvTelephone;
        TextView txvMessage;
        ImageView imvPicture;
        ImageView imvDelete;
        View view;
        int currentPosition;

        public ViewHolder(View view) {
            this.view = view;
            //initialize the graphical components
            txvName= (TextView) view.findViewById(R.id.txvName);
            txvTelephone= (TextView) view.findViewById(R.id.txvTelephone);
            txvMessage= (TextView) view.findViewById(R.id.txvMessage);
            imvPicture= (ImageView) view.findViewById(R.id.imvPicture);
            imvDelete= (ImageView) view.findViewById(R.id.imvDeletee);
            imvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteCurrentItem();
                }
            });
        }
        private void deleteCurrentItem(){
            deleteItemAt(currentPosition);
        }

        public ImageView getImvPicture() {
            return imvPicture;
        }

        public TextView getTxvMessage() {
            return txvMessage;
        }

        public TextView getTxvName() {
            return txvName;
        }

        public TextView getTxvTelephone() {
            return txvTelephone;
        }

        public void setCurrentPosition(int currentPosition) {
            this.currentPosition = currentPosition;
        }
    }
}
