/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message;

/**
 *
 * @author user
 */

public class Message implements java.io.Serializable {
    public static enum Message_Type {None, Name, Disconnect,RivalConnected, Text, Selected, Bitis,Start, Locations}
    //mesajın tipi
    public Message_Type type;
    public Object content;
    public Message(Message_Type t)
    {
        this.type=t;
    }
    public int[] array;
	public int playerNumber;
 

    
    
}