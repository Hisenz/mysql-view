package io.github.hisenz.mysqlview.mysqlview.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * SCHEMATA
 *
 * @author
 */
@Data
public class Schemata implements Serializable {
    private static final long serialVersionUID = -6250777347056564633L;
    private String catalogName;

    private String schemaName;

    private String defaultCharacterSetName;

    private String defaultCollationName;

    private Object defaultEncryption;

    private byte[] sqlPath;

}