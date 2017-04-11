package cl.karangop.flash.views.chat;

import cl.karangop.flash.data.CurrentUser;
import cl.karangop.flash.data.Nodes;
import cl.karangop.flash.models.Message;

/**
 * Created by karan_000 on 13-03-2017.
 */

public class SendMessage {

    public void validateMessage(String message, String chatId){
        if (message.trim().length() > 0){
            String email = new CurrentUser().email();
            Message msg= new Message();

            msg.setContent(message);
            msg.setOwner(email);

//            Enviar mensage
            new Nodes().messages(chatId).push().setValue(msg);
        }

    }
}
