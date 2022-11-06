package com.example.hwh_community.account;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)//: RUNTIME 보존 정책을 사용하여 주석이 달린 주석은 런타임 중에 유지되며 런타임 중에 프로그램에서 액세스할 수 있습니다.
@Target(ElementType.PARAMETER)
@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : account") // 로그인 한 유저 정보
public @interface CurrentAccount {

}