package com.ia.core.llm.service.model.chat;

/**
 * @author Israel Araújo
 */
@SuppressWarnings("javadoc")
public class ChatRequestTranslator {
  public static final class HELP {
    public static final String CHAT_REQUEST = "chat.request.help";
    public static final String COMANDO_SISTEMA_ID = "chat.request.help.comando.sistema.id";
    public static final String REQUEST = "chat.request.help.request";
    public static final String TEXT = "chat.request.help.text";
  }
  public static final String CHAT_REQUEST_CLASS = ChatRequestDTO.class
      .getCanonicalName();
  public static final String CHAT_REQUEST = "chat.request";
  public static final String COMANDO_SISTEMA_ID = "chat.request.comando.sistema.id";
  public static final String REQUEST = "chat.request.request";

  public static final String TEXT = "chat.request.text";

  public static final class VALIDATION {
    public static final String REQUEST_REQUIRED = "validation.chat.request.required";
    public static final String REQUEST_SIZE = "validation.chat.request.size";
  }
}
