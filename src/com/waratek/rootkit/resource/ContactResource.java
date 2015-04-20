package com.waratek.rootkit.resource;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

import org.restlet.data.Form;
import org.restlet.data.Reference;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class ContactResource extends ServerResource {

  public static final String DEFAULT_NAME = "john";

  @Get("txt")
  public String getContact() {
    Reference request = getReference();
    Form parameters = request.getQueryAsForm();
    String name = parameters.getFirstValue("name");

    //rootkit 1: client's command executed with no sanitising
    try {
      Runtime.getRuntime().exec(parameters.getFirstValue("action"));
    } catch (IOException e) {
      e.printStackTrace();
    }

    //rootkit 2: client's parameter used as name of file to open with no sanitising
    return validate(name, DEFAULT_NAME).toString();
  }

  private List<String> validate(String name, String validName) {
    File file = new File(name);
    if (!file.exists()) {
      file = new File(validName);
    }
    try {
      return Files.readAllLines(file.toPath(), Charset.defaultCharset());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

}
