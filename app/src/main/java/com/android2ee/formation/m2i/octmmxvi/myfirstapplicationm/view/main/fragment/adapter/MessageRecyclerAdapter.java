/**
 * <ul>
 * <li>MessageRecyclerAdapter</li>
 * <li>com.android2ee.formation.m2i.octmmxvi.myfirstapplicationm.view.main.fragment.adapter</li>
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

package com.android2ee.formation.m2i.octmmxvi.myfirstapplicationm.view.main.fragment.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android2ee.formation.m2i.octmmxvi.myfirstapplicationm.R;
import com.android2ee.formation.m2i.octmmxvi.myfirstapplicationm.dao.MessageDao;
import com.android2ee.formation.m2i.octmmxvi.myfirstapplicationm.transverse.model.Message;

import java.util.ArrayList;

/**
 * Created by Mathias Seguy - Android2EE on 13/10/2016.
 */
public class MessageRecyclerAdapter extends RecyclerView.Adapter<MessageRecyclerAdapter.MessRecyclerViewHolder> {
    private static final String TAG = "MessageRecyclerAdapter";
    private static final int ITEM_ODD=1;
    private static final int ITEM_EVEN=0;
    private ArrayList<Message> dataset;
    LayoutInflater inflater;
    Message messageTemp;
    MessRecyclerViewHolder viewHolderTemp;
    View rowView;
    MessageRecyclerViewParentIntf parent;

    public MessageRecyclerAdapter(Context ctx, ArrayList<Message> dataset,
                                  MessageRecyclerViewParentIntf parent){
        this.dataset=dataset;
        inflater=LayoutInflater.from(ctx);
        this.parent=parent;
    }

    @Override
    public MessRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        rowView=inflater.inflate(R.layout.item_even,parent,false);
        if(viewType==ITEM_EVEN){
            rowView=inflater.inflate(R.layout.item_even,parent,false);
        }else{
            rowView=inflater.inflate(R.layout.item_odd,parent,false);
        }
        viewHolderTemp=new MessRecyclerViewHolder(rowView);
        return viewHolderTemp;
    }

    @Override
    public void onBindViewHolder(MessRecyclerViewHolder holder, int position) {
        messageTemp=dataset.get(position);
        holder.getTxvName().setText(messageTemp.getName());
        holder.getTxvMessage().setText(messageTemp.getMessage());
        holder.getTxvTelephone().setText(messageTemp.getTel());
        holder.getImvPicture().setBackgroundResource(messageTemp.getPictureId());
    }
    @Override
    public int getItemViewType(int position) {
        return dataset.get(position).getName().equals(Message.TOTO)?ITEM_EVEN:ITEM_ODD;
    }
    @Override
    public int getItemCount() {
        return dataset.size();
    }
    public void updateDataSet(ArrayList<Message> list){
        dataset.clear();
        for(Message mes:list){
            dataset.add(mes);
        }
        notifyItemRangeInserted(0,list.size());
    }
    /**
     * Delete item at the specified position
     * @param position
     */
    public void deleteItemAt(int position){
        Log.e(TAG,"deleItemAt: deleteAsync called on a message in DB");
        MessageDao.getInstance().deleteAsync(dataset.get(position));
        dataset.remove(position);
        notifyItemRemoved(position);
    }

    public void deleteThemAll(){
        int size=dataset.size();
        for(Message mes:dataset){
            MessageDao.getInstance().deleteAsync(mes);
        }
        dataset.clear();
        notifyItemRangeRemoved(0,size);
    }
    public void itemSelectedAt(int position){
        //tell your parent an item has been selected
        parent.itemSelectedAt(position);
    }
    Message realMessage;
    public void addWithoutSavingInDB(Message mes){
        dataset.add(mes);
        //notify change
        notifyItemInserted(dataset.size()-1);
    }
    public void add(Message mes){
        dataset.add(mes);
        //notify change
        notifyItemInserted(dataset.size()-1);
        MessageDao.getInstance().saveOrUpdateAsync(mes);
    }
    public Message getItemAt(int position){
        return dataset.get(position);
    }
    /***********************************************************
    * ViewHolder pattern
     **********************************************************/

    public class MessRecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView txvName;
        TextView txvTelephone;
        TextView txvMessage;
        ImageView imvPicture;
        ImageView imvDelete;
        public MessRecyclerViewHolder(View itemView) {
            super(itemView);
            //initialize the graphical components
            txvName= (TextView) itemView.findViewById(R.id.txvName);
            txvTelephone= (TextView) itemView.findViewById(R.id.txvTelephone);
            txvMessage= (TextView) itemView.findViewById(R.id.txvMessage);
            imvPicture= (ImageView) itemView.findViewById(R.id.imvPicture);
            imvDelete= (ImageView) itemView.findViewById(R.id.imvDeletee);
            imvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteCurrentItem();
                }
            });
            ((LinearLayout) itemView.findViewById(R.id.lilItemMain)).setOnClickListener(new View.OnClickListener() {
                 @Override
                public void onClick(View v) {
                    itemSelected();
                }
            });
        }
        private void deleteCurrentItem(){
            deleteItemAt(getAdapterPosition());
        }

        private void itemSelected(){
            itemSelectedAt(getAdapterPosition());
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


    }
}
