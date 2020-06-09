package com.autumn.mybatis.annotation;

import com.autumn.util.PackageUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 注册默认的 Mybatis 信息
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-24 19:14
 */
class RegistrarDefaultMybatisInfo implements Serializable {

    private static final long serialVersionUID = 3210593716318354204L;

    private List<AutumnMybatisScanInfo> scanInfos;
    private String[] typeAliasesPackages;
    private String[] mapperInterfacePackages;

    public RegistrarDefaultMybatisInfo() {
        scanInfos = new ArrayList<>();
    }

    public List<AutumnMybatisScanInfo> getScanInfos() {
        return this.scanInfos;
    }

    public void setScanInfos(List<AutumnMybatisScanInfo> scanInfos) {
        this.scanInfos = scanInfos;
    }

    public String[] getTypeAliasesPackages() {
        List<String> list;
        if (typeAliasesPackages != null) {
            list = new ArrayList<>(Arrays.asList(typeAliasesPackages));
        } else {
            list = new ArrayList<>();
        }
        List<AutumnMybatisScanInfo> infos = this.getScanInfos();
        if (infos != null) {
            for (AutumnMybatisScanInfo info : infos) {
                list.addAll(Arrays.asList(info.getTypeAliasesPackages()));
            }
        }
        return PackageUtils.toPackages(list.toArray(new String[0]));
    }

    public void setTypeAliasesPackages(String[] typeAliasesPackages) {
        this.typeAliasesPackages = typeAliasesPackages;
    }

    /**
     * @return
     */
    public String[] getMapperInterfacePackages() {
        List<String> list;
        if (mapperInterfacePackages != null) {
            list = new ArrayList<>(Arrays.asList(mapperInterfacePackages));
        } else {
            list = new ArrayList<>();
        }
        List<AutumnMybatisScanInfo> infos = this.getScanInfos();
        if (infos != null) {
            for (AutumnMybatisScanInfo info : infos) {
                list.addAll(Arrays.asList(info.getMapperInterfacePackages()));
            }
        }
        return PackageUtils.toPackages(list.toArray(new String[0]));
    }

    public void setMapperInterfacePackages(String[] mapperInterfacePackages) {
        this.mapperInterfacePackages = mapperInterfacePackages;
    }


}
