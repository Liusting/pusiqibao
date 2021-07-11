package com.pusiqibao.entity;

import lombok.Data;

@Data
public class Msg {
    String encryptedData;
    String code;
    String iv;
}
