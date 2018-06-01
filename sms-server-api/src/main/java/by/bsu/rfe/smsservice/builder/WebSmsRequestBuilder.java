package by.bsu.rfe.smsservice.builder;

import by.bsu.rfe.smsservice.common.request.Request;

public abstract class WebSmsRequestBuilder<T> {

  public abstract Request build(T t);
}
