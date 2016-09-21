package com.ramkumar.cordovaplugins.sms;

import android.app.ListFragment;
import android.os.Bundle;
import android.widget.ArrayAdapter;


import com.shinobicontrols.messageme.models.Conversation;
import com.shinobicontrols.messageme.models.DataProvider;
import com.shinobicontrols.messageme.models.Message;

import java.util.Observable;
import java.util.Observer;

public class ConversationDetailFragment extends ListFragment implements Observer {
    public static final String ARG_ITEM_ID = "item_id";
    private Conversation mItem;
    public ConversationDetailFragment() {
    }
    private ArrayAdapter<Message> messageArrayAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = DataProvider.getInstance().getConversationMap().get(getArguments().getString(ARG_ITEM_ID));
            messageArrayAdapter = new ArrayAdapter<Message>(
                    getActivity(),
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    mItem.getMessages()
            );
            setListAdapter(messageArrayAdapter);
        }
        DataProvider.getInstance().addObserver(this);
    }

    @Override
    public void onDestroy() {
        DataProvider.getInstance().deleteObserver(this);
        super.onDestroy();
    }

    @Override
    public void update(Observable observable, Object data) {
        if(messageArrayAdapter != null) {
            messageArrayAdapter.notifyDataSetChanged();
        }
    }
}