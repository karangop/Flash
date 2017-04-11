package cl.karangop.flash.views.main.chats;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.github.siyamed.shapeimageview.BubbleImageView;
import com.squareup.picasso.Picasso;

import cl.karangop.flash.R;
import cl.karangop.flash.data.CurrentUser;
import cl.karangop.flash.data.Nodes;
import cl.karangop.flash.models.Chat;
import cl.karangop.flash.views.chat.ChatActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatsFragment extends Fragment {


    public ChatsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chats, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recycler= (RecyclerView) view;
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setHasFixedSize(true);

        FirebaseRecyclerAdapter<Chat, ChatHolder> adapter= new FirebaseRecyclerAdapter<Chat, ChatHolder>(
                Chat.class, R.layout.list_item_chat, ChatHolder.class, new Nodes().userChats(new CurrentUser().userId())
        ) {

            @Override
            protected void populateViewHolder(ChatHolder viewHolder, final Chat model, int position) {
                viewHolder.setBubble(model.getPhoto());
                viewHolder.setTextView(model.getReceiver());
                viewHolder.setNotification(model.isNotification());

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String id= model.getId();
                        Intent intent= new Intent(getActivity(), ChatActivity.class);
                        intent.putExtra("id",id);
                        startActivity(intent);
                    }
                });

            }
        };

        recycler.setAdapter(adapter);

    }

    public static class ChatHolder extends RecyclerView.ViewHolder{

        private final BubbleImageView bubble;
        private final TextView textView;
        private final View view;

        public ChatHolder(View itemView) {
            super(itemView);
            bubble = (BubbleImageView) itemView.findViewById(R.id.chatBiv);
            textView = (TextView) itemView.findViewById(R.id.chatTv);
            view = itemView.findViewById(R.id.chatv);
        }

        public void setBubble(String url){
            Picasso.with(bubble.getContext()).load(url).centerCrop().fit().into(bubble);
        }

        public void setTextView(String text){
            textView.setText(text);
        }

        public void setNotification(boolean notification){
            if(notification){
                view.setVisibility(View.VISIBLE);
            }
            else {
                view.setVisibility(View.GONE);
            }
        }
    }
}
