package com.autumn.mybatis.annotation;

/**
 * 扫描信息
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-24 0:59
 */
class AutumnMybatisScanInfo {

    /**
     * MyBatis mapper Interface 的包集合
     *
     * @return
     */
    private String[] mapperInterfacePackages;

    /**
     * 实体(POJO)类型(与数据映射的实体)包路径
     *
     * @return
     */
    private String[] typeAliasesPackages;

    /**
     * 名称(对应数据源名称或标识,多数据源时指定)
     *
     * @return
     */
    private String name = "";

    public String[] getMapperInterfacePackages() {
        return mapperInterfacePackages;
    }

    public void setMapperInterfacePackages(String[] mapperInterfacePackages) {
        this.mapperInterfacePackages = mapperInterfacePackages;
    }

    public String[] getTypeAliasesPackages() {
        return typeAliasesPackages;
    }

    public void setTypeAliasesPackages(String[] typeAliasesPackages) {
        this.typeAliasesPackages = typeAliasesPackages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
