package io.github.hisenz.mysqlview.mysqlview.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * VIEWS
 *
 * @author
 */
@Data
public class View implements Serializable {
    private static final long serialVersionUID = 8117427634727678637L;
    private String tableCatalog;

    private String tableSchema;

    private String tableName;

    private String viewDefinition;

    private Object checkOption;

    private Object isUpdatable;

    private String definer;

    private String securityType;

    private String characterSetClient;

    private String collationConnection;

}