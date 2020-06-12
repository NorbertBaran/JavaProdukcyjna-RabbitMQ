package uj.jwzp.chat;

public class Receiver extends Thread{

    private final Rabbit chat;

    public Receiver(Rabbit chat){
        this.chat=chat;
    }

    @Override
    public void run() {
        while(true){
            try {
                chat.receive();
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
    }
}
