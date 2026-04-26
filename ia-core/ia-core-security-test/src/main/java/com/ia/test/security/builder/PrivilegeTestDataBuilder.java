package com.ia.test.security.builder;

import com.ia.core.security.model.privilege.Privilege;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Builder para criar instâncias de {@link Privilege} com dados de teste.
 * Segue o padrão Builder para facilitar a criação de dados de teste com valores padrão.
 *
 * @author Israel Araújo
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PrivilegeTestDataBuilder {

  private String name = "PRIVILEGE_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

  /**
   * Cria uma nova instância do builder.
   *
   * @return novo PrivilegeTestDataBuilder
   */
  public static PrivilegeTestDataBuilder privilege() {
    return new PrivilegeTestDataBuilder();
  }

  /**
   * Define o nome do privilégio.
   *
   * @param name nome do privilégio
   * @return this para encadeamento
   */
  public PrivilegeTestDataBuilder withName(String name) {
    this.name = name;
    return this;
  }

  // Description field removed as Privilege model doesn't have setDescription method

  /**
   * Constrói a instância de Privilege com os parâmetros definidos.
   *
   * @return nova instância de Privilege
   */
  public Privilege build() {
    Privilege privilege = new Privilege();
    privilege.setName(name);
    return privilege;
  }
}
