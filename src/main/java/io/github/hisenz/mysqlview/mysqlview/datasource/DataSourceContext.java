package io.github.hisenz.mysqlview.mysqlview.datasource;

import io.github.hisenz.mysqlview.mysqlview.costant.DataSourceConstants;

public class DataSourceContext {

    private static final ThreadLocal<String> DATASOURCE_CONTEXT_KEY_HOLDER = new ThreadLocal<>();

    public static void setContextKey(String key) {
        DATASOURCE_CONTEXT_KEY_HOLDER.set(key);
    }

    public static String getContextKey() {
        String key = DATASOURCE_CONTEXT_KEY_HOLDER.get();
        return key == null ? DataSourceConstants.DEFAULT_DATASOURCE_KEY : key;
    }

    public static void remove() {
        DATASOURCE_CONTEXT_KEY_HOLDER.remove();
    }
}
