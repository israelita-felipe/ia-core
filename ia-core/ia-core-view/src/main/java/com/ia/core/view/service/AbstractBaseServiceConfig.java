package com.ia.core.view.service;

import java.io.Serializable;

import com.ia.core.view.client.BaseClient;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@RequiredArgsConstructor
@Slf4j
@Getter
public class AbstractBaseServiceConfig<D extends Serializable> {
  protected final BaseClient<D> client;
}
