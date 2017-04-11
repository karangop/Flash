package cl.karangop.flash.views.chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import cl.karangop.flash.R;
import cl.karangop.flash.adapters.MessageUpdater;
import cl.karangop.flash.adapters.MessagesAdapter;

public class ChatActivity extends AppCompatActivity implements MessageUpdater{

    private MessagesAdapter adapter;
    private RecyclerView recyclerView;
    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //    encontrar chatId
        id = getIntent().getStringExtra("id");

        final EditText editText = (EditText) findViewById(R.id.emailEt);
        ImageButton button = (ImageButton) findViewById(R.id.searchBtn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendMessage().validateMessage(editText.getText().toString() , id);
                editText.setText("");
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.messagesRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new MessagesAdapter(id, this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.cleanup();
        new UpdateChat().lastSeen(id);
    }

    @Override
    public void update() {
        recyclerView.scrollToPosition(adapter.getItemCount()-1);

    }
}
