package com.ia.core.llm.service.model.chat;

import java.util.UUID;

import com.ia.core.service.dto.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Israel Araújo
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequestDTO
  implements DTO<String> {

  private UUID comandoSistemaID;
  private String request;
  private String text;

  @Override
  public ChatRequestDTO cloneObject() {
    return toBuilder().build();
  }

}
