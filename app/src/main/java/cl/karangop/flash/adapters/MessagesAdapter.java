package cl.karangop.flash.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

import cl.karangop.flash.R;
import cl.karangop.flash.data.CurrentUser;
import cl.karangop.flash.data.Nodes;
import cl.karangop.flash.models.Message;

/**
 * Created by karan_000 on 13-03-2017.
 */

public class MessagesAdapter extends FirebaseRecyclerAdapter<Message, MessagesAdapter.MessageHolder>{

    private MessageUpdater updater;

    /**
     * @param modelClass      Firebase will marshall the data at a location into
     *                        an instance of a class that you provide
     * @param modelLayout     This is the layout used to represent a single item in the list.
     *                        You will be responsible for populating an instance of the corresponding
     *                        view with the data from an instance of modelClass.
     * @param viewHolderClass The class that hold references to all sub-views in an instance modelLayout.
     * @param ref             The Firebase location to watch for data changes. Can also be a slice of a location,
     *                        using some combination of {@code limit()}, {@code startAt()}, and {@code endAt()}.
     */

    private static final String CURRENT_EMAIL = new CurrentUser().email();

//    Borrar contenido de MessagesAdapter
    public MessagesAdapter(String chatId, MessageUpdater updater) {
        super(Message.class, R.layout.list_item_message, MessageHolder.class, new Nodes().messages(chatId));
        this.updater = updater;
    }

    @Override
    protected void populateViewHolder(MessageHolder viewHolder, Message model, int position) {
        TextView textView = (TextView) viewHolder.itemView;
        if (CURRENT_EMAIL.equals(model.getOwner())){
            textView.setGravity(Gravity.RIGHT);
        }else{
            textView.setGravity(Gravity.LEFT);
        }

        textView.setText(model.getContent());

    }

    @Override
    protected void onDataChanged() {
        super.onDataChanged();
        updater.update();
    }

    public static class MessageHolder extends RecyclerView.ViewHolder{

        public MessageHolder(View itemView) {
            super(itemView);
        }
    }
}
