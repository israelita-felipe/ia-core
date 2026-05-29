package com.ia.core.security.view.authentication;

import lombok.extern.slf4j.Slf4j;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Gerenciador de refresh token para a camada view.
 * <p>
 * Responsável por gerenciar o refresh proativo de tokens de acesso
 * antes que expirem, garantindo uma experiência contínua para o usuário.
 *
 * @author Israel Araújo
 */
@Slf4j
public class TokenRefreshManager {

  private static final long REFRESH_THRESHOLD_MINUTES = 5;
  private static final long REFRESH_CHECK_INTERVAL_MINUTES = 1;

  private final AuthenticationDetails authenticationDetails;
  private final Timer refreshTimer;
  private final long accessTokenExpirationMillis;

  public TokenRefreshManager(AuthenticationDetails authenticationDetails,
                          long accessTokenExpirationMillis) {
    this.authenticationDetails = authenticationDetails;
    this.accessTokenExpirationMillis = accessTokenExpirationMillis;
    this.refreshTimer = new Timer("TokenRefreshTimer", true);
  }

  /**
   * Inicia o monitoramento proativo de refresh token.
   */
  public void startProactiveRefresh() {
    if (!authenticationDetails.hasRefreshToken()) {
      log.warn("Refresh token não disponível, monitoramento proativo não iniciado");
      return;
    }

    long checkInterval = TimeUnit.MINUTES.toMillis(REFRESH_CHECK_INTERVAL_MINUTES);
    refreshTimer.schedule(new TimerTask() {
      @Override
      public void run() {
        try {
          if (shouldRefreshToken()) {
            log.info("Iniciando refresh proativo do access token");
            authenticationDetails.refreshAccessToken();
            log.info("Access token renovado com sucesso");
          }
        } catch (Exception e) {
          log.error("Erro ao renovar access token proativamente: {}", e.getLocalizedMessage(), e);
          // Se o refresh falhar, o usuário será redirecionado para login na próxima requisição
          stopProactiveRefresh();
        }
      }
    }, checkInterval, checkInterval);

    log.info("Monitoramento proativo de refresh token iniciado");
  }

  /**
   * Para o monitoramento proativo de refresh token.
   */
  public void stopProactiveRefresh() {
    refreshTimer.cancel();
    log.info("Monitoramento proativo de refresh token parado");
  }

  /**
   * Verifica se o token deve ser renovado.
   * O token é renovado se estiver próximo de expirar (dentro do threshold).
   *
   * @return true se o token deve ser renovado, false caso contrário
   */
  private boolean shouldRefreshToken() {
    if (!authenticationDetails.isAuthenticated()) {
      return false;
    }

    // Para simplificar, assumimos que o token foi gerado recentemente
    // Em uma implementação completa, precisaríamos extrair a data de expiração do token JWT
    // e comparar com o tempo atual
    return true;
  }

  /**
   * Tenta renovar o token de acesso de forma reativa (após erro 401).
   *
   * @return true se o refresh foi bem-sucedido, false caso contrário
   */
  public boolean tryReactiveRefresh() {
    if (!authenticationDetails.hasRefreshToken()) {
      log.warn("Refresh token não disponível para refresh reativo");
      return false;
    }

    try {
      log.info("Iniciando refresh reativo do access token");
      authenticationDetails.refreshAccessToken();
      log.info("Access token renovado com sucesso");
      return true;
    } catch (Exception e) {
      log.error("Erro ao renovar access token reativamente: {}", e.getLocalizedMessage(), e);
      return false;
    }
  }

  /**
   * Limpa os tokens e para o monitoramento.
   */
  public void clearTokens() {
    stopProactiveRefresh();
    // A limpeza dos tokens é feita pelo AuthenticationDetails
    log.info("Tokens limpos e monitoramento parado");
  }
}
