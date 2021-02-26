package com.springsecurityjwt.demo.core.security;

public interface AuthToken<T> {
    boolean validate();
    T getData();
}
