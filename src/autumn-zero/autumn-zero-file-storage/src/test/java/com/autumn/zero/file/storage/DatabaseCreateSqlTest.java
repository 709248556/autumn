package com.autumn.zero.file.storage;

import org.junit.Test;

import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.provider.DbProvider;
import com.autumn.mybatis.provider.mysql.MySqlProvider;
import com.autumn.zero.file.storage.entities.FileAttachmentInformation;

/**
 * 
 * @author 老码农 2019-03-18 17:38:46
 */
public class DatabaseCreateSqlTest {

	@Test
	public void createTableSql() {
		DbProvider provider = new MySqlProvider();
		String script = provider.getDefinitionBuilder()
				.createTableScript(EntityTable.getTable(FileAttachmentInformation.class));
		System.out.println(script);
	}

}
