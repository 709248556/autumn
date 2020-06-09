package com.autumn.word.pdf;

/**
 * pdf加密
 */
public final class PdfEncryption {

    public static final int ALLOW_ASSEMBLY = 1024;

    /**
     * 允许复制
     */
    public static final int ALLOW_COPY = 16;

    /**
     * 允许打印
     */
    public static final int ALLOW_DEGRADED_PRINTING = 4;

    /**
     * 允许填写
     */
    public static final int ALLOW_FILL_IN = 256;

    /**
     * 允许注释
     */
    public static final int ALLOW_MODIFY_ANNOTATIONS = 32;

    /**
     * 允许修改
     */
    public static final int ALLOW_MODIFY_CONTENTS = 8;

    /**
     * 允许打印
     */
    public static final int ALLOW_PRINTING = 2052;

    /**
     * 允许屏幕快照
     */
    public static final int ALLOW_SCREEN_READERS = 512;

    /**
     * 默认权限(允许复制和打印)
     */
    public static PdfEncryption DEFAULT = new PdfEncryption("", "", true, PdfEncryption.ALLOW_PRINTING | PdfEncryption.ALLOW_COPY);

    /**
     * 打印权限
     */
    public static PdfEncryption printing = new PdfEncryption("", "", true, PdfEncryption.ALLOW_PRINTING);

    /**
     * 实例化 PdfEncryption 类新实例
     *
     * @param userPassword    用户密码
     * @param ownerPassword   所有者密码
     * @param strength128Bits 128位加密
     * @param permissions     权限
     */
    public PdfEncryption(String userPassword, String ownerPassword, boolean strength128Bits, int permissions) {
        this.setUserPassword(userPassword == null ? "" : userPassword);
        this.setOwnerPassword(ownerPassword == null ? "" : ownerPassword);
        this.setStrength128Bits(strength128Bits);
        this.setPermissions(permissions);
    }

    /**
     * 获取用户密码
     */
    private String userPassword;

    public String getUserPassword() {
        return userPassword;
    }

    private void setUserPassword(String value) {
        userPassword = value;
    }

    /**
     * 获取所有者密码
     */
    private String ownerPassword;

    public String getOwnerPassword() {
        return ownerPassword;
    }

    private void setOwnerPassword(String value) {
        ownerPassword = value;
    }

    /**
     * 获取是否128位加密
     */
    private boolean strength128Bits;

    public boolean getStrength128Bits() {
        return strength128Bits;
    }

    private void setStrength128Bits(boolean value) {
        strength128Bits = value;
    }

    /**
     * 获取权限
     */
    private int permissions;

    public int getPermissions() {
        return permissions;
    }

    private void setPermissions(int value) {
        permissions = value;
    }
}