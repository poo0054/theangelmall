package com.themall.member.exception;

/**
 * 用户值重复异常
 */
public class MemberExection extends RuntimeException {

    public MemberExection(String message) {
        super(message);
    }
}
