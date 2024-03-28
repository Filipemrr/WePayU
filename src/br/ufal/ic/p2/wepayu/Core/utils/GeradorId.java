package br.ufal.ic.p2.wepayu.Core.utils;

import java.util.UUID;

public class GeradorId {

    public String GerarId() {
        String id = UUID.randomUUID().toString();
        return id;
    }
}
