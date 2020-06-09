package com.autumn.zero.authorization;

import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.provider.DbDocumentInfo;
import com.autumn.mybatis.provider.DbProvider;
import com.autumn.mybatis.provider.mysql.MySqlProvider;
import com.autumn.mybatis.provider.sqlserver.SqlServerProvider;
import com.autumn.zero.authorization.annotation.AutumnZeroCommonAuthorizationMybatisScan;
import com.autumn.zero.authorization.entities.common.log.UserOperationLog;
import org.junit.Test;

import java.time.LocalDate;

/**
 * 表脚本测试
 *
 * @author 老码农 2018-12-19 22:44:35
 */
public class TableScriptTest {

    /**
     *
     */
    @Test
    public void printMySqlTableScript() {
        DbProvider provider = new MySqlProvider();
        String script = provider.getDefinitionBuilder()
                .createTableScripts(AutumnZeroCommonAuthorizationMybatisScan.ENTITY_PACKAGE_BASE_PATH);
        System.out.println(script);
    }

    /**
     * RolePermission
     */
    @Test
    public void printMySqlTableScript1() {
        DbProvider provider = new MySqlProvider();
        String script = provider.getDefinitionBuilder()
                .createTableScript(EntityTable.getTable(UserOperationLog.class));
        System.out.println(script);
    }

    /**
     *
     */
    @Test
    public void printSqlServerTableScript() {
        DbProvider provider = new SqlServerProvider();
        String script = provider.getDefinitionBuilder()
                .createTableScripts(AutumnZeroCommonAuthorizationMybatisScan.ENTITY_PACKAGE_BASE_PATH);
        System.out.println(script);
    }

    /**
     * RolePermission
     */
    @Test
    public void printMySqlTableDocument() {
        DbProvider provider = new MySqlProvider();
        String script = provider.getDefinitionBuilder()
                .createTableDocument(EntityTable.getTable(UserOperationLog.class), true);
        System.out.println(script);
    }

    /**
     * RolePermission
     */
    @Test
    public void printMySqlPackageDocument() {
        DbDocumentInfo dbDocumentInfo = new DbDocumentInfo();
        dbDocumentInfo.setProjectName("测试生成文档");
        dbDocumentInfo.setCreatedDate(LocalDate.now());
        DbProvider provider = new MySqlProvider();
        String script = provider.getDefinitionBuilder().createProjectDocument(dbDocumentInfo, "com.autumn.zero.authorization.entities");
        System.out.println(script);
    }

}
