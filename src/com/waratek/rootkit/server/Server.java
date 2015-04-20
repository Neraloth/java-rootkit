package com.waratek.rootkit.server;

import org.restlet.Component;
import org.restlet.data.Protocol;
import org.restlet.resource.ServerResource;

import com.waratek.rootkit.resource.ContactResource;

public class Server extends ServerResource {


  public static void main(String[] args) {
    Component component = new Component();
    component.getServers().add(Protocol.HTTP, 8182);
    component.getClients().add(Protocol.FILE);


    component.getDefaultHost().attach("/contact", ContactResource.class);
    try {
      component.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
