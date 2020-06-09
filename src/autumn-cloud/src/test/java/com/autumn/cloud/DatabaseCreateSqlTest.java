package com.autumn.cloud;

import com.autumn.cloud.uid.worker.entities.WorkerNode;
import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.provider.DbProvider;
import com.autumn.mybatis.provider.mysql.MySqlProvider;
import com.autumn.timing.Clock;
import com.autumn.util.DateUtils;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-25 20:13
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
    public void createWorkerNodeTableSql() {
        printTableSql(WorkerNode.class);
    }

    @Test
    public void test1() {

        System.out.println( ~(-1L << 13));

        long maxDeltaSeconds = ~(-1L << 28);

        Date date = DateUtils.parseDate("2019-01-01");
        System.out.println(DateUtils.dateFormat(date));
        System.out.println(date.getTime());


        System.out.println(DateUtils.dateFormat(new Date(date.getTime() + (maxDeltaSeconds * 1000))));
        long value = TimeUnit.MILLISECONDS.toSeconds(date.getTime());

        System.out.println(value);



        long epochSeconds = TimeUnit.MILLISECONDS.toSeconds(Clock.now().getTime()) - TimeUnit.DAYS.toSeconds(365);

        System.out.println(DateUtils.dateFormat(new Date(epochSeconds * 1000)));
    }
}
