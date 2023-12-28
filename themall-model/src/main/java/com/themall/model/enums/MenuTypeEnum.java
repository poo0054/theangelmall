package com.themall.model.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @author poo0054
 */
@Getter
public enum MenuTypeEnum implements IEnum<String> {

    /**
     * 菜单
     */
    MENU("menu"),
    /**
     * iframe
     */
    IFRAME("iframe"),
    /**
     * 外链接
     */
    LINK("link"),
    /**
     * 按钮
     */
    BUTTON("button");

    @JsonValue
    private final String value;

    MenuTypeEnum(String value) {
        this.value = value;
    }


}
