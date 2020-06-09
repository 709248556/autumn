package com.autumn.mybatis.provider;

import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.provider.annotation.ProviderDrive;

/**
 * 数据提供者抽象
 *
 * @author 老码农
 * <p>
 * 2017-10-19 08:24:10
 */
public abstract class AbstractProvider implements DbProvider {

    private final ProviderDriveType driveType;

    /**
     *
     */
    public AbstractProvider() {
        ProviderDrive drive = this.getClass().getAnnotation(ProviderDrive.class);
        if (drive != null) {
            this.driveType = drive.value();
        } else {
            this.driveType = null;
        }
    }

    @Override
    public ProviderDriveType getDriveType() {
        return this.driveType;
    }

    private String getSafeName(String value, boolean isTable) {
        value = value.trim();
        if (value.startsWith(this.getSafeNamePrefix()) || value.endsWith(this.getSafeNameSuffix())) {
            return value;
        }
        if (isTable && value.contains(".")) {
            String[] names = value.split("\\.");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < names.length; i++) {
                String name = names[i];
                if (i > 0) {
                    sb.append(".");
                }
                sb.append(String.format("%s%s%s", this.getSafeNamePrefix(), name, this.getSafeNameSuffix()));
            }
            return sb.toString();
        }
        return String.format("%s%s%s", this.getSafeNamePrefix(), value, this.getSafeNameSuffix());
    }

    @Override
    public String getSafeTableName(EntityTable table) {
        return this.getSafeTableName(table.getName());
    }

    @Override
    public String getSafeTableName(String tableName) {
        return this.getSafeName(tableName, true);
    }

    @Override
    public final String getSafeName(String columnName) {
        return this.getSafeName(columnName, false);
    }

    @Override
    public String toString() {
        return "DbProvider : {" +
                "driveName=" + this.getDriveName() + " , " +
                "driveType=" + this.getDriveType() +
                "}";
    }
}
