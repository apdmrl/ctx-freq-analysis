package com.cfa.ctxfreqanalysis.constants;

import com.cfa.ctxfreqanalysis.model.Language;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ProjectConstants {
    public static final Map<String,String> alphabets;
    // initiliaze alphabets
    static {
        Map<String,String> a = new HashMap<>();
        a.put(String.valueOf(Language.TR),"aâbcçdefgğhıiîjklmnoöprsştuüûvyz"); // 32 char
        a.put(String.valueOf(Language.EN),"abcdefghijklmnopqrstuvwxyz"); //26 char
        a.put(String.valueOf(Language.DE),"aäbcdefghıjklmnoöpqrßstuüvwxyz"); //30 char
        a.put(String.valueOf(Language.FR),"aâàbcçdeêéèëfghıïîjklmnoôœpqrstuüûùvwxyz"); //40 char
        a.put(String.valueOf(Language.ES),"aábcdeéfghıíjklmnñoópqrstuüúvwxyz"); //33 char
        alphabets = Collections.unmodifiableMap(a);
    }
}
