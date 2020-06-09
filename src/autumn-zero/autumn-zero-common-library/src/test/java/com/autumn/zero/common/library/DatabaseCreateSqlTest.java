package com.autumn.zero.common.library;

import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.provider.DbProvider;
import com.autumn.mybatis.provider.mysql.MySqlProvider;
import com.autumn.zero.common.library.entities.common.CommonDataDictionary;
import com.autumn.zero.common.library.entities.common.Region;
import com.autumn.zero.common.library.entities.personal.UserPersonalInvoiceAddress;
import com.autumn.zero.common.library.entities.personal.UserPersonalReceivingAddress;
import com.autumn.zero.common.library.entities.sys.BusinessAgreement;
import com.autumn.zero.common.library.entities.sys.SystemCommonConfig;
import com.autumn.zero.common.library.entities.sys.SystemHelpDocument;
import org.junit.Test;

/**
 * 数据库创建测式
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-25 17:46
 */
public class DatabaseCreateSqlTest {

    private DbProvider createDbProvider() {
        return new MySqlProvider();
    }

    private void printTableSql(Class<?> entityClass) {
        DbProvider provider = this.createDbProvider();
        String script = provider.getDefinitionBuilder().createTableScript(EntityTable.getTable(entityClass));
        System.out.println(script);
    }


    @Test
    public void createCommonDataDictionaryTableSql() {
        printTableSql(CommonDataDictionary.class);
    }

    @Test
    public void createSystemCommonConfigTableSql() {
        printTableSql(SystemCommonConfig.class);
    }

    @Test
    public void createRegionTableSql() {
        printTableSql(Region.class);
    }

    @Test
    public void createSystemHelpDocumentTableSql() {
        printTableSql(SystemHelpDocument.class);
    }

    @Test
    public void createBusinessAgreementTableSql() {
        printTableSql(BusinessAgreement.class);
    }

    @Test
    public void createUserInvoiceAddressTableSql() {
        printTableSql(UserPersonalInvoiceAddress.class);
    }

    @Test
    public void createUserReceivingAddressTableSql() {
        printTableSql(UserPersonalReceivingAddress.class);
    }
}
