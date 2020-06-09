package com.autumn.mybatis.provider.builder;

import com.autumn.annotation.FriendlyApi;
import com.autumn.annotation.FriendlyProperty;
import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.mapper.annotation.ColumnDocument;
import com.autumn.mybatis.mapper.annotation.TableDocument;
import com.autumn.mybatis.metadata.EntityColumn;
import com.autumn.mybatis.metadata.EntityIndex;
import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.provider.DbDocumentInfo;
import com.autumn.util.EnvironmentConstants;
import com.autumn.util.StringUtils;
import com.autumn.util.tuple.TupleTwo;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 文档生成器
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-01-04 17:48
 **/
public class DocumentBuilder {

    private final AbstractDefinitionBuilder definitionBuilder;

    public DocumentBuilder(AbstractDefinitionBuilder definitionBuilder) {
        this.definitionBuilder = definitionBuilder;
    }

    private void addDocumentRow(StringBuilder builder, String title, Object value, String prefix, String suffix) {
        builder.append(EnvironmentConstants.LINE_SEPARATOR);
        builder.append(prefix).append(title);
        if (value != null) {
            builder.append(value.toString().trim());
        }
        builder.append(suffix);
    }

    /**
     * 创建方案文档
     *
     * @param documentInfo
     * @param tables
     * @return
     */
    public String createProjectDocument(DbDocumentInfo documentInfo, Collection<EntityTable> tables) {
        if (documentInfo == null) {
            documentInfo = new DbDocumentInfo();
        }
        String lineSeparator = EnvironmentConstants.LINE_SEPARATOR;
        StringBuilder builder = new StringBuilder(tables.size() * 2000);
        builder.append("# **");
        if (StringUtils.isNotNullOrBlank(documentInfo.getProjectName())) {
            builder.append(documentInfo.getProjectName());
        } else {
            builder.append("XXX 项目");
        }
        builder.append("**");
        Map<String, List<DocumentTable>> tableMap = this.getDocumentTableMap(tables);
        int tableCount = 0;
        for (List<DocumentTable> value : tableMap.values()) {
            tableCount += value.size();
        }
        builder.append(lineSeparator);
        this.addDocumentRow(builder, "创建日期:", documentInfo.getCreatedDate(), "> ", "  ");
        this.addDocumentRow(builder, "创建人:", documentInfo.getCreatedUserName(), "> ", "  ");
        this.addDocumentRow(builder, "修改日期:", documentInfo.getUpdatedDate(), "> ", "  ");
        this.addDocumentRow(builder, "修改人:", documentInfo.getUpdatedUserName(), "> ", "  ");
        this.addDocumentRow(builder, "版本:", documentInfo.getVersion(), "> ", "  ");
        builder.append(lineSeparator);
        builder.append("---");
        builder.append(lineSeparator);
        builder.append("## 文档目录 (" + tableCount + " 个表)");
        int order = 1;
        for (Map.Entry<String, List<DocumentTable>> entry : tableMap.entrySet()) {
            String groupName = "未分组";
            if (StringUtils.isNotNullOrBlank(entry.getKey())) {
                groupName = entry.getKey();
            }
            groupName = groupName + " (" + entry.getValue().size() + " 个表)";
            String groupSkip = "(#" + order + "-" + groupName + ")";
            this.addDocumentRow(builder, "", groupName, "- [" + order + ". ", "]" + groupSkip);
            order++;
        }
        builder.append(lineSeparator);
        builder.append("---");
        order = 1;
        for (Map.Entry<String, List<DocumentTable>> entry : tableMap.entrySet()) {
            builder.append(lineSeparator);
            String groupName = "未分组";
            if (StringUtils.isNotNullOrBlank(entry.getKey())) {
                groupName = entry.getKey();
            }
            this.addDocumentRow(builder, "", groupName, "## **" + order + ". ", "**");
            for (int i = 0; i < entry.getValue().size(); i++) {
                builder.append(lineSeparator);
                DocumentTable documentTable = entry.getValue().get(i);
                String itemPrefix = "### " + order + "." + (i + 1) + " ";
                String tableCatalog = documentTable.getTable().getName();
                TupleTwo<String, String> tableFriedLyExplain = this.getTableFriedLyExplain(documentTable.getTable());
                if (StringUtils.isNotNullOrBlank(tableFriedLyExplain.getItem1())) {
                    tableCatalog = tableCatalog + " (" + tableFriedLyExplain.getItem1() + ")";
                }
                this.addDocumentRow(builder, "", tableCatalog, itemPrefix, "");
                builder.append(this.createTableDocument(documentTable.getTable(), documentInfo.isBindEntityClass()));
            }
            order++;
        }
        builder.append(lineSeparator);
        this.addProjectDocumentStyle(builder);
        return builder.toString();
    }

    /**
     * 添加方案文档样式
     *
     * @param builder
     * @param value
     */
    private void addProjectDocumentStyleRow(StringBuilder builder, String value) {
        builder.append(EnvironmentConstants.LINE_SEPARATOR);
        builder.append(value);
    }

    /**
     * 添加方案文档样式
     *
     * @param builder
     */
    private void addProjectDocumentStyle(StringBuilder builder) {
        this.addProjectDocumentStyleRow(builder, "<style>");
        this.addProjectDocumentStyleRow(builder, "table {");
        this.addProjectDocumentStyleRow(builder, "  font-size:12px;");
        this.addProjectDocumentStyleRow(builder, "  width: 100%; /*表格宽度*/");
        this.addProjectDocumentStyleRow(builder, "  /*max-width: 65em; 表格最大宽度，避免表格过宽*/");
        this.addProjectDocumentStyleRow(builder, "  empty-cells: show; /*单元格无内容依旧绘制边框*/");
        this.addProjectDocumentStyleRow(builder, "}");
        this.addProjectDocumentStyleRow(builder, "table th,table td {");
        this.addProjectDocumentStyleRow(builder, "  height: 20px; /*统一每一行的默认高度*/");
        this.addProjectDocumentStyleRow(builder, "  border: 1px solid #dedede; /*内部边框样式*/");
        this.addProjectDocumentStyleRow(builder, "  padding: 0; /*内边距*/");
        this.addProjectDocumentStyleRow(builder, "}");
        this.addProjectDocumentStyleRow(builder, "table > thead > tr > th {");
        this.addProjectDocumentStyleRow(builder, "  font-size:14px;");
        this.addProjectDocumentStyleRow(builder, "  border-bottom:none;");
        this.addProjectDocumentStyleRow(builder, "  font-weight: bold; /*加粗*/");
        this.addProjectDocumentStyleRow(builder, "  text-align: center !important; /*内容居中，加上 !important 避免被 Markdown 样式覆盖*/");
        this.addProjectDocumentStyleRow(builder, "  background: rgba(158,188,226,0.2); /*背景色*/");
        this.addProjectDocumentStyleRow(builder, "  white-space: nowrap; /*表头内容强制在一行显示*/");
        this.addProjectDocumentStyleRow(builder, "}");
        this.addProjectDocumentStyleRow(builder, "table tbody tr:nth-child(2n) {");
        this.addProjectDocumentStyleRow(builder, "  background: rgba(230, 230, 229, 0.12); ");
        this.addProjectDocumentStyleRow(builder, "}");
        this.addProjectDocumentStyleRow(builder, "table tr:hover {");
        this.addProjectDocumentStyleRow(builder, "  background: #efefef;");
        this.addProjectDocumentStyleRow(builder, "}");
        this.addProjectDocumentStyleRow(builder, "table td:nth-child(1) {");
        this.addProjectDocumentStyleRow(builder, "  white-space: nowrap;");
        this.addProjectDocumentStyleRow(builder, "}");
        this.addProjectDocumentStyleRow(builder, "</style>");
    }

    private Map<String, List<DocumentTable>> getDocumentTableMap(Collection<EntityTable> tables) {
        List<DocumentTable> documentTables = new ArrayList<>(tables.size());
        for (EntityTable table : tables) {
            if (!table.isView()) {
                TupleTwo<String, String> tableFriedLyExplain = this.getTableFriedLyExplain(table);
                TableDocument tableDocument = table.getEntityClass().getAnnotation(TableDocument.class);
                String group;
                int groupOrder = -1;
                int tableOrder = -1;
                if (tableDocument != null) {
                    group = tableDocument.group();
                    groupOrder = tableDocument.groupOrder();
                    tableOrder = tableDocument.tableOrder();
                } else {
                    group = table.getEntityClass().getPackage().getName();
                }
                documentTables.add(new DocumentTable(group, groupOrder, tableOrder, table));
            }
        }
        documentTables.sort(DocumentTable::compareTo);
        Map<String, List<DocumentTable>> map = new LinkedHashMap<>(tables.size());
        for (DocumentTable documentTable : documentTables) {
            List<DocumentTable> mapTables = map.computeIfAbsent(documentTable.group, key -> {
                return new ArrayList<>(16);
            });
            mapTables.add(documentTable);
        }
        return map;
    }

    public String createTableDocument(EntityTable table, boolean isBindEntityClass) {
        return this.createTableDocument(table, table.getName(), isBindEntityClass);
    }

    private TupleTwo<String, String> getTableFriedLyExplain(EntityTable table) {
        TableDocument tableDocument = table.getEntityClass().getAnnotation(TableDocument.class);
        String friedLyName = "";
        String explain = "";
        if (tableDocument != null) {
            if (tableDocument.value() != null) {
                friedLyName = tableDocument.value().trim();
            }
            if (tableDocument.explain() != null) {
                explain = tableDocument.explain().trim();
            }
        } else {
            FriendlyApi friendlyApi = table.getEntityClass().getAnnotation(FriendlyApi.class);
            if (friendlyApi != null) {
                if (friendlyApi.value() != null) {
                    friedLyName = friendlyApi.value().trim();
                }
                if (friendlyApi.explain() != null) {
                    explain = friendlyApi.explain().trim();
                }
            }
        }
        return new TupleTwo<>(friedLyName, explain);
    }

    /**
     * 添加表文档头
     *
     * @param builder
     * @param table
     * @param tableName
     * @param isBindEntityClass
     */
    private void addTableDocumentHead(StringBuilder builder, EntityTable table, String tableName, boolean isBindEntityClass) {
        String lineSeparator = EnvironmentConstants.LINE_SEPARATOR;
        TupleTwo<String, String> tableFriedLyExplain = this.getTableFriedLyExplain(table);
        builder.append(lineSeparator).append("> 表名：").append(tableName).append("  ");
        builder.append(lineSeparator).append("> 中文：").append(tableFriedLyExplain.getItem1()).append("  ");
        builder.append(lineSeparator).append("> 说明：").append(tableFriedLyExplain.getItem2()).append("  ");
        if (isBindEntityClass) {
            builder.append(lineSeparator).append("> 实体类型：").append(table.getEntityClass().getName()).append("  ");
        }
        List<EntityIndex> indexs = table.getIndexs().stream().filter(s -> s.getColumns().size() > 1).collect(Collectors.toList());
        if (indexs.size() > 0) {
            builder.append(lineSeparator).append("> 复合索引：");
            for (int i = 0; i < indexs.size(); i++) {
                if (i > 0) {
                    builder.append("、");
                }
                builder.append("[");
                EntityIndex index = indexs.get(i);
                for (int j = 0; j < index.getColumns().size(); j++) {
                    if (j == 0) {
                        builder.append("(");
                    } else {
                        builder.append(",");
                    }
                    builder.append(index.getColumns().get(j).getColumnName());
                    if (j == index.getColumns().size() - 1) {
                        builder.append(")");
                        if (index.isUnique()) {
                            builder.append(" AS UNIQUE");
                        }
                    }
                }
                builder.append("]");
            }
            builder.append("  ");
        }
    }

    /**
     * 添加列文档分离符
     *
     * @param builder
     * @param name
     * @param maxWidth
     * @param filling
     * @param end
     */
    private void addColumnDocumentSeparate(StringBuilder builder, String name, int maxWidth, String filling, String end) {
        String value = "| " + name;
        builder.append(value);
        if (value.length() < maxWidth) {
            for (int i = 1; i < maxWidth - value.length(); i++) {
                builder.append(filling);
            }
        }
        builder.append(end);
        builder.append(" ");
    }

    private boolean isSingleColumnIndex(EntityColumn column) {
        for (EntityIndex index : column.getTable().getIndexs()) {
            for (EntityColumn indexColumn : index.getColumns()) {
                if (index.getColumns().size() == 1 && column.equals(indexColumn)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void addColumnDocument(StringBuilder builder, EntityTable table) {
        builder.append(EnvironmentConstants.LINE_SEPARATOR);
        this.addColumnDocumentSeparate(builder, "编号", 5, " ", "");
        this.addColumnDocumentSeparate(builder, "列名", 27, " ", "");
        this.addColumnDocumentSeparate(builder, "中文", 27, " ", "");
        this.addColumnDocumentSeparate(builder, "列类型", 14, " ", "");
        this.addColumnDocumentSeparate(builder, "规则", 20, " ", "");
        this.addColumnDocumentSeparate(builder, "非空", 7, " ", "");
        this.addColumnDocumentSeparate(builder, "备注", 40, " ", "");
        builder.append("|");
        builder.append(EnvironmentConstants.LINE_SEPARATOR);
        this.addColumnDocumentSeparate(builder, ":", 7, "-", "");
        this.addColumnDocumentSeparate(builder, ":", 29, "-", "");
        this.addColumnDocumentSeparate(builder, ":", 28, "-", "");
        this.addColumnDocumentSeparate(builder, ":", 17, "-", "");
        this.addColumnDocumentSeparate(builder, ":", 22, "-", "");
        this.addColumnDocumentSeparate(builder, ":", 8, "-", ":");
        this.addColumnDocumentSeparate(builder, ":", 42, "-", "");
        builder.append("|");
        for (int i = 0; i < table.getColumns().size(); i++) {
            EntityColumn column = table.getColumns().get(i);
            ColumnDocument columnDocument = column.getProperty().getAnnotation(ColumnDocument.class);
            String friedLyName = "";
            String colNameExplain = "";
            if (columnDocument != null) {
                if (columnDocument.value() != null) {
                    friedLyName = columnDocument.value().trim();
                }
                if (columnDocument.explain() != null) {
                    colNameExplain = columnDocument.explain().trim();
                }
            } else {
                FriendlyProperty friendlyProperty = column.getProperty().getAnnotation(FriendlyProperty.class);
                if (friendlyProperty != null) {
                    if (friendlyProperty.value() != null) {
                        friedLyName = friendlyProperty.value().trim();
                    }
                    if (friendlyProperty.explain() != null) {
                        colNameExplain = friendlyProperty.explain().trim();
                    }
                }
            }
            if (column.isPrimaryKey() && friedLyName == "") {
                friedLyName = "主键";
            }
            builder.append(EnvironmentConstants.LINE_SEPARATOR);
            this.addColumnDocumentSeparate(builder, Integer.toString(i + 1), 7, " ", "");
            this.addColumnDocumentSeparate(builder, column.getColumnName(), 29, " ", "");
            this.addColumnDocumentSeparate(builder, friedLyName, 25, " ", "");
            this.addColumnDocumentSeparate(builder, this.definitionBuilder.getColumnType(column), 17, " ", "");
            String rulName;
            if (column.isPrimaryKey()) {
                if (column.isIdentityAssignKey()) {
                    rulName = "[PK AUTO_INCREMENT]";
                } else {
                    rulName = "[PK]";
                }
            } else {
                if (isSingleColumnIndex(column)) {
                    rulName = "[INDEX]";
                } else {
                    rulName = "";
                }
            }
            this.addColumnDocumentSeparate(builder, rulName, 17, " ", "");
            this.addColumnDocumentSeparate(builder, column.isNullable() ? "false" : "true", 7, " ", "");
            this.addColumnDocumentSeparate(builder, colNameExplain.trim(), 40, " ", "");
            builder.append("|");
        }
    }

    public String createTableDocument(EntityTable table, String tableName, boolean isBindEntityClass) {
        if (table.isView()) {
            ExceptionUtils.throwNotSupportException("视图不支持生成文档。");
        }
        if (StringUtils.isNullOrBlank(tableName)) {
            tableName = table.getName();
        }
        String lineSeparator = EnvironmentConstants.LINE_SEPARATOR;
        StringBuilder builder = new StringBuilder(2000);
        this.addTableDocumentHead(builder, table, tableName, isBindEntityClass);
        builder.append(EnvironmentConstants.LINE_SEPARATOR);
        this.addColumnDocument(builder, table);
        return builder.toString();
    }

    @Getter
    private class DocumentTable implements Comparable<DocumentTable> {
        private final String group;
        private final int groupOrder;
        private final int tableOrder;
        private final EntityTable table;

        public DocumentTable(String group, int groupOrder, int tableOrder, EntityTable table) {
            this.group = group;
            this.groupOrder = groupOrder;
            this.tableOrder = tableOrder;
            this.table = table;
        }

        @Override
        public int compareTo(DocumentTable o) {
            int to = Integer.compare(this.getGroupOrder(), o.getGroupOrder());
            if (to != 0) {
                return to;
            }
            int to1 = this.getGroup().compareTo(o.getGroup());
            if (to1 != 0) {
                return to1;
            }
            int to2 = Integer.compare(this.getTableOrder(), o.getTableOrder());
            if (to2 == 0) {
                to2 = this.getTable().getName().compareTo(o.getTable().getName());
            }
            if (to2 != 0) {
                return to2;
            }
            return Integer.compare(Integer.compare(to, to1), to2);
        }
    }
}
