package web;

import java.io.IOException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/websocketendpoint")
public class ws_server {
    private Session ts;
    int  count=1;
      
    @OnOpen
    public void onOpen( Session ts) throws IOException{
        this.ts=ts;
        for (Session os : this.ts.getOpenSessions())
            count+=1;
        System.out.println("Open Connection ...");
    }
     
    @OnClose
    public void onClose(){
        System.out.println("Close Connection ...");
    }
     
    @OnMessage
    public void onMessage(String message,Session ts )throws IOException {
        System.out.println("Connection for message ...");
        String tMsg = "stranger_";
        for (Session os : this.ts.getOpenSessions()){
            if (!os.equals(this.ts)){
                RemoteEndpoint.Basic remote = os.getBasicRemote();
                remote.sendText(tMsg+count+":"+message);
            }
        }
        
    }
 
    @OnError
    public void onError(Throwable e){
        e.printStackTrace();
    }
    
    
}
