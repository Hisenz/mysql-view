package io.github.hisenz.mysqlview.mysqlview.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * TABLES
 *
 * @author
 */
@Data
public class Table implements Serializable {
    private static final long serialVersionUID = 8130059089110898208L;
    private String tableCatalog;

    private String tableSchema;

    private String tableName;

    private Object tableType;

    private String engine;

    private Integer version;

    private Object rowFormat;

    private Long tableRows;

    private Long avgRowLength;

    private Long dataLength;

    private Long maxDataLength;

    private Long indexLength;

    private Long dataFree;

    private Long autoIncrement;

    private Date createTime;

    private Date updateTime;

    private Date checkTime;

    private String tableCollation;

    private Long checksum;

    private String createOptions;

    private String tableComment;

}