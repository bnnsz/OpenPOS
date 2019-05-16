/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.application.managers;

import com.bizstudio.xmpp.dtos.XMPPMessage;
import com.bizstudio.xmpp.dtos.XMPPPayload;
import com.bizstudio.xmpp.enums.XmppPayloadAction;
import com.bizstudio.xmpp.events.XmppMessageEventListener;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.chat2.IncomingChatMessageListener;
import org.jivesoftware.smack.chat2.OutgoingChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterListener;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smack.util.stringencoder.Base64;
import org.jivesoftware.smackx.address.MultipleRecipientManager;
import org.jivesoftware.smackx.json.packet.JsonPacketExtension;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.Jid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

/**
 *
 * @author ObinnaAsuzu
 */
public class NetworkCommunicationManager implements IncomingChatMessageListener,
        OutgoingChatMessageListener, RosterListener {
    private static final Logger LOG = Logger.getLogger(NetworkCommunicationManager.class.getName());
    public static NetworkCommunicationManager getInstance() {
        NetworkCommunicationManager instance = NetworkCommunicationManagerHolder.INSTANCE;
        return instance;
    }

    private AbstractXMPPConnection connection;
    private Gson gson;
    private Function<String, Jid> jidMapper;

    private final Map<XmppPayloadAction, List<XmppMessageEventListener>> outgoingMessageEventListeners = new TreeMap<>();
    private final Map<XmppPayloadAction, List<XmppMessageEventListener>> incomingMessageEventListeners = new TreeMap<>();

    private NetworkCommunicationManager() {
        try {
            initComponents();
            initConnection();
            initPresence();
            initRoaster();
            initMessageListeners();
        } catch (SmackException | IOException | XMPPException | InterruptedException ex) {
            Logger.getLogger(NetworkCommunicationManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initComponents() {
        gson = new Gson();
        jidMapper = (String address) -> {
            try {
                return JidCreate.bareFrom(address);
            } catch (XmppStringprepException ex) {
                Logger.getLogger(NetworkCommunicationManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        };
    }

    private void initRoaster() {
        Roster.getInstanceFor(connection).setSubscriptionMode(Roster.SubscriptionMode.accept_all);
        Roster.getInstanceFor(connection).addRosterListener(this);
    }

    private void initPresence() throws SmackException, InterruptedException {
        Presence presence = new Presence(Presence.Type.available);
        presence.setStatus("Logged in");
        connection.sendStanza(presence);
    }

    private void initConnection() throws XmppStringprepException, SmackException, IOException, XMPPException, InterruptedException {
        XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                .setUsernameAndPassword("bnnsz", "asijust")
                .setXmppDomain("asuzuobinna.com")
                .setHost("asuzuobinna.com")
                .setSecurityMode(SecurityMode.disabled)
                .setPort(5222)
                .build();
        connection = new XMPPTCPConnection(config);
        System.out.println("[xmpp:connect] asuzuobinna.com");
        connection.connect();
        System.out.println("[xmpp:login] bnnsz");
        connection.login();
    }

    private void initMessageListeners() {
        ChatManager.getInstanceFor(connection).addIncomingListener(this);
        ChatManager.getInstanceFor(connection).addOutgoingListener(this);
        for (XmppPayloadAction action : XmppPayloadAction.values()) {
            incomingMessageEventListeners.put(action, new ArrayList<>());
            outgoingMessageEventListeners.put(action, new ArrayList<>());
        }
    }

    @Override
    public void newIncomingMessage(EntityBareJid ebj, Message msg, Chat chat) {

        System.out.println("[xmpp:incoming_message] " + msg.toString());
        XMPPPayload payload = gson.fromJson(JsonPacketExtension.from(msg).getJson(), XMPPPayload.class);
        incomingMessageEventListeners.get(payload.getAction()).forEach(listener -> {
            listener.handle(msg.getFrom().toString(), msg.getBody(), Base64.decode(payload.getData()));
        });
    }

    @Override
    public void newOutgoingMessage(EntityBareJid ebj, Message msg, Chat chat) {
        System.out.println("[xmpp:outgoing_message] " + msg.toString());
    }

    @Override
    public void entriesAdded(Collection<Jid> clctn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void entriesUpdated(Collection<Jid> clctn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void entriesDeleted(Collection<Jid> clctn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void presenceChanged(Presence presence) {
        System.out.println("[xmpp:presence_changed] " + presence.getFrom() + " => " + presence);
    }

    public void sendData(XMPPMessage message, String... recipients) throws SmackException.NoResponseException,
            XMPPException.XMPPErrorException, SmackException.FeatureNotSupportedException,
            SmackException.NotConnectedException, InterruptedException {
        Message msg = new Message();
        msg.setBody(message.getMessage());
        msg.addExtension(new JsonPacketExtension(gson.toJson(message.getPayload())));
        List<Jid> to = Arrays.asList(recipients).stream().map(jidMapper).collect(Collectors.toList());
        MultipleRecipientManager.send(connection, msg, to, null, null);
    }

    public void addIncomingMessageListener(XmppPayloadAction action, XmppMessageEventListener listener) {
        incomingMessageEventListeners.get(action).add(listener);
    }

    public void removeIncomingMessageListener(XmppPayloadAction action, XmppMessageEventListener listener) {
        incomingMessageEventListeners.get(action).remove(listener);
    }

    public void addOutgoingMessageListener(XmppPayloadAction action, XmppMessageEventListener listener) {
        outgoingMessageEventListeners.get(action).add(listener);
    }

    public void removeOutgoingMessageListener(XmppPayloadAction action, XmppMessageEventListener listener) {
        outgoingMessageEventListeners.get(action).remove(listener);
    }

    private static class NetworkCommunicationManagerHolder {

        private static final NetworkCommunicationManager INSTANCE = new NetworkCommunicationManager();
    }


}
